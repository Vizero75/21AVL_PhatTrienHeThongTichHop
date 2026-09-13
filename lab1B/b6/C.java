package bai6;

public class C extends B {
    int x;

    public C() {
        super();
        this.x = 3;
    }

    public void setAX(int value) {
        ((A) this).x = value;
    }

    public int getAX() {
        return ((A) this).x;
    }

    public int getBX() {
        return ((B) this).x;
    }

    public int getCX() {
        return this.x;
    }

    public void inThongTin() {
        System.out.println("A.x = " + getAX());
        System.out.println("B.x = " + getBX());
        System.out.println("C.x = " + getCX());
    }
}
