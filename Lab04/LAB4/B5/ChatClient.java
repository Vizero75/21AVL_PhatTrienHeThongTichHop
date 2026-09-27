package LAB4.B5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try {
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            System.out.println("Đã kết nối tới Chat Server (" + host + ":" + port + ").");

            // Luồng phụ: Liên tục lắng nghe tin nhắn từ Server và in ra Console
            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Đã ngắt kết nối với server.");
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();

            // Luồng chính: Đọc dữ liệu người dùng nhập từ bàn phím và gửi lên Server
            String userInput;
            while ((userInput = console.readLine()) != null) {
                out.println(userInput);
                if (userInput.trim().equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

            socket.close();
        } catch (IOException e) {
            System.err.println("Lỗi kết nối tới Server: " + e.getMessage());
        }
    }
}
