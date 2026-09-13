package bai7;

public class Triangle implements Polygon {
    protected double canhA, canhB, canhC;

    public Triangle() {
        this.canhA = 0;
        this.canhB = 0;
        this.canhC = 0;
    }

    public Triangle(double canhA, double canhB, double canhC) {
        this.canhA = canhA;
        this.canhB = canhB;
        this.canhC = canhC;
    }

    public double getCanhA() { return canhA; }
    public double getCanhB() { return canhB; }
    public double getCanhC() { return canhC; }

    public void setCanhA(double canhA) { this.canhA = canhA; }
    public void setCanhB(double canhB) { this.canhB = canhB; }
    public void setCanhC(double canhC) { this.canhC = canhC; }

    @Override
    public double perimeter() {
        return canhA + canhB + canhC;
    }
    @Override
    public double area() {
        double p = perimeter() / 2;
        double bp = p * (p - canhA) * (p - canhB) * (p - canhC);
        if (bp < 0) return 0;
        return Math.sqrt(bp);
    }

    @Override
    public String toString() {
        return String.format("%s: canhA=%.2f, canhB=%.2f, canhC=%.2f, chuVi=%.2f, dienTich=%.2f",
                getClass().getSimpleName(), canhA, canhB, canhC, perimeter(), area());
    }
}
