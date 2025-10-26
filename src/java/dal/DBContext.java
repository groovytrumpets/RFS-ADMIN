package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    // --- Cấu hình cho MySQL ---

    // 1. SỬA URL: 
    //    - Dùng "jdbc:mysql://" (thay vì "jdbc:sqlserver://")
    //    - Dùng port 3306 (là port mặc định của MySQL, thay vì 1433)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rfs"; // Giả sử database của bạn tên là EPMS
    
    private static final String DB_USER = "root"; // Tên user MySQL
    private static final String DB_PASS = "Khanh20041011"; // Mật khẩu của user đó

    // 2. SỬA DRIVER:
    //    - Dùng "com.mysql.cj.jdbc.Driver" (cho bản 8+)
    //    - (thay vì "com.microsoft.sqlserver.jdbc.SQLServerDriver")
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Phương thức tĩnh (static) để lấy kết nối.
     * Các Servlet sẽ gọi trực tiếp hàm này: DBContext.getConnection()
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Nạp driver
        Class.forName(DB_DRIVER);
        // Trả về kết nối
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // Hàm main để test nhanh kết nối
    public static void main(String[] args) {
        try {
            Connection conn = DBContext.getConnection(); // Gọi hàm static
            
            if (conn != null) {
                System.out.println("Kết nối MySQL thành công!");
                conn.close(); // Luôn đóng kết nối sau khi sử dụng xong
            } else {
                System.out.println("Kết nối MySQL thất bại!");
            }
        } catch (Exception e) {
            System.out.println("Lỗi kết nối:");
            e.printStackTrace();
        }
    }
}