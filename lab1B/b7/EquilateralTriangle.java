package bai7;

// Tam giác đều: cả 3 cạnh bằng nhau => là trường hợp đặc biệt của tam giác cân
public class EquilateralTriangle extends IsoscelesTriangle {

    public EquilateralTriangle() {
        super();
    }

    public EquilateralTriangle(double canh) {
        super(canh, canh);
    }

    public double getCanh() {
        return canhA;
    }

    public void setCanh(double canh) {
        this.canhA = canh;
        this.canhB = canh;
        this.canhC = canh;
    }
}
