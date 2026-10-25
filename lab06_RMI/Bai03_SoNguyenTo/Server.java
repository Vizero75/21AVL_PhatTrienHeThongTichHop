import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    static final int PORT = 1099;

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind("PrimeService", new PrimeCheckerImpl());
        System.out.println("RMI Server (So nguyen to) dang chay tai cong " + PORT + "...");
    }
}
