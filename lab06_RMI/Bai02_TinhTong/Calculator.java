import java.rmi.Remote;
import java.rmi.RemoteException;

// Bai 2: tinh tong hai so nguyen.
public interface Calculator extends Remote {
    int add(int a, int b) throws RemoteException;
}
