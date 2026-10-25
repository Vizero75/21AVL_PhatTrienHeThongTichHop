import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        Registry registry = LocateRegistry.getRegistry(host, 1099);
        Contact stub = (Contact) registry.lookup("ContactService");

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1.Them  2.Tim  3.Sua  4.Xoa  5.Xem tat ca  0.Thoat");
                System.out.print("Chon: ");
                String choice = sc.nextLine().trim();
                if (choice.equals("0")) break;

                String name, phone;
                switch (choice) {
                    case "1":
                        System.out.print("Ten: ");
                        name = sc.nextLine().trim();
                        System.out.print("So dien thoai: ");
                        phone = sc.nextLine().trim();
                        stub.addContact(name, phone);
                        System.out.println("Da them.");
                        break;
                    case "2":
                        System.out.print("Ten can tim: ");
                        name = sc.nextLine().trim();
                        phone = stub.findContact(name);
                        System.out.println(phone == null ? "Khong tim thay." : name + " - " + phone);
                        break;
                    case "3":
                        System.out.print("Ten can sua: ");
                        name = sc.nextLine().trim();
                        System.out.print("So dien thoai moi: ");
                        phone = sc.nextLine().trim();
                        System.out.println(stub.updateContact(name, phone) ? "Da sua." : "Khong tim thay.");
                        break;
                    case "4":
                        System.out.print("Ten can xoa: ");
                        name = sc.nextLine().trim();
                        System.out.println(stub.deleteContact(name) ? "Da xoa." : "Khong tim thay.");
                        break;
                    case "5":
                        System.out.println(stub.listContacts());
                        break;
                    default:
                        System.out.println("ERROR: Lua chon khong hop le.");
                }
            }
        }
    }
}
