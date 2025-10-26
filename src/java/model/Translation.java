/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class Translation {
    private int idTranslation;
    private int movieId; // Khóa ngoại liên kết đến Movie.idMovie
    private String languageCode;
    private String title;
    private String description;

    // Constructor rỗng
    public Translation() {
    }

    // Constructor đầy đủ
    public Translation(int idTranslation, int movieId, String languageCode, String title, String description) {
        this.idTranslation = idTranslation;
        this.movieId = movieId;
        this.languageCode = languageCode;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public int getIdTranslation() {
        return idTranslation;
    }

    public void setIdTranslation(int idTranslation) {
        this.idTranslation = idTranslation;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
