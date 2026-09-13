package bai5;

import java.util.Scanner;
import bai4.Student;

public class StudentDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap so luong sinh vien can nhap (n): ");
        int n = Integer.parseInt(sc.nextLine().trim());

        Student[] dsSinhVien = new Student[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Nhap thong tin sinh vien thu " + (i + 1) + " ---");
            dsSinhVien[i] = new Student();
            dsSinhVien[i].inputInfo(sc);
        }

        // println
        System.out.println("\n===== DANH SACH TAT CA SINH VIEN =====");
        for (int i = 0; i < n; i++) {
            System.out.println("\n-- Sinh vien " + (i + 1) + " --");
            dsSinhVien[i].printInfo();
        }

        if (n > 0) {
            // search SV
            Student caoNhat = dsSinhVien[0];
            Student thapNhat = dsSinhVien[0];
            for (int i = 1; i < n; i++) {
                if (dsSinhVien[i].getDiemTrungBinh() > caoNhat.getDiemTrungBinh()) {
                    caoNhat = dsSinhVien[i];
                }
                if (dsSinhVien[i].getDiemTrungBinh() < thapNhat.getDiemTrungBinh()) {
                    thapNhat = dsSinhVien[i];
                }
            }

            System.out.println("\n===== SINH VIEN CO DIEM TRUNG BINH CAO NHAT =====");
            caoNhat.printInfo();

            System.out.println("\n===== SINH VIEN CO DIEM TRUNG BINH THAP NHAT =====");
            thapNhat.printInfo();
        }

        // print SV has HB 
        System.out.println("\n===== DANH SACH SINH VIEN DUOC HOC BONG (Diem TB > 8.0) =====");
        boolean coHocBong = false;
        for (int i = 0; i < n; i++) {
            if (dsSinhVien[i].coHocBong()) {
                System.out.println("\n-- Sinh vien " + (i + 1) + " --");
                dsSinhVien[i].printInfo();
                coHocBong = true;
            }
        }
        if (!coHocBong) {
            System.out.println("Khong co sinh vien nao duoc hoc bong.");
        }

        sc.close();
    }
}
