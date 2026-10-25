import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContactImpl extends UnicastRemoteObject implements Contact {
    // Danh ba luu tren server: ten -> so dien thoai.
    private final HashMap<String, String> contacts = new HashMap<>();

    public ContactImpl() throws RemoteException {
        super();
    }

    // synchronized: nhieu client goi cung luc khong lam hong HashMap.
    public synchronized void addContact(String name, String phone) throws RemoteException {
        contacts.put(name, phone);
        System.out.println("Them: " + name + " - " + phone);
    }

    public synchronized String findContact(String name) throws RemoteException {
        return contacts.get(name); // null neu khong tim thay
    }

    public synchronized boolean deleteContact(String name) throws RemoteException {
        boolean ok = contacts.remove(name) != null;
        System.out.println("Xoa " + name + ": " + ok);
        return ok;
    }

    public synchronized boolean updateContact(String name, String newPhone) throws RemoteException {
        if (!contacts.containsKey(name)) return false;
        contacts.put(name, newPhone);
        System.out.println("Sua: " + name + " - " + newPhone);
        return true;
    }

    public synchronized String listContacts() throws RemoteException {
        if (contacts.isEmpty()) return "Danh ba trong.";
        StringBuilder sb = new StringBuilder();
        for (String name : contacts.keySet()) {
            sb.append(name).append(" - ").append(contacts.get(name)).append("\n");
        }
        return sb.toString().trim();
    }
}
