package com.example.app_biblioteca.servicios;

import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;

import java.util.ArrayList;

public interface IServicioPrestamo {

    void prestarLibro(Prestamo prestamo);
    Prestamo consultarPrestamo(String folio);
    int obtenerPosicion(String folio);
    boolean existe(String folio);
    ArrayList<Prestamo> obtenerPrestamos();
    boolean devolverLibro(String folio);
    ArrayList<Prestamo> listarDevoluciones();
    Prestamo obtenerDevolucion(String folio);

}
