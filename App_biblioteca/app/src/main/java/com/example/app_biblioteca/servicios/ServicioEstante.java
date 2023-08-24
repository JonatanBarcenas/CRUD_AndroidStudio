package com.example.app_biblioteca.servicios;

import android.content.Context;

import com.example.app_biblioteca.dao.LibrosDAO;
import com.example.app_biblioteca.modelo.Libro;

import java.util.ArrayList;

public class ServicioEstante implements IServicioEstante{

    private ArrayList<Libro> contenedor;
    private Context context;

    public ServicioEstante(Context context) {
        this.context = context;
        contenedor = (LibrosDAO.cargarDatos(context) != null) ? LibrosDAO.cargarDatos(context) : new ArrayList<>();
    }

    @Override
    public boolean agregarLibro(Libro libro) {
        if (!existe(libro.getIsbn()) && libro != null) {
            contenedor.add(libro);
            LibrosDAO.guardarDatos(context, contenedor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean eliminarLibro(String isbn) {
        if (existe(isbn)) {
            Libro libro = obtenerLibro(isbn);
            int posicion = obtenerPosicion(isbn);
            contenedor.remove(posicion);
            LibrosDAO.guardarDatos(context, contenedor);
            return true;
        }
        return false;
    }

    @Override
    public Libro modificarLibro(int posicion, Libro libro) {
        Libro libroModificado = contenedor.set(posicion, libro);
        LibrosDAO.guardarDatos(context, contenedor);
        return libroModificado;
    }

    @Override
    public Libro obtenerLibro(String isbn) {
        return (existe(isbn)) ? contenedor.get(obtenerPosicion(isbn)) : null;
    }

    @Override
    public int obtenerPosicion(String isbn) {
        int posicion = 0;
        for (Libro l : contenedor) {
            if (l.getIsbn().equals(isbn)) {
                return posicion;
            }
            posicion++;
        }
        return -1;
    }

    @Override
    public boolean existe(String isbn) {
        for (Libro l : contenedor) {
            if (l.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean estaDisponible(String isbn) {
        Libro libro = obtenerLibro(isbn);
        return libro.isEstaPrestado();
    }

    @Override
    public ArrayList<Libro> obtenerLibros() {
        return contenedor;
    }
}
