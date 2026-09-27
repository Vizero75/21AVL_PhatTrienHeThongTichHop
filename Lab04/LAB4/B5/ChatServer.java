package LAB4.B5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private static final int PORT = 5000;
    private static final int MAX_CLIENTS = 50;

    private static final Map clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        System.out.println("Chat Server đang lắng nghe trên cổng " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                pool.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
    public static void broadcast(String message, String excludeUser) {
        for (Object key : clients.keySet()) {
            String user = String.valueOf(key);
            if (excludeUser == null || !user.equalsIgnoreCase(excludeUser)) {
                PrintWriter writer = (PrintWriter) clients.get(user);
                if (writer != null) {
                    writer.println(message);
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private String nickname;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String clientAddr = socket.getRemoteSocketAddress().toString();
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

                // 1. Giai đoạn đăng ký Nickname duy nhất
                while (true) {
                    out.println("ENTER_NICKNAME Vui lòng nhập nickname:");
                    String input = in.readLine();
                    if (input == null) return;

                    String chosenNick = input.trim();
                    if (chosenNick.isEmpty() || chosenNick.contains(" ")) {
                        out.println("ERR Nickname không được để trống hoặc chứa khoảng trắng!");
                        continue;
                    }

                    synchronized (clients) {
                        if (!clients.containsKey(chosenNick)) {
                            nickname = chosenNick;
                            clients.put(nickname, out);
                            out.println("OK Chào mừng " + nickname + " tham gia phòng chat!");
                            broadcast("SYSTEM: " + nickname + " đã tham gia phòng chat.", nickname);
                            System.out.println(nickname + " (" + clientAddr + ") đã tham gia.");
                            break;
                        } else {
                            out.println("ERR Nickname đã tồn tại, vui lòng chọn tên khác!");
                        }
                    }
                }

                // 2. Giai đoạn tiếp nhận lệnh
                String line;
                while ((line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.equalsIgnoreCase("QUIT")) {
                        out.println("OK BYE");
                        break;
                    } else if (line.equalsIgnoreCase("USERS")) {
                        out.println("USERS " + String.join(", ", clients.keySet()));
                    } else if (line.toUpperCase().startsWith("MSG ")) {
                        String msgContent = line.substring(4).trim();
                        if (!msgContent.isEmpty()) {
                            broadcast("[" + nickname + "]: " + msgContent, nickname);
                        }
                    } else {
                        out.println("ERR UNKNOWN_COMMAND (Hỗ trợ: USERS, MSG , QUIT)");
                    }
                }

            } catch (IOException e) {
                System.err.println("Mất kết nối với " + (nickname != null ? nickname : clientAddr));
            } finally {
                // Xử lý dọn dẹp tài nguyên khi client ngắt kết nối
                if (nickname != null) {
                    clients.remove(nickname);
                    broadcast("SYSTEM: " + nickname + " đã rời phòng chat.", null);
                    System.out.println(nickname + " đã ngắt kết nối.");
                }
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
