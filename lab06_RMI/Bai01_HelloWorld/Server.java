import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    static final int PORT = 1099;

    public static void main(String[] args) throws Exception {
        // Tao registry ngay trong chuong trinh, khong can chay lenh rmiregistry.
        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind("HelloService", new HelloImpl());
        System.out.println("RMI Server dang chay tai cong " + PORT + "...");
    }
}
