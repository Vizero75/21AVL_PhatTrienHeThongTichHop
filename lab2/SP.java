// Mục tiêu
// - Hiểu ý nghĩa của tính đóng gói.
// - Sử dụng private, constructor, getter và phương thức nghiệp vụ.
// - Kiểm soát dữ liệu trước khi thay đổi trạng thái đối tượng.
// Mô tả:
// Xây dựng lớp SanPham để quản lý sản phẩm trong cửa hàng. Mỗi sản phẩm có:
// - Mã sản phẩm.
// - Tên sản phẩm.
// - Đơn giá.
// - Số lượng tồn kho.
// Yêu cầu:
// 1. Xây dựng lớp SanPham
// - Xây dựng constructor đầy đủ tham số
// 2. Xây dựng các phương thức:
// - public double tinhThanhTien() :
// thanhTien = donGia * soLuong
// - public void nhapHang(int soLuongNhap)
// +số lượng nhập phải lớn hơn 0
// +cộng số lượng nhập vào tồn kho
// - public boolean banHang(int soLuongBan)
// +Số lượng bán phải lớn hơn 0.
// +Không được bán quá số lượng tồn kho.
// +Nếu bán thành công, cập nhật số lượng và trả về true.
// +Nếu không đủ hàng, giữ nguyên số lượng và trả về false.
// - public void hienThiThongTin()
// +hiển thị mã, tên, đơn giá, số lượng và thành tiền.

// 3. Chương trình chính
// Trong Main.java:
// - Tạo ít nhất hai sản phẩm.
// - Nhập thêm hàng cho một sản phẩm.
// - Thử bán hàng thành công.
// - Thử bán số lượng lớn hơn tồn kho.
// - Hiển thị thông tin trước và sau mỗi thao tác.
public class SP {

    private String masp;
    private String tensp;
    private double dongia;
    private int tonkho;

    public SP(String masp, String tensp, double dongia, int tonkho) {
        this.masp = masp;
        this.tensp = tensp;
        this.dongia = dongia;
        this.tonkho = tonkho;
    }

    /**
     * @return String return the masp
     */
    public String getMasp() {
        return masp;
    }

    /**
     * @param masp the masp to set
     */
    public void setMasp(String masp) {
        this.masp = masp;
    }

    /**
     * @return String return the tensp
     */
    public String getTensp() {
        return tensp;
    }

    /**
     * @param tensp the tensp to set
     */
    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    /**
     * @return double return the dongia
     */
    public double getDongia() {
        return dongia;
    }

    /**
     * @param dongia the dongia to set
     */
    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

    /**
     * @return int return the tonkho
     */
    public int getTonkho() {
        return tonkho;
    }

    /**
     * @param tonkho the tonkho to set
     */
    public void setTonkho(int tonkho) {
        this.tonkho = tonkho;
    }

    public double tinhThanhTien() {
        return this.dongia * this.tonkho;
    }

    // - public void nhapHang(int soLuongNhap)
    // +số lượng nhập phải lớn hơn 0
    // +cộng số lượng nhập vào tồn kho
    public void nhapHang(int soLuongNhap) {
        if (soLuongNhap < 0) {
            System.out.println("Khong hop le");
        } else {
            this.tonkho += soLuongNhap;
            System.out.println("Da them hang vao kho");
        }
    }

    // - public boolean banHang(int soLuongBan)
    // +Số lượng bán phải lớn hơn 0.
    // +Không được bán quá số lượng tồn kho.
    // +Nếu bán thành công, cập nhật số lượng và trả về true.
    // +Nếu không đủ hàng, giữ nguyên số lượng và trả về false.
    public boolean banHang(int soLuongBan) {
        if (soLuongBan <= 0) {
            System.out.println("Khong hop le");
        }
        if (soLuongBan > this.tonkho) {
            System.out.println("Khong hop le");
        }
    }
}
