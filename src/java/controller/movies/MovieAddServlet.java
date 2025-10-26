/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.movies;

import DAO.AdminDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Genre;
import model.Movie;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="MovieAddServlet", urlPatterns={"/movie-add"})
public class MovieAddServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MovieAddServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MovieAddServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // 1. Lấy danh sách TẤT CẢ thể loại
            AdminDAO genreDAO = new AdminDAO();
            List<Genre> allGenres = genreDAO.getAllGenres();

            // 2. Gửi danh sách này sang JSP để render hộp <select multiple>
            request.setAttribute("allGenres", allGenres);
            
        } catch (Exception e) {
            System.out.println("Lỗi khi tải trang Thêm Phim: " + e.getMessage());
            e.printStackTrace();
        }
        
        // 3. Chuyển tiếp đến trang JSP (form trống)
        request.getRequestDispatcher("moviesAdd.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Khởi tạo các DAO cần thiết
        AdminDAO movieDAO = new AdminDAO();
        AdminDAO movieGenreDAO = new AdminDAO();
        AdminDAO translationDAO = new AdminDAO();

        try {
            // =============================================================
            // BƯỚC 1: LẤY DỮ LIỆU VÀ INSERT VÀO BẢNG CHÍNH "MOVIE"
            // =============================================================
            
            // Lấy tất cả thông tin từ Tab "Main Details"
            String title = request.getParameter("title");
            int releaseYear = Integer.parseInt(request.getParameter("release_year"));
            String duration = request.getParameter("duration");
            String director = request.getParameter("director");
            String country = request.getParameter("country");
            String language = request.getParameter("language");
            float ratingAvg = Float.parseFloat(request.getParameter("rating_avg"));
            String ageRating = request.getParameter("age_rating");
            String productionCompany = request.getParameter("production_company");
            String slug = request.getParameter("slug");
            String posterUrl = request.getParameter("poster_url");
            String wallpaperUrl = request.getParameter("wallpaper_url");
            String trailerUrl = request.getParameter("trailer_url");
            String description = request.getParameter("description");
            
            // Tạo đối tượng Movie (Model)
            Movie newMovie = new Movie();
            newMovie.setTitle(title);
            newMovie.setReleaseYear(releaseYear);
            newMovie.setDuration(duration);
            newMovie.setDirector(director);
            newMovie.setCountry(country);
            newMovie.setLanguage(language);
            newMovie.setRatingAvg(ratingAvg);
            newMovie.setAgeRating(ageRating);
            newMovie.setProductionCompany(productionCompany);
            newMovie.setSlug(slug);
            newMovie.setPosterUrl(posterUrl);
            newMovie.setWallpaperUrl(wallpaperUrl);
            newMovie.setTrailerUrl(trailerUrl);
            newMovie.setDescription(description);

            // Gọi DAO để INSERT và lấy ID tự động tăng
            // (Hàm insertMovie của chúng ta đã trả về ID mới)
            int newMovieId = movieDAO.insertMovie(newMovie);
            
            // =============================================================
            // BƯỚC 2 & 3: INSERT VÀO BẢNG PHỤ (NẾU BƯỚC 1 THÀNH CÔNG)
            // =============================================================
            
            if (newMovieId > 0) { // Kiểm tra xem phim đã được tạo thành công chưa
                
                // --- 2a. Xử lý Thể loại (Bảng Movie_genres) ---
                String[] genreIds = request.getParameterValues("genreIds");
                
                if (genreIds != null) {
                    for (String genreIdStr : genreIds) {
                        int genreId = Integer.parseInt(genreIdStr);
                        // Gọi DAO để liên kết Phim với Thể loại
                        movieGenreDAO.addGenreToMovie(newMovieId, genreId);
                    }
                }

                // --- 2b. Xử lý Bản dịch (Bảng Translation) ---
                String[] languageCodes = request.getParameterValues("languageCode[]");
                String[] translatedTitles = request.getParameterValues("title[]"); // Tên trùng với title chính, nhưng đây là mảng
                String[] translatedDescs = request.getParameterValues("description[]"); // Tên trùng
                
                // (JSP tự động gửi 'translationId[]' với giá trị '0' cho các mục mới)
                
                if (languageCodes != null) {
                    for (int i = 0; i < languageCodes.length; i++) {
                        // Gọi DAO để thêm bản dịch mới
                        translationDAO.insertTranslation(newMovieId, languageCodes[i], translatedTitles[i], translatedDescs[i]);
                    }
                }
                
                System.out.println("Thêm phim mới (ID: " + newMovieId + ") thành công!");
                
            } else {
                System.out.println("Thêm phim mới thất bại!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số (Năm, Rating...): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Lỗi chung trong MovieAddServlet (doPost): " + e.getMessage());
            e.printStackTrace();
        }
        
        // 4. Chuyển hướng (Redirect) về trang danh sách phim
        response.sendRedirect("movies");
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
