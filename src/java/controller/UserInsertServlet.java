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
import model.User;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
@WebServlet(name="UserInsertServlet", urlPatterns={"/insertuser"})
public class UserInsertServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UserInsertServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserInsertServlet at " + request.getContextPath () + "</h1>");
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
        // 1. Lấy tất cả tham số từ form
        String status = request.getParameter("status");
        String username = request.getParameter("username");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        
        // BẮT BUỘC: Form thêm mới phải có trường password
        String password = request.getParameter("password");
            System.out.println(username);
            System.out.println(password);
            
        // Kiểm tra các trường bắt buộc
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            // Có thể gửi thông báo lỗi về JSP
            System.out.println("Username hoặc Password bị trống!");
            response.sendRedirect("users?error=true"); // Sửa "users" thành URL trang của bạn
            return; // Dừng xử lý
        }

        // 2. Tạo đối tượng User mới
        User newUser = new User();
        newUser.setStatus(status);
        newUser.setUsername(username);
        newUser.setPassword(password); // !!! Cảnh báo: Mật khẩu đang ở dạng text thuần
        newUser.setFullName(fullname);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setGender(gender);
        
        // Set các giá trị mặc định cho các trường không có trên form
        newUser.setRoleId(2); // Giả sử Role ID 2 là "User"
        newUser.setDob(null); // Trừ khi bạn thêm trường này vào form
        newUser.setSlug(null); // Có thể tạo slug tự động
        
        // 3. Khởi tạo DAO và gọi hàm insertUser
        AdminDAO dao = new AdminDAO();
        boolean success = dao.insertUser(newUser);

        if (success) {
            System.out.println("Thêm mới thành công cho User: " + username);
        } else {
            System.out.println("Thêm mới thất bại cho User: " + username);
        }
        
    } catch (Exception e) {
        System.out.println("Lỗi chung trong UserInsertServlet: " + e.getMessage());
        e.printStackTrace();
    }

    // 4. Chuyển hướng (Redirect) về trang danh sách user
    // (Sửa "users" thành URL servlet tải danh sách user của bạn)
    response.sendRedirect("users");
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
