
public class SinhVien extends Nguoi {

    private String maSinhVien;
    private String nganhHoc;
    private double diemTrungBinh;

    // Constructor gọi constructor lớp cha bằng super
    public SinhVien(String hoTen, int namSinh, String diaChi,
            String maSinhVien, String nganhHoc, double diemTrungBinh) {
        super(hoTen, namSinh, diaChi);
        this.maSinhVien = maSinhVien;
        this.nganhHoc = nganhHoc;
        this.diemTrungBinh = diemTrungBinh;
    }

    // Getter & Setter riêng
    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getNganhHoc() {
        return nganhHoc;
    }

    public void setNganhHoc(String nganhHoc) {
        this.nganhHoc = nganhHoc;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    // Xếp loại học lực dựa trên điểm trung bình
    public String xepLoai() {
        if (diemTrungBinh >= 8.5) {
            return "Giỏi";
        } else if (diemTrungBinh >= 7.0) {
            return "Khá";
        } else if (diemTrungBinh >= 5.0) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }

    // Ghi đè hienThiThongTin, tận dụng lại thông tin chung của lớp cha
    @Override
    public void hienThiThongTin() {
        System.out.print("[SINH VIEN] ");
        super.hienThiThongTin();
        System.out.printf("           Mã SV: %-8s | Ngành: %-15s | ĐTB: %-4.2f | Xếp loại: %s%n",
                maSinhVien, nganhHoc, diemTrungBinh, xepLoai());
    }
}
