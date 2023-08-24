package com.example.app_biblioteca.servicios;

import com.example.app_biblioteca.dao.LibrosDAO;
import com.example.app_biblioteca.modelo.Libro;

import java.util.ArrayList;

public interface IServicioEstante {

    boolean agregarLibro(Libro libro);
    boolean eliminarLibro(String isbn);
    Libro modificarLibro(int posicion, Libro libro);
    Libro obtenerLibro(String isbn);
    int obtenerPosicion(String isbn);

    boolean existe(String isbn);
    boolean estaDisponible(String isbn);
    ArrayList<Libro> obtenerLibros();

}
