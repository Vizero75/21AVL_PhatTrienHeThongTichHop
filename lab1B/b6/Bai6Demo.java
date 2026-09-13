package bai6;

public class Bai6Demo {
    public static void main(String[] args) {
        C c = new C();

        System.out.println("Truoc khi goi setAX:");
        c.inThongTin();

        System.out.println("\nGoi c.setAX(100) - chi thay doi x cua A:");
        c.setAX(100);
        c.inThongTin();
    }
}
