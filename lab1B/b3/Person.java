package bai3;

import java.util.Scanner;

public class Person {
    protected String ten;
    protected String gioiTinh;
    protected String ngaySinh; // dạng dd/MM/yyyy
    protected String diaChi;

    // init contructor default
    public Person() {
        this.ten = "";
        this.gioiTinh = "";
        this.ngaySinh = "";
        this.diaChi = "";
    }

    // init contructor value
    public Person(String ten, String gioiTinh, String ngaySinh, String diaChi) {
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
    }

    // Getter
    public String getTen() { return ten; }
    public String getGioiTinh() { return gioiTinh; }
    public String getNgaySinh() { return ngaySinh; }
    public String getDiaChi() { return diaChi; }

    // Setter
    public void setTen(String ten) { this.ten = ten; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    // Input Stream keyboard
    public void inputInfo(Scanner sc) {
        System.out.print("Nhap ten: ");
        this.ten = sc.nextLine().trim();

        System.out.print("Nhap gioi tinh (Nam/Nu): ");
        this.gioiTinh = sc.nextLine().trim();

        System.out.print("Nhap ngay sinh (dd/MM/yyyy): ");
        this.ngaySinh = sc.nextLine().trim();

        System.out.print("Nhap dia chi: ");
        this.diaChi = sc.nextLine().trim();
    }

    // Hiển thị thông tin Person
    public void printInfo() {
        System.out.println("Ten      : " + ten);
        System.out.println("Gioi tinh: " + gioiTinh);
        System.out.println("Ngay sinh: " + ngaySinh);
        System.out.println("Dia chi  : " + diaChi);
    }
}
