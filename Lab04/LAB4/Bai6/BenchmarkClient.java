package LAB4.Bai6;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

public class BenchmarkClient {
    private static final String HOST = "localhost";
    private static final int TCP_PORT = 5000;
    private static final int UDP_PORT = 5001;
    private static final int TOTAL_MESSAGES = 1000;
    private static final int ROUNDS = 5;
    
    // Tạo mẫu thông điệp kích thước cố định (64 bytes)
    private static final String PAYLOAD = "BENCHMARK_TEST_PAYLOAD_STRING_ABCXYZ_0123456789_FIXED_SIZE_64_BYTES";

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("THỰC NGHIỆM ĐO HIỆU NĂNG TCP VÀ UDP TRÊN LOCALHOST");
        System.out.println("Số lượng thông điệp/đợt : " + TOTAL_MESSAGES);
        System.out.println("Số đợt đo               : " + ROUNDS);
        System.out.println("Kích thước payload      : " + PAYLOAD.getBytes(StandardCharsets.UTF_8).length + " bytes");
        System.out.println("==========================================================\n");

        runTcpBenchmark();
        System.out.println();
        runUdpBenchmark();
    }

    private static void runTcpBenchmark() {
        System.out.println("--- BẮT ĐẦU ĐO HIỆU NĂNG TCP ---");
        long totalTimeSum = 0;

        for (int r = 1; r <= ROUNDS; r++) {
            int received = 0;
            long startTime = System.currentTimeMillis();

            try (Socket socket = new Socket(HOST, TCP_PORT);
                 PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

                for (int i = 0; i < TOTAL_MESSAGES; i++) {
                    out.println(PAYLOAD);
                    String resp = in.readLine();
                    if (resp != null) {
                        received++;
                    }
                }
                out.println("QUIT");

            } catch (Exception e) {
                System.err.println("Lỗi đợt " + r + " (TCP): " + e.getMessage());
            }

            long duration = System.currentTimeMillis() - startTime;
            totalTimeSum += duration;
            System.out.printf("Đợt %d: Thời gian = %4d ms | Nhận = %d/%d (100.0%%)%n",
                    r, duration, received, TOTAL_MESSAGES);
        }
        System.out.printf("==> Thời gian TCP trung bình: %.2f ms%n", (double) totalTimeSum / ROUNDS);
    }

    private static void runUdpBenchmark() {
        System.out.println("--- BẮT ĐẦU ĐO HIỆU NĂNG UDP ---");
        long totalTimeSum = 0;
        int totalReceivedAll = 0;

        byte[] sendData = PAYLOAD.getBytes(StandardCharsets.UTF_8);

        for (int r = 1; r <= ROUNDS; r++) {
            int received = 0;
            long startTime = System.currentTimeMillis();

            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(100); // 100ms timeout nếu mất gói
                InetAddress address = InetAddress.getByName(HOST);
                byte[] recvBuffer = new byte[4096];

                for (int i = 0; i < TOTAL_MESSAGES; i++) {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, UDP_PORT);
                    socket.send(sendPacket);

                    DatagramPacket recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
                    try {
                        socket.receive(recvPacket);
                        received++;
                    } catch (SocketTimeoutException ignored) {
                        // Bị rơi gói tin hoặc timeout
                    }
                }

            } catch (Exception e) {
                System.err.println("Lỗi đợt " + r + " (UDP): " + e.getMessage());
            }

            long duration = System.currentTimeMillis() - startTime;
            totalTimeSum += duration;
            totalReceivedAll += received;
            double successRate = (double) received / TOTAL_MESSAGES * 100.0;
            System.out.printf("Đợt %d: Thời gian = %4d ms | Nhận = %d/%d (%.1f%%)%n",
                    r, duration, received, TOTAL_MESSAGES, successRate);
        }

        System.out.printf("==> Thời gian UDP trung bình: %.2f ms | Tỉ lệ nhận trung bình: %.1f%%%n",
                (double) totalTimeSum / ROUNDS, (double) totalReceivedAll / (ROUNDS * TOTAL_MESSAGES) * 100.0);
    }
}
