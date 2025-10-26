/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class Movie {
    private int idMovie;
    private String title;
    private int releaseYear; // Đổi từ release_year
    private String duration;
    private String director;
    private String country;
    private String language;
    private String posterUrl; // Đổi từ poster_url
    private String wallpaperUrl; // Đổi từ wallpaper_url
    private String trailerUrl; // Đổi từ trailer_url
    private String description;
    private float ratingAvg; // Đổi từ rating_avg
    private String productionCompany; // Đổi từ production_company
    private String ageRating; // Đổi từ age_rating
    private String slug;
    
    private List<Translation> translations;

    // 2. Constructor rỗng (Bắt buộc phải có cho JavaBean)
    public Movie() {
    }

    // 3. Constructor đầy đủ (Tiện lợi khi tạo đối tượng mới)
    public Movie(int idMovie, String title, int releaseYear, String duration, 
                 String director, String country, String language, String posterUrl, 
                 String wallpaperUrl, String trailerUrl, String description, 
                 float ratingAvg, String productionCompany, String ageRating, String slug) {
        this.idMovie = idMovie;
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.director = director;
        this.country = country;
        this.language = language;
        this.posterUrl = posterUrl;
        this.wallpaperUrl = wallpaperUrl;
        this.trailerUrl = trailerUrl;
        this.description = description;
        this.ratingAvg = ratingAvg;
        this.productionCompany = productionCompany;
        this.ageRating = ageRating;
        this.slug = slug;
    }

    public Movie(int idMovie, String title, int releaseYear, String duration, String director, String country, String language, String posterUrl, String wallpaperUrl, String trailerUrl, String description, float ratingAvg, String productionCompany, String ageRating, String slug, List<Translation> translations) {
        this.idMovie = idMovie;
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.director = director;
        this.country = country;
        this.language = language;
        this.posterUrl = posterUrl;
        this.wallpaperUrl = wallpaperUrl;
        this.trailerUrl = trailerUrl;
        this.description = description;
        this.ratingAvg = ratingAvg;
        this.productionCompany = productionCompany;
        this.ageRating = ageRating;
        this.slug = slug;
        this.translations = translations;
    }
    

    // 4. Các phương thức Getter và Setter (Bấm chuột phải > Insert Code... > Getter and Setter... trong IDE)

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getWallpaperUrl() {
        return wallpaperUrl;
    }

    public void setWallpaperUrl(String wallpaperUrl) {
        this.wallpaperUrl = wallpaperUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(float ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    // 5. (Tùy chọn) Phương thức toString() để debug
    @Override
    public String toString() {
        return "Movie{" + "idMovie=" + idMovie + ", title=" + title + 
               ", releaseYear=" + releaseYear + ", ratingAvg=" + ratingAvg + '}';
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    
}
