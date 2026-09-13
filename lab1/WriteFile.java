import java.io.*;
import java.util.*;

public class WriteFile {
    public static void main(String[] args) throws Exception {

        String src = "sinhvien.dat";

        List<SinhVien> listSV = new ArrayList<>();

        listSV.add(new SinhVien("SV01", "An", 20));
        listSV.add(new SinhVien("SV02", "Binh", 21));

        DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(src));

        dos.writeInt(listSV.size());

        for (SinhVien sv : listSV) {
            dos.writeUTF(sv.getMssv());
            dos.writeUTF(sv.getTen());
            dos.writeInt(sv.getTuoi());
        }

        dos.close();

        System.out.println("Ghi file thanh cong!");
    }
}

class SinhVien {
    private String mssv;
    private String ten;
    private int tuoi;

    public SinhVien(String mssv, String ten, int tuoi) {
        this.mssv = mssv;
        this.ten = ten;
        this.tuoi = tuoi;
    }

    public String getMssv() {
        return mssv;
    }

    public String getTen() {
        return ten;
    }

    public int getTuoi() {
        return tuoi;
    }
}