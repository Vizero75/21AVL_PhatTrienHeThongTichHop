
public class Main2 {

    public static void main(String[] args) {
        SinhVien sv1 = new SinhVien("Nguyen Van A", 2004, "TP.HCM", "SV001", "Cong nghe thong tin", 8.7);
        SinhVien sv2 = new SinhVien("Tran Thi B", 2003, "Ha Noi", "SV002", "Ke toan", 6.2);
        GiangVien gv1 = new GiangVien("Le Van C", 1985, "Da Nang", "GV001", "Khoa hoc may tinh", 8000000, 3.5);
        GiangVien gv2 = new GiangVien("Pham Thi D", 1978, "Can Tho", "GV002", "Toan ung dung", 8500000, 4.2);

        System.out.println("===== THONG TIN SINH VIEN =====");
        sv1.hienThiThongTin();
        sv2.hienThiThongTin();

        System.out.println("\n===== THONG TIN GIANG VIEN =====");
        gv1.hienThiThongTin();
        gv2.hienThiThongTin();

        System.out.println("\n===== XEP LOAI SINH VIEN =====");
        System.out.println(sv1.getHoTen() + " xep loai: " + sv1.xepLoai());
        System.out.println(sv2.getHoTen() + " xep loai: " + sv2.xepLoai());

        System.out.println("\n===== LUONG GIANG VIEN =====");
        System.out.printf("%s co luong: %,.0f%n", gv1.getHoTen(), gv1.tinhLuong());
        System.out.printf("%s co luong: %,.0f%n", gv2.getHoTen(), gv2.tinhLuong());
    }
}
