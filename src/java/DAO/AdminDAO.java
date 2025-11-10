/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import dal.DBContext;
import java.util.ArrayList;
import java.util.List;
import model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Genre;
import model.Movie;
import java.sql.Statement; // Cần cho việc lấy ID
import java.sql.Types;    // Cần cho setNull
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import model.MovieList;
import model.Rating;
import model.Report;
import model.Translation;
// import java.util.ArrayList; // Bị lặp, đã có ở trên
// import java.util.List; // Bị lặp, đã có ở trên

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
// 1. KHÔNG CẦN "extends DBContext"
public class AdminDAO { 

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        
        user.setIdUser(rs.getInt("idUser"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setStatus(rs.getString("status"));
        user.setFullName(rs.getString("fullName"));
        user.setPhone(rs.getString("phone"));
        user.setCreateDate(rs.getTimestamp("create_date"));
        user.setDob(rs.getDate("dob"));
        user.setEmail(rs.getString("email"));
        user.setGender(rs.getString("gender"));
        user.setSlug(rs.getString("slug"));
        user.setRoleId(rs.getInt("Role_idRole"));
        
        return user;
    }
    public List<User> getUsersLast6Months() {
    List<User> userList = new ArrayList<>();

    // Lấy 6 tháng gần nhất (SQL tự tính bằng hàm DATEADD)
    String sql = "SELECT * FROM [User] " +
                 "WHERE create_date >= DATEADD(MONTH, -6, GETDATE()) " +
                 "ORDER BY create_date DESC";

    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            userList.add(extractUser(rs));
        }

    } catch (SQLException | ClassNotFoundException e) {
        System.out.println("Lỗi khi lấy danh sách người dùng 6 tháng gần đây: " + e.getMessage());
        e.printStackTrace();
    }

