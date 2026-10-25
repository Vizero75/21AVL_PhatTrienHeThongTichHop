import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        Registry registry = LocateRegistry.getRegistry(host, 1099);
        PrimeChecker stub = (PrimeChecker) registry.lookup("PrimeService");

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("Nhap so nguyen n (hoac EXIT): ");
                String s = sc.nextLine().trim();
                if (s.equalsIgnoreCase("EXIT")) break;
                try {
                    int n = Integer.parseInt(s);
                    if (stub.isPrime(n)) {
                        System.out.println(n + " LA so nguyen to.");
                    } else {
                        System.out.println(n + " KHONG phai so nguyen to.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Hay nhap so nguyen hop le.");
                }
            }
        }
    }
}
