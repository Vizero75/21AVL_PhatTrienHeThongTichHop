package bai1;

public class HTG {
    private double canhA, canhB, canhC;

    public HTG() {
        this.canhA = 0;
        this.canhB = 0;
        this.canhC = 0;
    }

    public HTG(double canhA, double canhB, double canhC) {
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

    // check 3 canh isvalid ?
    public boolean isHopLe() {
        return canhA + canhB > canhC && canhA + canhC > canhB && canhB + canhC > canhA;
    }

    public double tinhChuVi() {
        return canhA + canhB + canhC;
    }

    // S = ?
    public double tinhDienTich() {
        if (!isHopLe()) return 0;
        double p = tinhChuVi() / 2;
        return Math.sqrt(p * (p - canhA) * (p - canhB) * (p - canhC));
    }

    public void xuatThongTin() {
        if (!isHopLe()) {
            System.out.println("Hinh tam giac: 3 canh (" + canhA + ", " + canhB + ", " + canhC + ") khong hop le!");
            return;
        }
        System.out.printf("Hinh tam giac: a=%.2f, b=%.2f, c=%.2f, chuVi=%.2f, dienTich=%.2f%n",
                canhA, canhB, canhC, tinhChuVi(), tinhDienTich());
    }
}
