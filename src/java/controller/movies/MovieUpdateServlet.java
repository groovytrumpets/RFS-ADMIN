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
import model.Translation;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="MovieUpdateServlet", urlPatterns={"/loadMovieForEdit"})
public class MovieUpdateServlet extends HttpServlet {
   
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
            out.println("<title>Servlet MovieUpdateServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MovieUpdateServlet at " + request.getContextPath () + "</h1>");
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
            // 1. Lấy ID của phim cần sửa
            int idMovie = Integer.parseInt(request.getParameter("id"));
            
            // 2. Khởi tạo các DAO
            AdminDAO movieDAO = new AdminDAO();
            AdminDAO translationDAO = new AdminDAO();
            AdminDAO genreDAO = new AdminDAO();
            AdminDAO movieGenreDAO = new AdminDAO();
            
            // 3. Lấy dữ liệu cần thiết
            
            // 3a. Lấy thông tin phim chính (Bảng Movie)
            Movie movie = movieDAO.getMovieById(idMovie);
            
            // 3b. Lấy các bản dịch của phim (Bảng Translation)
            List<Translation> translations = translationDAO.getTranslationsForMovie(idMovie);
            // Gán vào đối tượng movie (nếu model của bạn có hỗ trợ)
            movie.setTranslations(translations); // Cần hàm setTranslations trong model Movie

            // 3c. Lấy TẤT CẢ thể loại (để hiển thị hộp chọn)
            List<Genre> allGenres = genreDAO.getAllGenres();
            
            // 3d. Lấy ID của các thể loại MÀ PHIM NÀY ĐANG CÓ (để 'selected')
            List<Integer> selectedGenreIds = movieGenreDAO.getGenreIdsForMovie(idMovie);
            
            // 4. Gửi tất cả dữ liệu này sang JSP
            request.setAttribute("movie", movie);
            request.setAttribute("allGenres", allGenres);
            request.setAttribute("selectedGenreIds", selectedGenreIds);
            
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse ID phim (Edit GET): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi chung trong MovieEditServlet (doGet): " + e.getMessage());
            e.printStackTrace();
        }
        
        // 5. Chuyển tiếp đến trang JSP (form sửa)
        // (Sửa "editMovie.jsp" nếu file của bạn tên khác)
        request.getRequestDispatcher("movies.jsp").forward(request, response);
    
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
        // Khởi tạo các DAO
        AdminDAO movieDAO = new AdminDAO();
        AdminDAO movieGenreDAO = new AdminDAO();
        AdminDAO translationDAO = new AdminDAO();

        try {
            // =============================================================
            // BƯỚC 1: CẬP NHẬT BẢNG CHÍNH "MOVIE"
            // =============================================================
            
            // Lấy ID phim
            int idMovie = Integer.parseInt(request.getParameter("idMovie"));
            
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
            Movie movieToUpdate = new Movie();
            movieToUpdate.setIdMovie(idMovie); // Quan trọng
            movieToUpdate.setTitle(title);
            movieToUpdate.setReleaseYear(releaseYear);
            movieToUpdate.setDuration(duration);
            movieToUpdate.setDirector(director);
            movieToUpdate.setCountry(country);
            movieToUpdate.setLanguage(language);
            movieToUpdate.setRatingAvg(ratingAvg);
            movieToUpdate.setAgeRating(ageRating);
            movieToUpdate.setProductionCompany(productionCompany);
            movieToUpdate.setSlug(slug);
            movieToUpdate.setPosterUrl(posterUrl);
            movieToUpdate.setWallpaperUrl(wallpaperUrl);
            movieToUpdate.setTrailerUrl(trailerUrl);
            movieToUpdate.setDescription(description);

            // Gọi DAO để UPDATE
            movieDAO.updateMovie(movieToUpdate);
            
            // =============================================================
            // BƯỚC 2: CẬP NHẬT BẢNG "MOVIE_GENRES" (Xóa cũ, thêm mới)
            // =============================================================
            
            // 2a. Xóa tất cả thể loại cũ của phim này
            movieGenreDAO.deleteAllGenresForMovie(idMovie);
            
            // 2b. Lấy danh sách ID thể loại mới từ form
            String[] genreIds = request.getParameterValues("genreIds");
            
            // 2c. Thêm lại các thể loại vừa được chọn
            if (genreIds != null) {
                for (String genreIdStr : genreIds) {
                    int genreId = Integer.parseInt(genreIdStr);
                    movieGenreDAO.addGenreToMovie(idMovie, genreId);
                }
            }

            // =============================================================
            // BƯỚC 3: CẬP NHẬT BẢNG "TRANSLATION" (Thêm/Sửa/Xóa)
            // =============================================================
            
            // 3a. Lấy danh sách ID bản dịch GỐC (trong CSDL)
            List<Integer> originalTransIds = translationDAO.getTranslationIdsForMovie(idMovie);
            
            // 3b. Lấy mảng dữ liệu MỚI từ form
            String[] submittedTransIds = request.getParameterValues("translationId[]");
            String[] languageCodes = request.getParameterValues("languageCode[]");
            String[] translatedTitles = request.getParameterValues("title[]");
            String[] translatedDescs = request.getParameterValues("description[]");
            
            if (submittedTransIds != null) {
                for (int i = 0; i < submittedTransIds.length; i++) {
                    int transId = Integer.parseInt(submittedTransIds[i]);
                    String langCode = languageCodes[i];
                    String transTitle = translatedTitles[i];
                    String transDesc = translatedDescs[i];
                    
                    if (transId == 0) {
                        // (INSERT) Đây là bản dịch MỚI (do user nhấn "Add New")
                        translationDAO.insertTranslation(idMovie, langCode, transTitle, transDesc);
                    } else {
                        // (UPDATE) Đây là bản dịch CŨ (đã có)
                        translationDAO.updateTranslation(transId, langCode, transTitle, transDesc);
                        
                        // Xóa ID này khỏi danh sách gốc để đánh dấu là "đã xử lý"
                        originalTransIds.remove(Integer.valueOf(transId));
                    }
                }
            }
            
            // 3c. (DELETE) Xóa các bản dịch còn sót lại
            // Bất kỳ ID nào còn lại trong 'originalTransIds' là bản dịch
            // mà người dùng đã nhấn nút "Remove" (vì nó không được gửi lên)
            for (int idToDelete : originalTransIds) {
                translationDAO.deleteTranslation(idToDelete);
            }
            
            System.out.println("Cập nhật phim (ID: " + idMovie + ") thành công!");

        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse số (Edit POST): " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Lỗi chung trong MovieEditServlet (doPost): " + e.getMessage());
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
