package bai2;

import java.util.Scanner;

public class DemoArray {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap so phan tu toi da cua mang: ");
        int n = Integer.parseInt(sc.nextLine().trim());
        MyArray myArray = new MyArray(n);

        myArray.nhapMang(sc);
        System.out.println("\n-- Mang sau khi nhap --");
        myArray.xuatMang();

        System.out.println("\n-- Them phan tu 100 vao dau --");
        myArray.themDau(100);
        myArray.xuatMang();

        System.out.println("\n-- Them phan tu 200 vao cuoi --");
        myArray.themCuoi(200);
        myArray.xuatMang();

        System.out.println("\n-- Them phan tu 999 vao vi tri 2 --");
        myArray.themGiua(2, 999);
        myArray.xuatMang();

        System.out.println("\n-- Xoa phan tu co gia tri 999 --");
        myArray.xoaPhanTu(999);
        myArray.xuatMang();

        System.out.println("\n-- Xoa phan tu vi tri 0 --");
        myArray.xoaViTri(0);
        myArray.xuatMang();

        System.out.println("\n-- Tim kiem tuan tu (mang chua sap xep) --");
        System.out.print("Nhap gia tri B can tim: ");
        int b1 = Integer.parseInt(sc.nextLine().trim());
        int viTri1 = myArray.timKiemTuanTu(b1);
        System.out.println(viTri1 == -1 ? "Khong tim thay!" : "Tim thay tai vi tri: " + viTri1);

        System.out.println("\n-- Sap xep TANG DAN bang Radix Sort --");
        myArray.sapXepRadix(true);
        myArray.xuatMang();

        System.out.println("\n-- Tim kiem nhi phan (mang da sap xep tang dan) --");
        System.out.print("Nhap gia tri B can tim: ");
        int b2 = Integer.parseInt(sc.nextLine().trim());
        int viTri2 = myArray.timKiemNhiPhan(b2);
        System.out.println(viTri2 == -1 ? "Khong tim thay!" : "Tim thay tai vi tri: " + viTri2);

        System.out.println("\n-- Sap xep GIAM DAN bang Radix Sort --");
        myArray.sapXepRadix(false);
        myArray.xuatMang();

        sc.close();
    }
}
