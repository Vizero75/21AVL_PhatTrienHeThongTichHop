import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {

    public static void main(String[] args) {

        String sourceFile = "Main1.java";
        String destFile = "Main1_copy.java";

        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            byte[] arr = new byte[1024];

            int n;

            while ((n = fis.read(arr)) != -1) {
                fos.write(arr, 0, n);
            }

            fis.close();
            fos.close();

            System.out.println("Copy file thanh cong!");

        } catch (IOException e) {
            System.out.println("Co loi: " + e.getMessage());
        }
    }
}