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

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="DeleteMovieServlet", urlPatterns={"/deleteMovie"})
public class DeleteMovieServlet extends HttpServlet {
   
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
            out.println("<title>Servlet DeleteMovieServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteMovieServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        try {
            // 1. Lấy ID của phim cần xóa từ form
            int idMovie = Integer.parseInt(request.getParameter("idMovie"));

            // 2. Khởi tạo các DAO cần thiết
            AdminDAO movieDAO = new AdminDAO();
            AdminDAO translationDAO = new AdminDAO();
            AdminDAO movieGenreDAO = new AdminDAO();

            // 3. XÓA DỮ LIỆU Ở BẢNG PHỤ TRƯỚC
            
            // 3a. Xóa tất cả bản dịch liên quan
            translationDAO.deleteAllTranslationsForMovie(idMovie);
            
            // 3b. Xóa tất cả liên kết thể loại liên quan
            movieGenreDAO.deleteAllGenresForMovie(idMovie);
            
            // 4. XÓA DỮ LIỆU Ở BẢNG CHÍNH (MOVIE)
            boolean success = movieDAO.deleteMovie(idMovie);

            if (success) {
                System.out.println("Đã xóa thành công Movie ID: " + idMovie);
            } else {
                System.out.println("Xóa Movie thất bại (ID: " + idMovie + ")");
            }

        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse ID phim (Delete): " + e.getMessage());
        }
        
        // 5. Chuyển hướng (Redirect) về trang danh sách phim
        // URL này phải khớp với URL của LoadMoviesServlet
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
