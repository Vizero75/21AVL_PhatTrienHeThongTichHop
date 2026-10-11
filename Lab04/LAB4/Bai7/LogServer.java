package LAB4.Bai7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogServer {
    private static final int PORT = 5000;
    private static final int MAX_CLIENTS = 20;
    private static final Path LOG_DIR = Path.of("data", "logs");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        // Đảm bảo thư mục data/logs tồn tại
        try {
            Files.createDirectories(LOG_DIR);
        } catch (IOException e) {
            System.err.println("Không thể tạo thư mục lưu log: " + e.getMessage());
            return;
        }

        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        System.out.println("Log Server đang lắng nghe trên cổng " + PORT + "...");

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
        String clientAddress = socket.getRemoteSocketAddress().toString();
        System.out.println("Kết nối mới từ: " + clientAddress);

        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String clientId = null;

            // 1. Giai đoạn handshake: Bắt buộc gửi "HELLO "
            while (true) {
                String line = in.readLine();
                if (line == null) return;

                String trimmed = line.trim();
                if (trimmed.toUpperCase().startsWith("HELLO ")) {
                    String id = trimmed.substring(6).trim();

                    // Ràng buộc: clientId chỉ gồm chữ, số, '-', '_'
                    if (id.matches("^[a-zA-Z0-9_-]+$")) {
                        clientId = id;
                        out.println("OK HELLO " + clientId);
                        System.out.println("Client [" + clientAddress + "] đã định danh là: " + clientId);
                        break;
                    } else {
                        out.println("ERR INVALID_CLIENT_ID (Chỉ chấp nhận chữ, số, dấu '-' và '_')");
                    }
                } else if (trimmed.equalsIgnoreCase("QUIT")) {
                    out.println("OK BYE");
                    return;
                } else {
                    out.println("ERR PLEASE_HELLO_FIRST (Cú pháp: HELLO )");
                }
            }

            // Đường dẫn tệp log: data/logs/.txt
            Path clientLogPath = LOG_DIR.resolve(clientId + ".txt");

            // 2. Giai đoạn nhận tin nhắn và ghi nhật ký
            String message;
            while ((message = in.readLine()) != null) {
                String trimmed = message.trim();

                // Lệnh QUIT kết thúc phiên
                if (trimmed.equalsIgnoreCase("QUIT")) {
                    out.println("OK BYE");
                    break;
                }

                if (trimmed.isEmpty()) continue;

                // Định dạng dòng log: [Timestamp] [Remote Address] Nội dung
                String timestamp = LocalDateTime.now().format(FORMATTER);
                String logEntry = String.format("[%s] [%s] %s", timestamp, clientAddress, trimmed);

                // Ghi vào tệp ở chế độ nối thêm (APPEND) bằng UTF-8
                synchronized (LogServer.class) {
                    try (BufferedWriter writer = Files.newBufferedWriter(
                            clientLogPath,
                            StandardCharsets.UTF_8,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.APPEND)) {
                        writer.write(logEntry);
                        writer.newLine();
                    }
                }

                out.println("OK LOGGED");
            }

        } catch (IOException e) {
            System.err.println("Lỗi giao tiếp với client " + clientAddress + ": " + e.getMessage());
        } finally {
            System.out.println("Client đã ngắt kết nối: " + clientAddress);
        }
    }
}