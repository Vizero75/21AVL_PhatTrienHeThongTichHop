import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhap thang:");
        int so = scanner.nextInt();
        String[] months = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };
        if (so > 0 && so <= 12) {
            System.out.println("Thang " + so + " tuong ung tieng anh la: " + months[so]);
        } else {
            System.out.println("Thang nhap ko hop le");
        }
    }
}
