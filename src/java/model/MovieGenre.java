/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class MovieGenre {
    private int idMovieGenres;
    private int genreId; // Khóa ngoại đến Genre.idGenre
    private int movieId; // Khóa ngoại đến Movie.idMovie

    // Constructor rỗng
    public MovieGenre() {
    }

    // Constructor đầy đủ
    public MovieGenre(int idMovieGenres, int genreId, int movieId) {
        this.idMovieGenres = idMovieGenres;
        this.genreId = genreId;
        this.movieId = movieId;
    }

    // Getters and Setters
    public int getIdMovieGenres() {
        return idMovieGenres;
    }

    public void setIdMovieGenres(int idMovieGenres) {
        this.idMovieGenres = idMovieGenres;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
