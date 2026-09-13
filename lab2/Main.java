
public class Main {

    public static void main(String[] args) {
        SanPham sp1 = new SanPham("SP001", "Ban phim", 250000, 20);
        SanPham sp2 = new SanPham("SP002", "Chuot khong day", 150000, 10);

        System.out.println("===== THONG TIN BAN DAU =====");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();
        System.out.println("\n===== NHAP HANG CHO SP1 (+15) =====");
        sp1.nhapHang(15);
        sp1.hienThiThongTin();
        System.out.println("\n===== NHAP HANG KHONG HOP LE (SP1: -5) =====");
        sp1.nhapHang(-5);

        System.out.println("\n===== BAN HANG THANH CONG (SP1: -10) =====");
        boolean ketQua1 = sp1.banHang(10);
        System.out.println("Ket qua ban hang: " + ketQua1);
        sp1.hienThiThongTin();

        System.out.println("\n===== BAN VUOT QUA TON KHO (SP2: -50) =====");
        sp2.hienThiThongTin();
        boolean ketQua2 = sp2.banHang(50);
        System.out.println("Ket qua ban hang: " + ketQua2);

        System.out.println("\n===== THONG TIN SAU CUNG =====");
        sp1.hienThiThongTin();
        sp2.hienThiThongTin();
    }
}
