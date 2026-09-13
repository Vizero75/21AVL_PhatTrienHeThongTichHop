
import java.time.Year;

public class Nguoi {

    private String hoTen;
    private int namSinh;
    private String diaChi;

    // Constructor đầy đủ tham số
    public Nguoi(String hoTen, int namSinh, String diaChi) {
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.diaChi = diaChi;
    }

    // Getter & Setter
    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(int namSinh) {
        this.namSinh = namSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Tính tuổi dựa trên năm hiện tại
    public int tinhTuoi() {
        int namHienTai = Year.now().getValue();
        return namHienTai - namSinh;
    }

    // Hiển thị thông tin chung
    public void hienThiThongTin() {
        System.out.printf("Họ tên: %-15s | Năm sinh: %-5d | Tuổi: %-3d | Địa chỉ: %s%n",
                hoTen, namSinh, tinhTuoi(), diaChi);
    }
}
