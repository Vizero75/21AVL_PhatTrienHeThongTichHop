
public class SanPham {

    private String maSanPham;
    private String tenSanPham;
    private double donGia;
    private int soLuongTon;

    public SanPham(String maSanPham, String tenSanPham, double donGia, int soLuongTon) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soLuongTon = soLuongTon;
    }

    // Getter - chỉ cho phép đọc dữ liệu, không cho sửa trực tiếp
    public String getMaSanPham() {
        return maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    // Tính thành tiền = đơn giá * số lượng tồn
    public double tinhThanhTien() {
        return donGia * soLuongTon;
    }

    // Nhập thêm hàng vào kho
    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap <= 0) {
            System.out.println("Số lượng nhập không hợp lệ! Phải lớn hơn 0.");
            return;
        }
        soLuongTon += soLuongNhap;
        System.out.println("Nhập hàng thành công: +" + soLuongNhap + " sản phẩm \"" + tenSanPham + "\".");
    }

    // Bán hàng, trả về true nếu thành công, false nếu không đủ hàng
    public boolean banHang(int soLuongBan) {
        if (soLuongBan <= 0) {
            System.out.println("Số lượng bán không hợp lệ! Phải lớn hơn 0.");
            return false;
        }
        if (soLuongBan > soLuongTon) {
            System.out.println("Không đủ hàng để bán! Tồn kho hiện tại: " + soLuongTon);
            return false;
        }
        soLuongTon -= soLuongBan;
        System.out.println("Bán hàng thành công: -" + soLuongBan + " sản phẩm \"" + tenSanPham + "\".");
        return true;
    }

    // Hiển thị thông tin sản phẩm
    public void hienThiThongTin() {
        System.out.printf("Mã: %-6s | Tên: %-15s | Đơn giá: %,.0f | Tồn kho: %-5d | Thành tiền: %,.0f%n",
                maSanPham, tenSanPham, donGia, soLuongTon, tinhThanhTien());
    }
}
