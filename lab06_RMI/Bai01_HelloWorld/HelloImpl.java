import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Lop trien khai interface o phia server.
public class HelloImpl extends UnicastRemoteObject implements Hello {
    public HelloImpl() throws RemoteException {
        super();
    }

    public String sayHello() throws RemoteException {
        System.out.println("Client vua goi sayHello()");
        return "Hello, World!";
    }
}
