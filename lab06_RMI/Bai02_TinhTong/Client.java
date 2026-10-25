import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        Registry registry = LocateRegistry.getRegistry(host, 1099);
        Calculator stub = (Calculator) registry.lookup("CalculatorService");

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.print("Nhap a (hoac EXIT): ");
                    String s = sc.nextLine().trim();
                    if (s.equalsIgnoreCase("EXIT")) break;
                    int a = Integer.parseInt(s);
                    System.out.print("Nhap b: ");
                    int b = Integer.parseInt(sc.nextLine().trim());

                    System.out.println("Tong a + b = " + stub.add(a, b));
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Hay nhap so nguyen hop le.");
                }
            }
        }
    }
}
