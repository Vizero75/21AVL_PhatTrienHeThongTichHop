package LAB4.Bai8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.Scanner;

public class FileTransferClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== CLIENT TRUYỀN FILE AN TOÀN ===");
        System.out.print("Nhập đường dẫn file cần gửi (Ví dụ: data/test.txt hoặc data/source.png): ");
        String filePathStr = scanner.nextLine().trim();

        Path path = Path.of(filePathStr);
        if (!Files.exists(path) || Files.isDirectory(path)) {
            System.err.println("File không tồn tại hoặc là thư mục: " + path.toAbsolutePath());
            return;
        }

        try {
            long fileSize = Files.size(path);
            System.out.println("Kích thước file: " + fileSize + " bytes");
            System.out.println("Đang tính toán mã băm SHA-256...");
            String sha256 = calculateSHA256(path);
            System.out.println("SHA-256: " + sha256);

            // Tên file gửi đi (lấy tên file gốc)
            String fileName = path.getFileName().toString();

            System.out.print("Bạn có muốn giả lập thử nghiệm đường dẫn chứa '../' không? (y/n): ");
            String testTraversal = scanner.nextLine().trim();
            if (testTraversal.equalsIgnoreCase("y")) {
                fileName = "../malicious_" + fileName;
                System.out.println("Tên file gửi trong metadata đổi thành: " + fileName);
            }

            try (Socket socket = new Socket(host, port);
                 OutputStream out = socket.getOutputStream();
                 InputStream in = socket.getInputStream();
                 InputStream fileIn = Files.newInputStream(path)) {

                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                // 1. Gửi metadata: FILE   
                String metadata = String.format("FILE %s %d %s", fileName, fileSize, sha256);
                pw.println(metadata);

                // 2. Gửi luồng byte dữ liệu
                byte[] buffer = new byte[8192];
                int read;
                long totalSent = 0;
                while ((read = fileIn.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                    totalSent += read;
                }
                out.flush();
                System.out.printf("Đã gửi: %d bytes. Đang chờ phản hồi từ Server...%n", totalSent);

                // 3. Nhận phản hồi từ Server
                String response = br.readLine();
                System.out.println("Phản hồi từ Server: " + response);

            }

        } catch (Exception e) {
            System.err.println("Lỗi truyền file: " + e.getMessage());
        }
    }

    private static String calculateSHA256(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream fis = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        byte[] hash = digest.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}