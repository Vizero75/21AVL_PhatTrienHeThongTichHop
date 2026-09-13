package bai7;

import java.util.Scanner;

public class Bai7Demo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean tiepTuc = true;

        while (tiepTuc) {
            hienThiMenu();
            System.out.print("Chon loai da giac (0 de thoat): ");
            int luaChon = Integer.parseInt(sc.nextLine().trim());

            Polygon polygon = null;

            switch (luaChon) {
                case 0:
                    tiepTuc = false;
                    continue;
                case 1: { // Triangle
                    System.out.print("Nhap 3 canh (a b c) cach nhau khoang trang: ");
                    double[] s = docBaSo(sc);
                    polygon = new Triangle(s[0], s[1], s[2]);
                    break;
                }
                case 2: { // IsoscelesTriangle
                    System.out.print("Nhap canh ben va canh day (canhBen canhDay): ");
                    double[] s = docHaiSo(sc);
                    polygon = new IsoscelesTriangle(s[0], s[1]);
                    break;
                }
                case 3: { // EquilateralTriangle
                    System.out.print("Nhap do dai canh: ");
                    double canh = Double.parseDouble(sc.nextLine().trim());
                    polygon = new EquilateralTriangle(canh);
                    break;
                }
                case 4: { // Quadrilateral
                    System.out.print("Nhap 4 canh (a b c d): ");
                    double[] s = docBonSo(sc);
                    polygon = new Quadrilateral(s[0], s[1], s[2], s[3]);
                    break;
                }
                case 5: { // Rectangle
                    System.out.print("Nhap chieu dai va chieu rong (dai rong): ");
                    double[] s = docHaiSo(sc);
                    polygon = new Rectangle(s[0], s[1]);
                    break;
                }
                case 6: { // Square
                    System.out.print("Nhap do dai canh: ");
                    double canh = Double.parseDouble(sc.nextLine().trim());
                    polygon = new Square(canh);
                    break;
                }
                case 7: { // Pentagon
                    System.out.print("Nhap do dai canh: ");
                    double canh = Double.parseDouble(sc.nextLine().trim());
                    polygon = new Pentagon(canh);
                    break;
                }
                case 8: { // Hexagon
                    System.out.print("Nhap do dai canh: ");
                    double canh = Double.parseDouble(sc.nextLine().trim());
                    polygon = new Hexagon(canh);
                    break;
                }
                case 9: { // Octagon
                    System.out.print("Nhap do dai canh: ");
                    double canh = Double.parseDouble(sc.nextLine().trim());
                    polygon = new Octagon(canh);
                    break;
                }
                default:
                    System.out.println("Lua chon khong hop le!");
                    continue;
            }

            System.out.println("\n>> Ket qua:");
            System.out.println(polygon.toString());
            System.out.printf(">> Chu vi (theo interface Polygon): %.2f%n", polygon.perimeter());
            System.out.printf(">> Dien tich (theo interface Polygon): %.2f%n%n", polygon.area());
        }

        System.out.println("Ket thuc chuong trinh.");
        sc.close();
    }

    private static void hienThiMenu() {
        System.out.println("===== TAO DA GIAC =====");
        System.out.println("1. Tam giac thuong (Triangle)");
        System.out.println("2. Tam giac can (IsoscelesTriangle)");
        System.out.println("3. Tam giac deu (EquilateralTriangle)");
        System.out.println("4. Tu giac thuong (Quadrilateral)");
        System.out.println("5. Hinh chu nhat (Rectangle)");
        System.out.println("6. Hinh vuong (Square)");
        System.out.println("7. Ngu giac deu (Pentagon)");
        System.out.println("8. Luc giac deu (Hexagon)");
        System.out.println("9. Bat giac deu (Octagon)");
        System.out.println("0. Thoat");
    }

    private static double[] docHaiSo(Scanner sc) {
        String[] parts = sc.nextLine().trim().split("\\s+");
        return new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1])};
    }

    private static double[] docBaSo(Scanner sc) {
        String[] parts = sc.nextLine().trim().split("\\s+");
        return new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2])};
    }

    private static double[] docBonSo(Scanner sc) {
        String[] parts = sc.nextLine().trim().split("\\s+");
        return new double[]{Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]), Double.parseDouble(parts[3])};
    }
}
