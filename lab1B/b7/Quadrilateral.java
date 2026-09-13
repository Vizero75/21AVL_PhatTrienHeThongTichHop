package bai7;

public class Quadrilateral implements Polygon {
    protected double canhA, canhB, canhC, canhD;

    public Quadrilateral() {
        this.canhA = 0;
        this.canhB = 0;
        this.canhC = 0;
        this.canhD = 0;
    }

    public Quadrilateral(double canhA, double canhB, double canhC, double canhD) {
        this.canhA = canhA;
        this.canhB = canhB;
        this.canhC = canhC;
        this.canhD = canhD;
    }

    public double getCanhA() { return canhA; }
    public double getCanhB() { return canhB; }
    public double getCanhC() { return canhC; }
    public double getCanhD() { return canhD; }

    public void setCanhA(double canhA) { this.canhA = canhA; }
    public void setCanhB(double canhB) { this.canhB = canhB; }
    public void setCanhC(double canhC) { this.canhC = canhC; }
    public void setCanhD(double canhD) { this.canhD = canhD; }

    @Override
    public double perimeter() {
        return canhA + canhB + canhC + canhD;
    }

    @Override
    public double area() {
        System.out.println("Canh bao: khong the tinh chinh xac dien tich tu giac thuong chi voi 4 canh.");
        return 0;
    }

    @Override
    public String toString() {
        return String.format("%s: canhA=%.2f, canhB=%.2f, canhC=%.2f, canhD=%.2f, chuVi=%.2f, dienTich=%.2f",
                getClass().getSimpleName(), canhA, canhB, canhC, canhD, perimeter(), area());
    }
}
