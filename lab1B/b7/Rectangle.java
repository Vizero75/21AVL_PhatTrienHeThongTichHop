package bai7;

public class Rectangle extends Quadrilateral {

    public Rectangle() {
        super();
    }

    public Rectangle(double chieuDai, double chieuRong) {
        super(chieuDai, chieuRong, chieuDai, chieuRong);
    }

    public double getChieuDai() {
        return canhA;
    }

    public double getChieuRong() {
        return canhB;
    }

    public void setChieuDai(double chieuDai) {
        this.canhA = chieuDai;
        this.canhC = chieuDai;
    }

    public void setChieuRong(double chieuRong) {
        this.canhB = chieuRong;
        this.canhD = chieuRong;
    }

    @Override
    public double area() {
        return getChieuDai() * getChieuRong();
    }
}
