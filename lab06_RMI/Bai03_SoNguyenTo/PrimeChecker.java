import java.rmi.Remote;
import java.rmi.RemoteException;

// Bai 3: kiem tra so nguyen to.
public interface PrimeChecker extends Remote {
    boolean isPrime(int n) throws RemoteException;
}
