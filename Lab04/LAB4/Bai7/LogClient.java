package LAB4.Bai7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LogClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try (Socket socket = new Socket(host, port);
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            System.out.println("Đã kết nối tới Log Server (" + host + ":" + port + ")");
            System.out.println("Bước 1: Nhập lệnh định danh: HELLO  (Ví dụ: HELLO client_01)");
            System.out.println("Bước 2: Gửi nội dung tin nhắn cần ghi log");
            System.out.println("Bước 3: Nhập QUIT để thoát phiên\n");

            String line;
            while ((line = console.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                out.println(line);
                String response = in.readLine();
                if (response == null) {
                    System.out.println("Server đã đóng kết nối.");
                    break;
                }

                System.out.println("Server: " + response);

                if (line.trim().equalsIgnoreCase("QUIT")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi kết nối tới Server: " + e.getMessage());
        }
    }
}