package LAB4.Bai4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Server máy tính từ xa: nhận lệnh "CALC <toán tử> <số 1> <số 2>" qua TCP và trả kết quả
public class MayTinhServer {
    private static final int PORT = 5000;        // cổng server lắng nghe
    private static final int MAX_CLIENTS = 10;   // số client phục vụ cùng lúc tối đa

    public static void main(String[] args) {
        // Tạo pool 10 luồng, mỗi client được xử lý trên một luồng riêng
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Máy tính Server đang nghe trên cổng " + PORT + "...");
            while (true) {
                Socket client = serverSocket.accept();      // chờ client kết nối
                pool.submit(() -> handleClient(client));    // giao client cho một luồng xử lý
            }
        } catch (IOException e) {
            System.out.println("Lỗi server: " + e.getMessage());
        } finally {
            pool.shutdown();                                // đóng pool khi server dừng
        }
    }

    // Xử lý một client: đọc từng dòng lệnh, tính toán và gửi kết quả về
    private static void handleClient(Socket socket) {
        String addr = socket.getRemoteSocketAddress().toString();
        System.out.println("Client đã kết nối: " + addr);
        try (Socket s = socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String line;
            while ((line = in.readLine()) != null) {
                String cmd = line.trim();
                if (cmd.equalsIgnoreCase("QUIT")) {         // client muốn thoát
                    out.println("OK BYE");
                    break;
                }
                out.println(calculate(cmd));                // tính và trả kết quả
            }
        } catch (IOException e) {
            System.out.println("Lỗi giao tiếp với " + addr + ": " + e.getMessage());
        } finally {
            System.out.println("Client đã ngắt kết nối: " + addr);
        }
    }

    // Phân tích lệnh "CALC op a b" và trả về "OK<kết quả>" hoặc mã lỗi "ERR ..."
    public static String calculate(String cmd) {
        String[] parts = cmd.split("\\s+");                 // tách theo khoảng trắng
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("CALC")) {
            return "ERR INVALID_FORMAT";                    // sai cú pháp
        }
        String op = parts[1];
        double a, b;
        try {
            a = Double.parseDouble(parts[2]);
            b = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER";                    // không phải số
        }
        switch (op) {
            case "+": return "OK" + formatResult(a + b);
            case "-": return "OK" + formatResult(a - b);
            case "*": return "OK" + formatResult(a * b);
            case "/":
                if (b == 0) return "ERR DIVIDE_BY_ZERO";    // chia cho 0
                return "OK" + formatResult(a / b);
            default:  return "ERR UNSUPPORTED_OPERATOR";    // toán tử không hỗ trợ
        }
    }

    // Nếu kết quả là số nguyên thì in không có ".0" (300 thay vì 300.0)
    private static String formatResult(double v) {
        if (v == (long) v) return String.format("%d", (long) v);
        return String.valueOf(v);
    }
}
