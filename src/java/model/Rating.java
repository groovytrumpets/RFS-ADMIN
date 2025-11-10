/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class Rating {
    private int idRating;
    private String status;
    private String comment;
    private float score;
    private int userIdUser;
    private int movieIdMovie;

    public Rating() {
    }

    public Rating(int idRating, String status, String comment, float score, int userIdUser, int movieIdMovie) {
        this.idRating = idRating;
        this.status = status;
        this.comment = comment;
        this.score = score;
        this.userIdUser = userIdUser;
        this.movieIdMovie = movieIdMovie;
    }

    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getUserIdUser() {
        return userIdUser;
    }

    public void setUserIdUser(int userIdUser) {
        this.userIdUser = userIdUser;
    }

    public int getMovieIdMovie() {
        return movieIdMovie;
    }

    public void setMovieIdMovie(int movieIdMovie) {
        this.movieIdMovie = movieIdMovie;
    }
    
    
}
