import java.io.File;
import java.util.Scanner;

public class DeleteFile {

    public void finFile(String source, String key) {

        File file = new File(source);

        if (file.exists()) {

            if (file.isFile()) {

                if (file.getName().endsWith(key)) {
                    System.out.println(file.getAbsolutePath());
                }

            } else if (file.isDirectory()) {

                File[] listFile = file.listFiles();

                if (listFile != null) {
                    for (File f : listFile) {
                        finFile(f.getAbsolutePath(), key);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Nhap duong dan: ");
        String source = sc.nextLine();

        System.out.print("Nhap duoi file can tim: ");
        String key = sc.nextLine();

        DeleteFile df = new DeleteFile();

        df.finFile(source, key);

        sc.close();
    }
}