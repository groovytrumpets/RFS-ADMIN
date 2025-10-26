/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class User {
    private int idUser;
    private String username;
    private String password;
    private String status;
    private String fullName;
    private String phone;
    private Timestamp createDate; // Tương ứng với create_date (DATETIME)
    private Date dob;             // Tương ứng với dob (DATE)
    private String email;
    private String gender;
    private String slug;
    private int roleId;           // Tương ứng với Role_idRole (INT)

    // 2. Constructor rỗng
    public User() {
    }

    // 3. Constructor đầy đủ (tiện lợi)
    public User(int idUser, String username, String password, String status, 
                String fullName, String phone, Timestamp createDate, Date dob, 
                String email, String gender, String slug, int roleId) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.status = status;
        this.fullName = fullName;
        this.phone = phone;
        this.createDate = createDate;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.slug = slug;
        this.roleId = roleId;
    }

    // 4. Các phương thức Getter và Setter
    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    // 5. (Tùy chọn) Phương thức toString() để debug
    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", username=" + username + 
               ", email=" + email + ", roleId=" + roleId + '}';
    }
}
