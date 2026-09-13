package bai1;

public class HVuong {
    private double canh;

    public HVuong() {
        this.canh = 0;
    }

    public HVuong(double canh) {
        this.canh = canh;
    }

    public double getCanh() {
        return canh;
    }

    public void setCanh(double canh) {
        this.canh = canh;
    }

    public double tinhChuVi() {
        return canh * 4;
    }

    public double tinhDienTich() {
        return canh * canh;
    }

    public void xuatThongTin() {
        System.out.printf("Hinh vuong: canh=%.2f, chuVi=%.2f, dienTich=%.2f%n",
                canh, tinhChuVi(), tinhDienTich());
    }
}
