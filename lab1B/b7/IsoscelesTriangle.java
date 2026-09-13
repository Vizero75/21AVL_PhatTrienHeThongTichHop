package bai7;

public class IsoscelesTriangle extends Triangle {

    public IsoscelesTriangle() {
        super();
    }

    public IsoscelesTriangle(double canhBen, double canhDay) {
        super(canhBen, canhBen, canhDay);
    }

    public double getCanhBen() {
        return canhA; // canhA == canhB
    }

    public double getCanhDay() {
        return canhC;
    }

    public void setCanhBen(double canhBen) {
        this.canhA = canhBen;
        this.canhB = canhBen;
    }

    public void setCanhDay(double canhDay) {
        this.canhC = canhDay;
    }
}
