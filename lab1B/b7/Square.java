package bai7;
public class Square extends Rectangle {

    public Square() {
        super();
    }

    public Square(double canh) {
        super(canh, canh);
    }

    public double getCanh() {
        return getChieuDai();
    }

    public void setCanh(double canh) {
        setChieuDai(canh);
        setChieuRong(canh);
    }
}
