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

public class MayTinhServer {
    private static final int PORT = 5000;        
    private static final int MAX_CLIENTS = 10;   

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(MAX_CLIENTS);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Máy tính Server đang nghe trên cổng " + PORT + "...");
            while (true) {
                Socket client = serverSocket.accept();    
                pool.submit(() -> handleClient(client));    
            }
        } catch (IOException e) {
            System.out.println("Lỗi server: " + e.getMessage());
        } finally {
            pool.shutdown();                            
        }
    }

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
                if (cmd.equalsIgnoreCase("QUIT")) {       
                    out.println("OK BYE");
                    break;
                }
                out.println(calculate(cmd));              
            }
        } catch (IOException e) {
            System.out.println("Lỗi giao tiếp với " + addr + ": " + e.getMessage());
        } finally {
            System.out.println("Client đã ngắt kết nối: " + addr);
        }
    }


    public static String calculate(String cmd) {
        String[] parts = cmd.split("\\s+");                
        if (parts.length != 4 || !parts[0].equalsIgnoreCase("CALC")) {
            return "ERR INVALID_FORMAT";                 
        }
        String op = parts[1];
        double a, b;
        try {
            a = Double.parseDouble(parts[2]);
            b = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            return "ERR INVALID_NUMBER";                    
        }
        switch (op) {
            case "+": return "OK" + formatResult(a + b);
            case "-": return "OK" + formatResult(a - b);
            case "*": return "OK" + formatResult(a * b);
            case "/":
                if (b == 0) return "ERR DIVIDE_BY_ZERO";    
                return "OK" + formatResult(a / b);
            default:  return "ERR UNSUPPORTED_OPERATOR";    
        }
    }

  
    private static String formatResult(double v) {
        if (v == (long) v) return String.format("%d", (long) v);
        return String.valueOf(v);
    }
}
