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
public class MovieList {
    private int idMovieCollections;
    private String listName;
    private String description;
    private String visibility;    // ENUM: private, friends, public
    private String status;
    private Timestamp createDate;
    private String slug;
    private int userIdUser;

    public MovieList() {
    }

    public MovieList(int idMovieCollections, String listName, String description, String visibility, String status, Timestamp createDate, String slug, int userIdUser) {
        this.idMovieCollections = idMovieCollections;
        this.listName = listName;
        this.description = description;
        this.visibility = visibility;
        this.status = status;
        this.createDate = createDate;
        this.slug = slug;
        this.userIdUser = userIdUser;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

   

    public int getIdMovieCollections() {
        return idMovieCollections;
    }

    public void setIdMovieCollections(int idMovieCollections) {
        this.idMovieCollections = idMovieCollections;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }
    
    
}
