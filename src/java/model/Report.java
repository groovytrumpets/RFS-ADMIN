/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class Report {
    private int idReport;
    private int ratingIdRating;
    private int userIdUserReporter;
    private String reason;       
    private String status;       
    private Timestamp createDate;

    public Report() {
    }

    

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public int getRatingIdRating() {
        return ratingIdRating;
    }

    public void setRatingIdRating(int ratingIdRating) {
        this.ratingIdRating = ratingIdRating;
    }

    public int getUserIdUserReporter() {
        return userIdUserReporter;
    }

    public void setUserIdUserReporter(int userIdUserReporter) {
        this.userIdUserReporter = userIdUserReporter;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Report(int idReport, int ratingIdRating, int userIdUserReporter, String reason, String status, Timestamp createDate) {
        this.idReport = idReport;
        this.ratingIdRating = ratingIdRating;
        this.userIdUserReporter = userIdUserReporter;
        this.reason = reason;
        this.status = status;
        this.createDate = createDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    
    
    
}
