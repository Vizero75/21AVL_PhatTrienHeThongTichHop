package bai4;

import java.util.Scanner;
import bai3.Person;

public class Student extends Person {
    private double diemTrungBinh; // 0.0 - 10.0
    private String email;         // @email

    // init contructor default
    public Student() {
        super();
        this.diemTrungBinh = 0.0;
        this.email = "";
    }

    // init contructor value
    public Student(String ten, String gioiTinh, String ngaySinh, String diaChi,
                    double diemTrungBinh, String email) {
        super(ten, gioiTinh, ngaySinh, diaChi);
        setDiemTrungBinh(diemTrungBinh);
        setEmail(email);
    }

    // Getter
    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public String getEmail() {
        return email;
    }

    // Setter
    public void setDiemTrungBinh(double diemTrungBinh) {
        if (diemTrungBinh < 0.0 || diemTrungBinh > 10.0) {
            System.out.println("Diem trung binh khong hop le (phai tu 0.0 den 10.0). Gan gia tri mac dinh 0.0");
            this.diemTrungBinh = 0.0;
        } else {
            this.diemTrungBinh = diemTrungBinh;
        }
    }

    public void setEmail(String email) {
        if (isEmailHopLe(email)) {
            this.email = email;
        } else {
            System.out.println("Email khong hop le (phai chua '@' va khong co khoang trang). Gan gia tri rong.");
            this.email = "";
        }
    }

    // @email isvalid?
    private boolean isEmailHopLe(String email) {
        if (email == null) return false;
        return email.contains("@") && !email.contains(" ");
    }

    // InputStream keyboard
    @Override
    public void inputInfo(Scanner sc) {
        super.inputInfo(sc);

        double diem;
        while (true) {
            System.out.print("Nhap diem trung binh (0.0 - 10.0): ");
            diem = Double.parseDouble(sc.nextLine().trim());
            if (diem >= 0.0 && diem <= 10.0) break;
            System.out.println("Diem khong hop le, vui long nhap lai!");
        }
        this.diemTrungBinh = diem;

        String emailNhap;
        while (true) {
            System.out.print("Nhap email: ");
            emailNhap = sc.nextLine().trim();
            if (isEmailHopLe(emailNhap)) break;
            System.out.println("Email khong hop le (phai chua '@' va khong co khoang trang), vui long nhap lai!");
        }
        this.email = emailNhap;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("Email    : " + email);
        System.out.printf("Diem TB  : %.2f%n", diemTrungBinh);
        System.out.println("Hoc bong : " + (coHocBong() ? "Co" : "Khong"));
    }

    public boolean coHocBong() {
        return diemTrungBinh > 8.0;
    }
}
