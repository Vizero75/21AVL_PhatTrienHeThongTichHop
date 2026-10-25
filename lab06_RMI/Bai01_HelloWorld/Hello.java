import java.rmi.Remote;
import java.rmi.RemoteException;

// Bai 1: interface tu xa, moi phuong thuc phai throws RemoteException.
public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}
