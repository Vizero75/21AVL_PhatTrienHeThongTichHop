import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeCheckerImpl extends UnicastRemoteObject implements PrimeChecker {
    public PrimeCheckerImpl() throws RemoteException {
        super();
    }

    // So nguyen to: lon hon 1 va chi chia het cho 1 va chinh no.
    // Chi can thu cac uoc tu 2 den can bac hai cua n.
    public boolean isPrime(int n) throws RemoteException {
        System.out.println("Client kiem tra so: " + n);
        if (n < 2) return false;
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
