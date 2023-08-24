package com.example.app_biblioteca.modelo;

import java.io.Serializable;

public class Libro implements Serializable {

    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private String edicion;
    private String idioma;
    private String volumen;
    private boolean estaPrestado;

    public Libro(){


    }

    public Libro(String isbn, String titulo, String autor, String editorial, String edicion, String idioma, String volumen) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.edicion = edicion;
        this.idioma = idioma;
        this.volumen = volumen;
    }

    public boolean isEstaPrestado() {
        return estaPrestado;
    }

    public void setEstaPrestado(boolean estaPrestado) {
        this.estaPrestado = estaPrestado;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", edicion='" + edicion + '\'' +
                ", idioma='" + idioma + '\'' +
                ", volumen='" + volumen + '\'' +
                ", estaPrestado=" + estaPrestado +
                '}';
    }
}
