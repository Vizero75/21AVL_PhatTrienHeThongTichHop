import java.rmi.Remote;
import java.rmi.RemoteException;

// Bai 4: quan ly danh ba tu xa.
public interface Contact extends Remote {
    void addContact(String name, String phone) throws RemoteException;
    String findContact(String name) throws RemoteException;
    boolean deleteContact(String name) throws RemoteException;
    // Bo sung de "sua" va xem toan bo danh ba.
    boolean updateContact(String name, String newPhone) throws RemoteException;
    String listContacts() throws RemoteException;
}
