/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class Genre {
    private int idGenre;
    private String name;

    // Constructor rỗng
    public Genre() {
    }

    // Constructor đầy đủ
    public Genre(int idGenre, String name) {
        this.idGenre = idGenre;
        this.name = name;
    }

    // Getters and Setters
    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" + "idGenre=" + idGenre + ", name=" + name + '}';
    }
}
