package bai1;

public class HCN {
    private double chieuDai;
    private double chieuRong;

    // init contructor set default
    public HCN() {
        this.chieuDai = 0;
        this.chieuRong = 0;
    }

    // init contructor set value
    public HCN(double chieuDai, double chieuRong) {
        this.chieuDai = chieuDai;
        this.chieuRong = chieuRong;
    }

    // Getter
    public double getChieuDai() {
        return chieuDai;
    }

    public double getChieuRong() {
        return chieuRong;
    }

    // Setter
    public void setChieuDai(double chieuDai) {
        this.chieuDai = chieuDai;
    }

    public void setChieuRong(double chieuRong) {
        this.chieuRong = chieuRong;
    }

    // C = ?
    public double tinhChuVi() {
        return (chieuDai + chieuRong) * 2;
    }

    // S = ?
    public double tinhDienTich() {
        return chieuDai * chieuRong;
    }

    // println
    public void xuatThongTin() {
        System.out.printf("Hinh chu nhat: chieuDai=%.2f, chieuRong=%.2f, chuVi=%.2f, dienTich=%.2f%n",
                chieuDai, chieuRong, tinhChuVi(), tinhDienTich());
    }
}
