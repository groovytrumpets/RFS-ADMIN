/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

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
@WebServlet(name="UserUpdateServlet", urlPatterns={"/updateuser"})
public class UserUpdateServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UserUpdateServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserUpdateServlet at " + request.getContextPath () + "</h1>");
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
        // 1. Set encoding (Rất quan trọng) để xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // 2. Lấy tất cả tham số từ form JSP
            // (Tên tham số phải khớp với thuộc tính "name" trong input)
            int idUser = Integer.parseInt(request.getParameter("userid"));
            String username = request.getParameter("username");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");

            // 3. Khởi tạo DAO và gọi hàm update
            AdminDAO dao = new AdminDAO();
            boolean success = dao.updateUser(idUser, username, fullname, email, phone, gender);

            if (success) {
                System.out.println("Cập nhật thành công cho User ID: " + idUser);
            } else {
                System.out.println("Cập nhật thất bại cho User ID: " + idUser);
            }
            response.sendRedirect("users");
        } catch (NumberFormatException e) {
            System.out.println("Lỗi parse ID user: " + e.getMessage());
            // Có thể forward đến trang lỗi
        } catch (Exception e) {
            System.out.println("Lỗi chung trong UpdateUserServlet: " + e.getMessage());
            e.printStackTrace();
        }

        // 4. Chuyển hướng (Redirect) về trang danh sách
        // DÙNG REDIRECT (PRG Pattern) để tránh lỗi F5 (gửi lại form)
        // !! LƯU Ý: Sửa "admin-user-list" thành URL CỦA SERVLET
        // hiển thị danh sách user của bạn (ví dụ: /admin/users)
        
        // Giả sử servlet hiển thị danh sách user có URL là "load-users"
    
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
