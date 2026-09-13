package bai1;

public class DemoHinh {
    public static void main(String[] args) {
        System.out.println("===== DEMO HINH CHU NHAT =====");
        HCN hcn1 = new HCN();
        hcn1.xuatThongTin();
        HCN hcn2 = new HCN(5, 3);
        hcn2.xuatThongTin();

        System.out.println("\n===== DEMO HINH VUONG =====");
        HVuong hv1 = new HVuong();
        hv1.xuatThongTin();
        HVuong hv2 = new HVuong(4);
        hv2.xuatThongTin();

        System.out.println("\n===== DEMO HINH TAM GIAC =====");
        HTG tg1 = new HTG();
        tg1.xuatThongTin();
        HTG tg2 = new HTG(3, 4, 5);
        tg2.xuatThongTin();
    }
}
