import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * LAB 08 - JDBC: BAI TAP GOI Y 1 -> 4
 *  1. CRUD bang SanPham (maSP, tenSP, gia, soluong)
 *  2. Transaction khi cap nhat du lieu
 *  3. try-catch-finally nghiem ngat, dong moi tai nguyen trong finally
 *  4. Dung PreparedStatement (chong SQL Injection)
 */
public class SanPhamApp {

    private static final String URL = "jdbc:sqlite:sanpham.db";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Ham tien ich: dong tai nguyen an toan (dung trong finally)
    private static void close(AutoCloseable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                System.out.println("Loi khi dong tai nguyen: " + e.getMessage());
            }
        }
    }

    // ===================== KHOI TAO BANG + DU LIEU MAU =====================
    private static void khoiTao() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS SanPham ("
              + " maSP INTEGER PRIMARY KEY AUTOINCREMENT,"
              + " tenSP TEXT NOT NULL,"
              + " gia REAL NOT NULL,"
              + " soluong INTEGER NOT NULL DEFAULT 0)");

            rs = stmt.executeQuery("SELECT COUNT(*) FROM SanPham");
            boolean rong = rs.next() && rs.getInt(1) == 0;
            close(rs);
            rs = null;

            if (rong) { // bang trong -> them 3 san pham mau
                them(conn, "Laptop Dell", 15000000, 10);
                them(conn, "Chuot Logitech", 350000, 50);
                them(conn, "Ban phim co", 900000, 30);
                System.out.println("Da them 3 san pham mau.");
            }
        } catch (SQLException e) {
            System.out.println("Loi khoi tao: " + e.getMessage());
        } finally {
            close(rs);
            close(stmt);
            close(conn);
        }
    }

    // ===================== CREATE =====================
    private static void them(Connection conn, String tenSP, double gia, int soluong) throws SQLException {
        String sql = "INSERT INTO SanPham(tenSP, gia, soluong) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenSP);
            ps.setDouble(2, gia);
            ps.setInt(3, soluong);
            ps.executeUpdate();
        } finally {
            close(ps);
        }
    }

    private static void themSanPham(String tenSP, double gia, int soluong) {
        Connection conn = null;
        try {
            conn = getConnection();
            them(conn, tenSP, gia, soluong);
            System.out.println("Da them san pham: " + tenSP);
        } catch (SQLException e) {
            System.out.println("Loi them san pham: " + e.getMessage());
        } finally {
            close(conn);
        }
    }

    // ===================== READ =====================
    private static void inTatCa() {
        timKiem(null);
    }

    /** keyword = null -> liet ke tat ca; nguoc lai tim theo tenSP (LIKE) */
    private static void timKiem(String keyword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            if (keyword == null) {
                ps = conn.prepareStatement("SELECT maSP, tenSP, gia, soluong FROM SanPham ORDER BY maSP");
            } else {
                ps = conn.prepareStatement("SELECT maSP, tenSP, gia, soluong FROM SanPham WHERE tenSP LIKE ?");
                ps.setString(1, "%" + keyword + "%");
            }
            rs = ps.executeQuery();

            System.out.println("\nMaSP | Ten San Pham        |         Gia | So luong");
            System.out.println("-------------------------------------------------------");
            boolean coDuLieu = false;
            while (rs.next()) {
                coDuLieu = true;
                System.out.printf("%-4d | %-19s | %,11.0f | %d%n",
                        rs.getInt("maSP"), rs.getString("tenSP"),
                        rs.getDouble("gia"), rs.getInt("soluong"));
            }
            if (!coDuLieu) System.out.println("(Khong co san pham nao)");
        } catch (SQLException e) {
            System.out.println("Loi truy van: " + e.getMessage());
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
    }

    // ===================== UPDATE (co transaction) =====================
    private static void capNhat(int maSP, double giaMoi, int soluongMoi) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);           // bat dau transaction

            ps = conn.prepareStatement("UPDATE SanPham SET gia = ?, soluong = ? WHERE maSP = ?");
            ps.setDouble(1, giaMoi);
            ps.setInt(2, soluongMoi);
            ps.setInt(3, maSP);
            int n = ps.executeUpdate();

            if (n == 0) {
                throw new SQLException("Khong tim thay san pham co maSP = " + maSP);
            }
            conn.commit();                       // thanh cong -> luu
            System.out.println("Da cap nhat san pham " + maSP);
        } catch (SQLException e) {
            System.out.println("Loi cap nhat: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();             // loi -> huy thay doi
                    System.out.println("Da rollback.");
                }
            } catch (SQLException ex) {
                System.out.println("Loi rollback: " + ex.getMessage());
            }
        } finally {
            close(ps);
            close(conn);
        }
    }

    /** Giam gia X% cho TAT CA san pham trong 1 transaction (hoac cung thanh cong, hoac khong gi ca) */
    private static void giamGiaTatCa(double phanTram) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            if (phanTram <= 0 || phanTram >= 100) {
                throw new SQLException("Phan tram giam phai trong khoang (0, 100)");
            }
            conn = getConnection();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement("UPDATE SanPham SET gia = gia * (1 - ? / 100.0)");
            ps.setDouble(1, phanTram);
            int n = ps.executeUpdate();

            conn.commit();
            System.out.println("Da giam gia " + phanTram + "% cho " + n + " san pham.");
        } catch (SQLException e) {
            System.out.println("Loi giam gia: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Loi rollback: " + ex.getMessage());
            }
        } finally {
            close(ps);
            close(conn);
        }
    }

    // ===================== DELETE =====================
    private static void xoa(int maSP) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement("DELETE FROM SanPham WHERE maSP = ?");
            ps.setInt(1, maSP);
            int n = ps.executeUpdate();
            System.out.println(n > 0 ? "Da xoa san pham " + maSP : "Khong tim thay maSP = " + maSP);
        } catch (SQLException e) {
            System.out.println("Loi xoa: " + e.getMessage());
        } finally {
            close(ps);
            close(conn);
        }
    }

    // ===================== MENU =====================
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Chua them driver sqlite-jdbc vao du an: " + e.getMessage());
            return;
        }

        khoiTao();

        Scanner sc = new Scanner(System.in);
        int chon;
        do {
            System.out.println("\n===== QUAN LY SAN PHAM =====");
            System.out.println("1. Xem tat ca san pham");
            System.out.println("2. Them san pham");
            System.out.println("3. Tim theo ten");
            System.out.println("4. Cap nhat gia/so luong theo ma");
            System.out.println("5. Xoa san pham theo ma");
            System.out.println("6. Giam gia tat ca san pham (transaction)");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");

            try {
                chon = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                chon = -1;
            }

            try {
                switch (chon) {
                    case 1:
                        inTatCa();
                        break;
                    case 2:
                        System.out.print("Ten SP: ");
                        String ten = sc.nextLine();
                        System.out.print("Gia: ");
                        double gia = Double.parseDouble(sc.nextLine());
                        System.out.print("So luong: ");
                        int sl = Integer.parseInt(sc.nextLine());
                        themSanPham(ten, gia, sl);
                        break;
                    case 3:
                        System.out.print("Tu khoa ten SP: ");
                        timKiem(sc.nextLine());
                        break;
                    case 4:
                        System.out.print("Ma SP can sua: ");
                        int ma = Integer.parseInt(sc.nextLine());
                        System.out.print("Gia moi: ");
                        double giaMoi = Double.parseDouble(sc.nextLine());
                        System.out.print("So luong moi: ");
                        int slMoi = Integer.parseInt(sc.nextLine());
                        capNhat(ma, giaMoi, slMoi);
                        break;
                    case 5:
                        System.out.print("Ma SP can xoa: ");
                        xoa(Integer.parseInt(sc.nextLine()));
                        break;
                    case 6:
                        System.out.print("Giam bao nhieu %: ");
                        giamGiaTatCa(Double.parseDouble(sc.nextLine()));
                        break;
                    case 0:
                        System.out.println("Tam biet!");
                        break;
                    default:
                        System.out.println("Lua chon khong hop le.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ban phai nhap so hop le!");
            }
        } while (chon != 0);

        sc.close();
    }
}
