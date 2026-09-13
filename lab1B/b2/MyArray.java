package bai2;

import java.util.Scanner;

public class MyArray {
    private int[] arr;
    private int n;          // số phần tử tối đa
    private int soPhanTu;   // số phần tử hiện có

    // Khởi tạo mảng với số phần tử tối đa là n
    public MyArray(int n) {
        this.n = n;
        this.arr = new int[n];
        this.soPhanTu = 0;
    }

    // Nhập giá trị cho mảng từ bàn phím
    public void nhapMang(Scanner sc) {
        System.out.print("Nhap so luong phan tu can nhap (<= " + n + "): ");
        int soLuong = Integer.parseInt(sc.nextLine().trim());
        if (soLuong > n) soLuong = n;
        soPhanTu = soLuong;
        for (int i = 0; i < soLuong; i++) {
            System.out.print("Nhap phan tu thu " + i + ": ");
            arr[i] = Integer.parseInt(sc.nextLine().trim());
        }
    }

    // Xuất thông tin của mảng
    public void xuatMang() {
        System.out.print("Mang hien tai (" + soPhanTu + " phan tu): [");
        for (int i = 0; i < soPhanTu; i++) {
            System.out.print(arr[i]);
            if (i < soPhanTu - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // Thêm phần tử Y vào đầu mảng
    public boolean themDau(int y) {
        if (soPhanTu >= n) {
            System.out.println("Mang da day, khong the them!");
            return false;
        }
        for (int i = soPhanTu; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = y;
        soPhanTu++;
        return true;
    }

    // Thêm phần tử Y vào cuối mảng
    public boolean themCuoi(int y) {
        if (soPhanTu >= n) {
            System.out.println("Mang da day, khong the them!");
            return false;
        }
        arr[soPhanTu] = y;
        soPhanTu++;
        return true;
    }

    // Thêm phần tử Y vào vị trí thứ i (0-based) của mảng
    public boolean themGiua(int i, int y) {
        if (soPhanTu >= n) {
            System.out.println("Mang da day, khong the them!");
            return false;
        }
        if (i < 0 || i > soPhanTu) {
            System.out.println("Vi tri khong hop le!");
            return false;
        }
        for (int k = soPhanTu; k > i; k--) {
            arr[k] = arr[k - 1];
        }
        arr[i] = y;
        soPhanTu++;
        return true;
    }

    // Xóa phần tử có giá trị X đầu tiên tìm thấy trong mảng
    public boolean xoaPhanTu(int x) {
        int viTri = timKiemTuanTu(x);
        if (viTri == -1) {
            System.out.println("Khong tim thay phan tu " + x + " trong mang!");
            return false;
        }
        return xoaViTri(viTri);
    }

    // Xóa phần tử ở vị trí thứ j (0-based) trong mảng
    public boolean xoaViTri(int j) {
        if (j < 0 || j >= soPhanTu) {
            System.out.println("Vi tri khong hop le!");
            return false;
        }
        for (int k = j; k < soPhanTu - 1; k++) {
            arr[k] = arr[k + 1];
        }
        soPhanTu--;
        return true;
    }

    // Tìm giá trị lớn nhất trong mảng con [0, soPhanTu)
    private int timMax() {
        int max = arr[0];
        for (int i = 1; i < soPhanTu; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    // Sắp xếp mảng theo phương pháp Radix Sort
    // tangDan = true: sắp xếp tăng dần, false: giảm dần
    // Lưu ý: Radix sort chuẩn áp dụng cho số nguyên khong am.
    public void sapXepRadix(boolean tangDan) {
        if (soPhanTu == 0) return;
        int max = timMax();
        if (max < 0) {
            System.out.println("Radix sort trong bai nay chi ho tro so nguyen khong am!");
            return;
        }
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(exp);
        }
        if (!tangDan) {
            // Đảo ngược mảng để có thứ tự giảm dần
            for (int i = 0; i < soPhanTu / 2; i++) {
                int tmp = arr[i];
                arr[i] = arr[soPhanTu - 1 - i];
                arr[soPhanTu - 1 - i] = tmp;
            }
        }
    }

    // Sắp xếp ổn định theo từng chữ số (digit) - dùng bởi Radix sort
    private void countingSortByDigit(int exp) {
        int[] output = new int[soPhanTu];
        int[] count = new int[10];

        for (int i = 0; i < soPhanTu; i++) {
            int digit = (arr[i] / exp) % 10;
            count[digit]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = soPhanTu - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }
        for (int i = 0; i < soPhanTu; i++) {
            arr[i] = output[i];
        }
    }

    // Tìm kiếm tuần tự phần tử B trong mảng CHƯA sắp xếp - trả về vị trí hoặc -1
    public int timKiemTuanTu(int b) {
        for (int i = 0; i < soPhanTu; i++) {
            if (arr[i] == b) return i;
        }
        return -1;
    }

    // Tìm kiếm nhị phân phần tử B trong mảng ĐÃ sắp xếp tăng dần - trả về vị trí hoặc -1
    public int timKiemNhiPhan(int b) {
        int left = 0, right = soPhanTu - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == b) return mid;
            if (arr[mid] < b) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    public int getSoPhanTu() {
        return soPhanTu;
    }

    public int[] getArr() {
        return arr;
    }
}
