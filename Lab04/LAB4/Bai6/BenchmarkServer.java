package LAB4.Bai6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BenchmarkServer {
    private static final int TCP_PORT = 5000;
    private static final int UDP_PORT = 5001;

    public static void main(String[] args) {
        System.out.println("=== KHỞI CHẠY BENCHMARK SERVER ===");
        
        // 1. Luồng chạy TCP Server
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
                System.out.println("TCP Server đang lắng nghe trên cổng " + TCP_PORT);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleTcpClient(clientSocket)).start();
                }
            } catch (IOException e) {
                System.err.println("Lỗi TCP Server: " + e.getMessage());
            }
        }).start();

        // 2. Luồng chạy UDP Server
        new Thread(() -> {
            byte[] buffer = new byte[4096];
            try (DatagramSocket udpSocket = new DatagramSocket(UDP_PORT)) {
                System.out.println("UDP Server đang lắng nghe trên cổng " + UDP_PORT);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);
                    
                    // Echo ngược lại đúng dữ liệu đã nhận cho client
                    DatagramPacket response = new DatagramPacket(
                            packet.getData(), packet.getOffset(), packet.getLength(),
                            packet.getAddress(), packet.getPort());
                    udpSocket.send(response);
                }
            } catch (IOException e) {
                System.err.println("Lỗi UDP Server: " + e.getMessage());
            }
        }).start();
    }

    private static void handleTcpClient(Socket socket) {
        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String line;
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("QUIT")) {
                    break;
                }
                out.println(line); // Echo lại nội dung
            }
        } catch (IOException e) {
            // Client ngắt kết nối
        }
    }
}
