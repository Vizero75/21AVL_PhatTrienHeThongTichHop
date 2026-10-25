import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    static final int PORT = 1099;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind("CalculatorService", new CalculatorImpl());
        System.out.println("RMI Server (Tinh tong) dang chay tai cong " + PORT + "...");
    }
}
