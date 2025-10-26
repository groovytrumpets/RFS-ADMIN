/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.genre;

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
@WebServlet(name="UpdateGenreServlet", urlPatterns={"/updategenre"})
public class UpdateGenreServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateGenreServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateGenreServlet at " + request.getContextPath () + "</h1>");
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
            // 1. Lấy ID và Tên mới từ form
            int idGenre = Integer.parseInt(request.getParameter("genreid"));
            String name = request.getParameter("name");

            // 2. Kiểm tra (name không được rỗng)
            if (name != null && !name.trim().isEmpty()) {
                
                // 3. Khởi tạo DAO và gọi hàm update
                AdminDAO dao = new AdminDAO();
                boolean success = dao.updateGenre(idGenre, name.trim());

                if (success) {
                    System.out.println("Cập nhật thành công cho Genre ID: " + idGenre);
                } else {
                    System.out.println("Cập nhật thất bại cho Genre ID: " + idGenre);
                }
            } else {
                 System.out.println("Tên thể loại không được rỗng khi cập nhật!");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse ID thể loại (Update): " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi chung trong UpdateGenreServlet: " + e.getMessage());
            e.printStackTrace();
        }

        // 4. Chuyển hướng về trang danh sách
        response.sendRedirect("genres");
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
