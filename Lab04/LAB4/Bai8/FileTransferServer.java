package LAB4.Bai8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileTransferServer {
    private static final int PORT = 5000;
    private static final int MAX_CLIENTS = 10;
    private static final Path UPLOAD_DIR = Path.of("uploads");

    public static void main(String[] args) {
        try {
            Files.createDirectories(UPLOAD_DIR);
        } catch (IOException e) {
            System.err.println("Không tạo được thư mục uploads: " + e.getMessage());
            return;
        }

        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        System.out.println("File Transfer Server đang lắng nghe trên cổng " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                pool.submit(() -> handleClient(socket));
            }
        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    private static void handleClient(Socket socket) {
        String clientAddr = socket.getRemoteSocketAddress().toString();
        System.out.println("Kết nối từ: " + clientAddr);

        try (socket) {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // 1. Đọc dòng metadata
            String metadataLine = readLine(in);
            if (metadataLine == null) return;

            String[] parts = metadataLine.trim().split("\\s+");
            if (parts.length != 4 || !parts[0].equalsIgnoreCase("FILE")) {
                sendResponse(out, "ERR INVALID_METADATA_FORMAT");
                return;
            }

            String rawFileName = parts[1];
            long fileSize;
            try {
                fileSize = Long.parseLong(parts[2]);
                if (fileSize < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                sendResponse(out, "ERR INVALID_FILE_SIZE");
                return;
            }
            String expectedSha256 = parts[3];

            // 2. Chống Path Traversal: Lấy tên file an toàn (bỏ qua ../)
            String safeFileName = Path.of(rawFileName).getFileName().toString();
            if (safeFileName.isEmpty() || safeFileName.equals("..")) {
                sendResponse(out, "ERR INVALID_FILE_NAME");
                return;
            }

            Path targetPath = UPLOAD_DIR.resolve(safeFileName);
            System.out.printf("Đang nhận file '%s' (Kích thước: %d bytes)...%n", safeFileName, fileSize);

            // 3. Đọc đúng số byte fileSize và ghi ra đĩa đồng thời tính SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            long bytesRemaining = fileSize;
            byte[] buffer = new byte[8192];

            try (OutputStream fileOut = Files.newOutputStream(targetPath,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                while (bytesRemaining > 0) {
                    int toRead = (int) Math.min(buffer.length, bytesRemaining);
                    int bytesRead = in.read(buffer, 0, toRead);
                    if (bytesRead == -1) {
                        break; // Bị ngắt kết nối đột ngột
                    }
                    fileOut.write(buffer, 0, bytesRead);
                    digest.update(buffer, 0, bytesRead);
                    bytesRemaining -= bytesRead;
                }
            }

            if (bytesRemaining > 0) {
                Files.deleteIfExists(targetPath);
                sendResponse(out, "ERR INCOMPLETE_DATA_STREAM");
                System.err.println("Nhận file thất bại: Thiếu dữ liệu!");
                return;
            }

            // 4. So khớp SHA-256
            byte[] hashBytes = digest.digest();
            String actualSha256 = bytesToHex(hashBytes);

            if (actualSha256.equalsIgnoreCase(expectedSha256)) {
                sendResponse(out, "OK " + actualSha256);
                System.out.println("Nhận và lưu thành công file: " + safeFileName);
            } else {
                sendResponse(out, "ERR HASH_MISMATCH");
                System.err.printf("Sai lệch SHA-256! Kỳ vọng: %s | Thực tế: %s%n", expectedSha256, actualSha256);
            }

        } catch (Exception e) {
            System.err.println("Lỗi xử lý file từ " + clientAddr + ": " + e.getMessage());
        } finally {
            System.out.println("Hoàn tất kết nối với " + clientAddr);
        }
    }

    private static String readLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = in.read()) != -1) {
            if (b == '\n') break;
            if (b != '\r') {
                sb.append((char) b);
            }
        }
        if (sb.length() == 0 && b == -1) return null;
        return sb.toString();
    }

    private static void sendResponse(OutputStream out, String response) throws IOException {
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);
        pw.println(response);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}