    return userList;
}
    public Map<String, Integer> getUserCountByMonthLast6Months() {
    Map<String, Integer> data = new LinkedHashMap<>();

    String sql = "SELECT DATE_FORMAT(create_date, '%Y-%m') AS month, COUNT(*) AS count "
               + "FROM `User` "
               + "WHERE create_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) "
               + "GROUP BY DATE_FORMAT(create_date, '%Y-%m') "
               + "ORDER BY month;";

    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            data.put(rs.getString("month"), rs.getInt("count"));
        }

    } catch (SQLException | ClassNotFoundException e) {
        System.out.println("Lỗi khi thống kê người dùng theo tháng: " + e.getMessage());
        e.printStackTrace();
    }

    // === Thêm các tháng còn thiếu (đảm bảo có đủ 6 tháng gần nhất) ===
    Map<String, Integer> fullData = new LinkedHashMap<>();
    LocalDate now = LocalDate.now();

    for (int i = 5; i >= 0; i--) {
        LocalDate month = now.minusMonths(i);
        String key = month.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        fullData.put(key, data.getOrDefault(key, 0)); // Nếu không có trong DB thì 0
    }

    return fullData;
}
    public Map<String, Integer> countUsersByStatus() {
        // Sử dụng LinkedHashMap để duy trì thứ tự (tùy chọn)
        Map<String, Integer> statusCounts = new LinkedHashMap<>();
        String sql = "SELECT status, COUNT(*) AS count FROM User GROUP BY status";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String status = rs.getString("status");
                int count = rs.getInt("count");
                statusCounts.put(status, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi phù hợp (ví dụ: log lỗi)
        }
        return statusCounts;
    }

    // 2. Bỏ "throws SQLException" vì chúng ta sẽ bắt lỗi (catch) bên trong
    public List<User> getAllUsers() {
        
        List<User> userList = new ArrayList<>();
        
        // `User` là từ khóa trong MySQL, cần bọc bằng dấu backtick `
        String sql = "SELECT * FROM `User`";
        try (Connection connection = DBContext.getConnection(); // <-- GỌI HÀM STATIC
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            

            while (rs.next()) {

                userList.add(extractUser(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách User: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi ra console
        }
        return userList;
    }
    public String getAllTransactions() {
        
        String transaction ="";
        
        // `User` là từ khóa trong MySQL, cần bọc bằng dấu backtick `
        String sql = "Select sum(amount) AS total_amount from ylvqitishosting_rfs.Transactions where status like 'completed';";
        try (Connection connection = DBContext.getConnection(); // <-- GỌI HÀM STATIC
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            

            if (rs.next()) {
            transaction = rs.getString("total_amount"); // ✅ Lấy giá trị từ cột alias
        }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách User: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi ra console
        }
        return transaction;
    }
    
    public boolean updateUser(int idUser, String username, String fullName, String email, String phone, String gender, String status) {
        
        // Câu lệnh SQL update
        String sql = "UPDATE `User` SET username = ?, fullName = ?, email = ?, "
                   + "phone = ?, gender = ?, status =? WHERE idUser = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            System.out.println(status);
            // Set các tham số cho câu lệnh SQL
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, gender);
            ps.setString(6, status);
            ps.setInt(7, idUser); // Tham số cuối cùng là idUser cho điều kiện WHERE
            
            // executeUpdate() trả về số dòng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();
            
            // Nếu có 1 dòng bị ảnh hưởng, tức là update thành công
            return rowsAffected > 0; 
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi cập nhật User (DAO): " + e.getMessage());
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
    
    public boolean insertUser(User newUser) {
        
        // Câu lệnh SQL không chứa idUser. 
        // create_date sẽ dùng hàm NOW() của MySQL.
        String sql = "INSERT INTO `User` "
                   + "(username, password, status, fullName, phone, email, gender, Role_idRole, create_date, dob, slug) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?)";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            // Set các tham số cho câu lệnh SQL
            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getPassword()); // !!! Cảnh báo: Nên mã hóa mật khẩu trước khi lưu
            ps.setString(3, newUser.getStatus());
            ps.setString(4, newUser.getFullName());
            ps.setString(5, newUser.getPhone());
            ps.setString(6, newUser.getEmail());
            ps.setString(7, newUser.getGender());
            ps.setInt(8, newUser.getRoleId());
            ps.setDate(9, (Date) newUser.getDob()); // Sẽ là null nếu bạn không set trong Servlet
            ps.setString(10, newUser.getSlug()); // Sẽ là null nếu bạn không set trong Servlet
            
            // executeUpdate() trả về số dòng bị ảnh hưởng (phải là 1)
            int rowsAffected = ps.executeUpdate();
            
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi thêm User (DAO): " + e.getMessage());
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
    public boolean deleteUser(int idUser) {
        
        String sql = "DELETE FROM `User` WHERE idUser = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            // Set tham số (idUser) cho câu lệnh DELETE
            ps.setInt(1, idUser);
            
            // executeUpdate() trả về số dòng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();
            
            // Nếu > 0 (tức là 1) thì thành công
            return rowsAffected > 0; 
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi xóa User (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private Genre extractGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setIdGenre(rs.getInt("idGenre"));
        genre.setName(rs.getString("name"));
        return genre;
    }

    //==================================================================
    //  READ (Lấy danh sách) 
    //==================================================================
    /**
     * Lấy ra danh sách TẤT CẢ thể loại trong CSDL.
     * @return List<Genre> - Danh sách các đối tượng Genre.
     */
    public List<Genre> getAllGenres() {
        
        List<Genre> genreList = new ArrayList<>();
        
        // Tên bảng là `Genre`
        String sql = "SELECT * FROM `Genre`";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                genreList.add(extractGenre(rs));
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách Genre: " + e.getMessage());
            e.printStackTrace();
        }
        
        return genreList; // Trả về danh sách (có thể rỗng nếu bị lỗi)
    }
    public List<Report> getAllReports() {
        List<Report> reportList = new ArrayList<>();
        String sql = "SELECT * FROM `Reports`";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reportList.add(extractReport(rs));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách Report: " + e.getMessage());
            e.printStackTrace();
        }

        return reportList;
    }

    private Report extractReport(ResultSet rs) throws SQLException {
        Report r = new Report();
        r.setIdReport(rs.getInt("idReport"));
        r.setRatingIdRating(rs.getInt("Rating_idRating"));
        r.setUserIdUserReporter(rs.getInt("User_idUser_reporter"));
        r.setReason(rs.getString("reason"));
        r.setStatus(rs.getString("status"));
        r.setCreateDate(rs.getTimestamp("create_date"));
        return r;
    }
    public List<Rating> getAllRatings() {
        List<Rating> ratingList = new ArrayList<>();
        String sql = "SELECT * FROM `Rating`";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ratingList.add(extractRating(rs));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách Rating: " + e.getMessage());
            e.printStackTrace();
        }

        return ratingList;
    }

    private Rating extractRating(ResultSet rs) throws SQLException {
        Rating r = new Rating();
        r.setIdRating(rs.getInt("idRating"));
        r.setStatus(rs.getString("status"));
        r.setComment(rs.getString("comment"));
        r.setScore(rs.getFloat("score"));
        r.setUserIdUser(rs.getInt("User_idUser"));
        r.setMovieIdMovie(rs.getInt("Movie_idMovie"));
        return r;
    }
    public List<MovieList> getAllMovieLists() {
        List<MovieList> movieLists = new ArrayList<>();
        String sql = "SELECT * FROM `Movie_lists`";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                movieLists.add(extractMovieList(rs));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách MovieList: " + e.getMessage());
            e.printStackTrace();
        }

        return movieLists;
    }

    private MovieList extractMovieList(ResultSet rs) throws SQLException {
        MovieList m = new MovieList();
        m.setIdMovieCollections(rs.getInt("idMovie_collections"));
        m.setListName(rs.getString("list_name"));
        m.setDescription(rs.getString("description"));
        m.setVisibility(rs.getString("visibility"));
        m.setStatus(rs.getString("status"));
        m.setCreateDate(rs.getTimestamp("create_date"));
        m.setSlug(rs.getString("slug"));
        m.setUserIdUser(rs.getInt("User_idUser"));
        return m;
    }

    //==================================================================
    //  CREATE (Thêm mới)
    //==================================================================
    /**
     * Thêm (INSERT) một thể loại mới vào CSDL.
     * @param name Tên của thể loại mới.
     * @return true nếu thêm thành công, false nếu thất bại.
     */
    public boolean insertGenre(String name) {
        
        // idGenre là tự động tăng (auto-increment), nên ta chỉ cần chèn 'name'
        String sql = "INSERT INTO `Genre` (name) VALUES (?)";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, name);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Nếu > 0 (tức là 1) thì thành công
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi thêm Genre (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //==================================================================
    //  UPDATE (Cập nhật)
    //==================================================================
    /**
     * Cập nhật (UPDATE) tên của một thể loại dựa trên ID.
     * @param idGenre ID của thể loại cần cập nhật.
     * @param name Tên mới cho thể loại.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateGenre(int idGenre, String name) {
        
        String sql = "UPDATE `Genre` SET name = ? WHERE idGenre = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            // Set các tham số
            ps.setString(1, name);
            ps.setInt(2, idGenre); // Cho điều kiện WHERE
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi cập nhật Genre (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //==================================================================
    //  DELETE (Xóa)
    //==================================================================
    /**
     * Xóa (DELETE) một thể loại khỏi CSDL dựa trên ID.
     * @param idGenre ID của thể loại cần xóa.
     * @return true nếu xóa thành công, false nếu thất bại.
     */
    public boolean deleteGenre(int idGenre) {
        
        String sql = "DELETE FROM `Genre` WHERE idGenre = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idGenre);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi xóa Genre (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    
    private Movie extractMovie(ResultSet rs) throws SQLException {
        Movie movie = new Movie();
        movie.setIdMovie(rs.getInt("idMovie"));
        movie.setTitle(rs.getString("title"));
        movie.setReleaseYear(rs.getInt("release_year"));
        movie.setDuration(rs.getString("duration"));
        movie.setDirector(rs.getString("director"));
        movie.setCountry(rs.getString("country"));
        movie.setLanguage(rs.getString("language"));
        movie.setPosterUrl(rs.getString("poster_url"));
        movie.setWallpaperUrl(rs.getString("wallpaper_url"));
        movie.setTrailerUrl(rs.getString("trailer_url"));
        movie.setDescription(rs.getString("description"));
        movie.setRatingAvg(rs.getFloat("rating_avg"));
        movie.setProductionCompany(rs.getString("production_company"));
        movie.setAgeRating(rs.getString("age_rating"));
        movie.setSlug(rs.getString("slug"));
        return movie;
    }

    /**
     * (READ) Lấy tất cả phim ra khỏi CSDL.
     */
    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        String sql = "SELECT * FROM `Movie`";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                movieList.add(extractMovie(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách Movie: " + e.getMessage());
            e.printStackTrace();
        }
        return movieList;
    }

    /**
     * (READ) Lấy một phim bằng ID của nó.
     */
    public Movie getMovieById(int idMovie) {
        String sql = "SELECT * FROM `Movie` WHERE idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractMovie(rs);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy Movie theo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    /**
     * (CREATE) Thêm một phim mới và TRẢ VỀ ID TỰ ĐỘNG TĂNG.
     * @return ID của phim mới được tạo (hoặc 0 nếu thất bại).
     */
    public int insertMovie(Movie movie) {
        String sql = "INSERT INTO `Movie` (title, release_year, duration, director, country, "
                   + "language, poster_url, wallpaper_url, trailer_url, description, "
                   + "rating_avg, production_company, age_rating, slug) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Dùng Statement.RETURN_GENERATED_KEYS để lấy ID
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getReleaseYear());
            ps.setString(3, movie.getDuration());
            ps.setString(4, movie.getDirector());
            ps.setString(5, movie.getCountry());
            ps.setString(6, movie.getLanguage());
            ps.setString(7, movie.getPosterUrl());
            ps.setString(8, movie.getWallpaperUrl());
            ps.setString(9, movie.getTrailerUrl());
            ps.setString(10, movie.getDescription());
            ps.setFloat(11, movie.getRatingAvg()); // Giả sử ratingAvg không null
            ps.setString(12, movie.getProductionCompany());
            ps.setString(13, movie.getAgeRating());
            ps.setString(14, movie.getSlug());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Lấy ID vừa được tạo
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Trả về ID
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi thêm Movie (DAO): " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu thất bại
    }

    /**
     * (UPDATE) Cập nhật thông tin chính của phim.
     * @return true nếu thành công, false nếu thất bại.
     */
    public boolean updateMovie(Movie movie) {
        String sql = "UPDATE `Movie` SET title = ?, release_year = ?, duration = ?, "
                   + "director = ?, country = ?, language = ?, poster_url = ?, "
                   + "wallpaper_url = ?, trailer_url = ?, description = ?, "
                   + "rating_avg = ?, production_company = ?, age_rating = ?, slug = ? "
                   + "WHERE idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getReleaseYear());
            ps.setString(3, movie.getDuration());
            ps.setString(4, movie.getDirector());
            ps.setString(5, movie.getCountry());
            ps.setString(6, movie.getLanguage());
            ps.setString(7, movie.getPosterUrl());
            ps.setString(8, movie.getWallpaperUrl());
            ps.setString(9, movie.getTrailerUrl());
            ps.setString(10, movie.getDescription());
            ps.setFloat(11, movie.getRatingAvg());
            ps.setString(12, movie.getProductionCompany());
            ps.setString(13, movie.getAgeRating());
            ps.setString(14, movie.getSlug());
            ps.setInt(15, movie.getIdMovie()); // Điều kiện WHERE

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi cập nhật Movie (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * (DELETE) Xóa một phim.
     * ⚠️ CẢNH BÁO: Để hàm này hoạt động, bạn phải thiết lập CSDL của mình 
     * với "ON DELETE CASCADE" cho các khóa ngoại trong
     * bảng `Translation` và `Movie_genres` trỏ đến `Movie`.
     * Nếu không, lệnh DELETE này sẽ thất bại.
     */
    public boolean deleteMovie(int idMovie) {
        String sql = "DELETE FROM `Movie` WHERE idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi xóa Movie (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private Translation extractTranslation(ResultSet rs) throws SQLException {
        Translation trans = new Translation();
        trans.setIdTranslation(rs.getInt("idTranslation"));
        trans.setMovieId(rs.getInt("Movie_idMovie"));
        trans.setLanguageCode(rs.getString("language_code"));
        trans.setTitle(rs.getString("title"));
        trans.setDescription(rs.getString("description"));
        return trans;
    }

    /**
     * (READ) Lấy tất cả các bản dịch cho một phim cụ thể.
     * Cần thiết cho Tab 2 trong trang Edit Movie.
     */
    public List<Translation> getTranslationsForMovie(int idMovie) {
        List<Translation> transList = new ArrayList<>();
        String sql = "SELECT * FROM `Translation` WHERE Movie_idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transList.add(extractTranslation(rs));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy danh sách Translation: " + e.getMessage());
            e.printStackTrace();
        }
        return transList;
    }

    /**
     * (CREATE) Thêm một bản dịch mới.
     * Được gọi bởi Servlet khi translationId[] = 0.
     */
    public boolean insertTranslation(int idMovie, String langCode, String title, String description) {
        String sql = "INSERT INTO `Translation` (Movie_idMovie, language_code, title, description) "
                   + "VALUES (?, ?, ?, ?)";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            ps.setString(2, langCode);
            ps.setString(3, title);
            ps.setString(4, description);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi thêm Translation (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * (UPDATE) Cập nhật một bản dịch đã có.
     * Được gọi bởi Servlet khi translationId[] > 0.
     */
    public boolean updateTranslation(int idTranslation, String langCode, String title, String description) {
        String sql = "UPDATE `Translation` SET language_code = ?, title = ?, description = ? "
                   + "WHERE idTranslation = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setString(1, langCode);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setInt(4, idTranslation); // Điều kiện WHERE

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi cập nhật Translation (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * (DELETE) Xóa một bản dịch cụ thể (khi người dùng nhấn "Remove").
     */
    public boolean deleteTranslation(int idTranslation) {
        String sql = "DELETE FROM `Translation` WHERE idTranslation = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idTranslation);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi xóa Translation (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * (DELETE) Xóa TẤT CẢ bản dịch liên quan đến một phim.
     * (Hàm này được dùng khi xóa một phim).
     */
    public boolean deleteAllTranslationsForMovie(int idMovie) {
        String sql = "DELETE FROM `Translation` WHERE Movie_idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            ps.executeUpdate(); // Không cần quan tâm số dòng
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi xóa tất cả Translation của phim (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Integer> getGenreIdsForMovie(int idMovie) {
        List<Integer> genreIds = new ArrayList<>();
        String sql = "SELECT Genre_idGenre FROM `Movie_genres` WHERE Movie_idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genreIds.add(rs.getInt("Genre_idGenre"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy Genre IDs cho phim: " + e.getMessage());
            e.printStackTrace();
        }
        return genreIds;
    }

    /**
     * (CREATE) Thêm một liên kết thể loại cho phim.
     * Được Servlet gọi trong vòng lặp.
     */
    public boolean addGenreToMovie(int idMovie, int idGenre) {
        String sql = "INSERT INTO `Movie_genres` (Movie_idMovie, Genre_idGenre) VALUES (?, ?)";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            ps.setInt(2, idGenre);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi thêm Genre vào Movie (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * (DELETE) Xóa TẤT CẢ các liên kết thể loại của một phim.
     * Được dùng khi cập nhật phim (xóa cũ, thêm mới) hoặc khi xóa phim.
     */
    public boolean deleteAllGenresForMovie(int idMovie) {
        String sql = "DELETE FROM `Movie_genres` WHERE Movie_idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            ps.executeUpdate(); // Không cần quan tâm số dòng
            return true;
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi xóa tất cả Genre của phim (DAO): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Integer> getTranslationIdsForMovie(int idMovie) {
        List<Integer> idList = new ArrayList<>();
        String sql = "SELECT idTranslation FROM `Translation` WHERE Movie_idMovie = ?";
        
        try (Connection connection = DBContext.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, idMovie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    idList.add(rs.getInt("idTranslation"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Lỗi khi lấy Translation IDs: " + e.getMessage());
            e.printStackTrace();
        }
        return idList;
    }
    
    public boolean updateReport(int idReport, int ratingIdRating, int userIdUserReporter, String reason, String status) {
    
    // Câu lệnh SQL UPDATE. Lưu ý: Trường createDate thường không được cập nhật
    // sau khi Report đã được tạo, nên tôi không đưa nó vào đây.
    String sql = "UPDATE `Report` SET ratingIdRating = ?, userIdUserReporter = ?, reason = ?, "
               + "status = ? WHERE idReport = ?";
    
    try (Connection connection = DBContext.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
        
        // Set các tham số cho câu lệnh SQL
        ps.setInt(1, ratingIdRating); // Tham số 1: ratingIdRating
        ps.setInt(2, userIdUserReporter); // Tham số 2: userIdUserReporter
        ps.setString(3, reason); // Tham số 3: reason
        ps.setString(4, status); // Tham số 4: status
        ps.setInt(5, idReport); // Tham số 5: idReport cho điều kiện WHERE
        
        // executeUpdate() trả về số dòng bị ảnh hưởng
        int rowsAffected = ps.executeUpdate();
        
        // Nếu có 1 dòng bị ảnh hưởng, tức là update thành công
        return rowsAffected > 0; 
        
    } catch (SQLException | ClassNotFoundException e) {
        System.out.println("Lỗi khi cập nhật Report (DAO): " + e.getMessage());
        e.printStackTrace();
        return false; // Trả về false nếu có lỗi
    }
}
    public static void main(String[] args) {
        AdminDAO add = new AdminDAO();
        List<User> userList = add.getAllUsers();
        if (userList == null) {
            System.out.println("Lỗi! Hàm trả về null.");
        } else if (userList.isEmpty()) {
            System.out.println("Thành công. Hàm trả về danh sách rỗng (Không có user trong DB).");
        } else {
            System.out.println("Thành công! Tìm thấy " + userList.size() + " user:");
            // In ra 1 vài user để kiểm tra
            for (User u : userList) {
                System.out.println("  - ID: " + u.getIdUser() + 
                                   ", Tên: " + u.getFullName() + 
                                   ", Email: " + u.getEmail());
            }
        }
        
    }
}