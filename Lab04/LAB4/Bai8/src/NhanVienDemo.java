import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class NhanVienDemo {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // B1. Nap driver + tao ket noi (SQLite khong can username/password)
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:nhanvien.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Ket noi SQLite thanh cong!");

            // B2. Tao Statement
            stmt = conn.createStatement();

            // B3.1 Tao bang (xoa bang cu de chay lai nhieu lan khong bi loi)
            stmt.executeUpdate("DROP TABLE IF EXISTS NhanVien");
            String sql_create_table =
                    "CREATE TABLE NhanVien ("
                  + " id INTEGER PRIMARY KEY,"
                  + " ten TEXT NOT NULL,"
                  + " chuc_vu TEXT,"
                  + " luong REAL)";            // cot luong dung cho Bai 5
            stmt.executeUpdate(sql_create_table);
            System.out.println("Da tao bang NhanVien.");

            // B3.2 Them du lieu - dung TRANSACTION: tat ca thanh cong moi commit
            conn.setAutoCommit(false);
            try {
                String sql_insert1 = "INSERT INTO NhanVien(id, ten, chuc_vu, luong) VALUES (1, 'Nguyen Van A', 'Giam doc', 30000000)";
                String sql_insert2 = "INSERT INTO NhanVien(id, ten, chuc_vu, luong) VALUES (2, 'Tran Thi B', 'Ke toan', 15000000)";
                String sql_insert3 = "INSERT INTO NhanVien(id, ten, chuc_vu, luong) VALUES (3, 'Le Van C', 'Lap trinh vien', 20000000)";
                String sql_insert4 = "INSERT INTO NhanVien(id, ten, chuc_vu, luong) VALUES (4, 'Pham Thi D', 'Lap trinh vien', 18000000)";
                String sql_insert5 = "INSERT INTO NhanVien(id, ten, chuc_vu, luong) VALUES (5, 'Hoang Van E', 'Ke toan', 12000000)";

                stmt.executeUpdate(sql_insert1);
                stmt.executeUpdate(sql_insert2);
                stmt.executeUpdate(sql_insert3);
                stmt.executeUpdate(sql_insert4);
                stmt.executeUpdate(sql_insert5);

                conn.commit();   // moi thao tac thanh cong -> luu
                System.out.println("Da them 5 nhan vien (commit).");
            } catch (SQLException ex) {
                conn.rollback(); // co loi -> huy toan bo thay doi
                System.out.println("Co loi, da rollback: " + ex.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

            // B4. Truy van va hien thi du lieu
            String sql_select = "SELECT id, ten, chuc_vu, luong FROM NhanVien";
            rs = stmt.executeQuery(sql_select);
            System.out.println("\n--- DANH SACH NHAN VIEN ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String chucVu = rs.getString("chuc_vu");
                double luong = rs.getDouble("luong");
                System.out.printf("ID: %d | Ten: %-15s | Chuc vu: %-15s | Luong: %,.0f%n",
                        id, ten, chucVu, luong);
            }
            rs.close(); // dong rs truoc khi dung stmt cho truy van khac

            // Bai 5. Ham tong hop
            System.out.println("\n--- THONG KE LUONG ---");
            rs = stmt.executeQuery("SELECT SUM(luong) AS tong, AVG(luong) AS tb, MIN(luong) AS thap, MAX(luong) AS cao FROM NhanVien");
            if (rs.next()) {
                System.out.printf("Tong luong       : %,.0f%n", rs.getDouble("tong"));
                System.out.printf("Luong trung binh : %,.0f%n", rs.getDouble("tb"));
                System.out.printf("Luong thap nhat  : %,.0f%n", rs.getDouble("thap"));
                System.out.printf("Luong cao nhat   : %,.0f%n", rs.getDouble("cao"));
            }
            rs.close();

            // Nhan vien luong cao nhat / thap nhat
            rs = stmt.executeQuery("SELECT ten, luong FROM NhanVien WHERE luong = (SELECT MAX(luong) FROM NhanVien)");
            while (rs.next()) {
                System.out.println("NV luong cao nhat: " + rs.getString("ten"));
            }
            rs.close();

            rs = stmt.executeQuery("SELECT ten, luong FROM NhanVien WHERE luong = (SELECT MIN(luong) FROM NhanVien)");
            while (rs.next()) {
                System.out.println("NV luong thap nhat: " + rs.getString("ten"));
            }
            rs.close();

            // Dem so nhan vien theo chuc vu
            System.out.println("\n--- SO NHAN VIEN THEO CHUC VU ---");
            rs = stmt.executeQuery("SELECT chuc_vu, COUNT(*) AS so_luong FROM NhanVien GROUP BY chuc_vu");
            while (rs.next()) {
                System.out.println(rs.getString("chuc_vu") + ": " + rs.getInt("so_luong"));
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Khong tim thay driver SQLite (chua them file .jar?): " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Loi SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // B5. Dong tai nguyen theo thu tu nguoc lai: rs -> stmt -> conn
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            System.out.println("\nDa dong ket noi.");
        }
    }
}
