package com.example.app_biblioteca.modelo;

import java.io.Serializable;

public class Prestamo implements Serializable {

    private String folio;
    private String isbn;
    private String claveUsuario;
    private String fechaPrestamo;
    private String fechaDevolucion;


    public Prestamo(){

    }

    public Prestamo(String folio, String isbn, String claveUsuario, String fechaPrestamo, String fechaDevolucion) {
        this.folio = folio;
        this.isbn = isbn;
        this.claveUsuario = claveUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "folio='" + folio + '\'' +
                ", isbn='" + isbn + '\'' +
                ", claveUsuario='" + claveUsuario + '\'' +
                ", fechaPrestamo='" + fechaPrestamo + '\'' +
                ", fechaDevolucion='" + fechaDevolucion + '\'' +
                '}';
    }
}
