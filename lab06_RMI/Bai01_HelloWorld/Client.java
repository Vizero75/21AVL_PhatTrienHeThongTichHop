import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        Registry registry = LocateRegistry.getRegistry(host, 1099);

        // Lay doi tuong tu xa theo ten da dang ky.
        Hello stub = (Hello) registry.lookup("HelloService");
        System.out.println("Server tra ve: " + stub.sayHello());
    }
}
