
public class GiangVien extends Nguoi {

    private String maGiangVien;
    private String chuyenMon;
    private double luongCoBan;
    private double heSoLuong;

    // Constructor gọi constructor lớp cha bằng super
    public GiangVien(String hoTen, int namSinh, String diaChi,
            String maGiangVien, String chuyenMon,
            double luongCoBan, double heSoLuong) {
        super(hoTen, namSinh, diaChi);
        this.maGiangVien = maGiangVien;
        this.chuyenMon = chuyenMon;
        this.luongCoBan = luongCoBan;
        this.heSoLuong = heSoLuong;
    }

    // Getter & Setter riêng
    public String getMaGiangVien() {
        return maGiangVien;
    }

    public void setMaGiangVien(String maGiangVien) {
        this.maGiangVien = maGiangVien;
    }

    public String getChuyenMon() {
        return chuyenMon;
    }

    public void setChuyenMon(String chuyenMon) {
        this.chuyenMon = chuyenMon;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public double getHeSoLuong() {
        return heSoLuong;
    }

    public void setHeSoLuong(double heSoLuong) {
        this.heSoLuong = heSoLuong;
    }

    // Tính lương = lương cơ bản * hệ số lương
    public double tinhLuong() {
        return luongCoBan * heSoLuong;
    }

    // Ghi đè hienThiThongTin, tận dụng lại thông tin chung của lớp cha
    @Override
    public void hienThiThongTin() {
        System.out.print("[GIANG VIEN] ");
        super.hienThiThongTin();
        System.out.printf("            Mã GV: %-8s | Chuyên môn: %-15s | Lương: %,.0f%n",
                maGiangVien, chuyenMon, tinhLuong());
    }
}
