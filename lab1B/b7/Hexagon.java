package bai7;
public class Hexagon implements Polygon {
    protected double canh;
    private static final int SO_CANH = 6;

    public Hexagon() {
        this.canh = 0;
    }

    public Hexagon(double canh) {
        this.canh = canh;
    }

    public double getCanh() { return canh; }
    public void setCanh(double canh) { this.canh = canh; }

    @Override
    public double perimeter() {
        return SO_CANH * canh;
    }

    @Override
    public double area() {
        return (SO_CANH * canh * canh) / (4 * Math.tan(Math.PI / SO_CANH));
    }

    @Override
    public String toString() {
        return String.format("%s: canh=%.2f, chuVi=%.2f, dienTich=%.2f",
                getClass().getSimpleName(), canh, perimeter(), area());
    }
}
