package com.example.app_biblioteca.servicios;

import android.content.Context;

import com.example.app_biblioteca.dao.DevolucionesDAO;
import com.example.app_biblioteca.dao.LibrosDAO;
import com.example.app_biblioteca.dao.PrestamosDAO;
import com.example.app_biblioteca.dao.UsuariosDAO;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;

import java.util.ArrayList;

public class ServicioPrestamo implements IServicioPrestamo{

    private ArrayList<Prestamo> contenedor;
    private ArrayList<Prestamo> devoluciones;
    private Context context;

    public ServicioPrestamo(Context context) {

        this.context = context;
        contenedor = (PrestamosDAO.cargarDatos(context) != null) ? PrestamosDAO.cargarDatos(context) : new ArrayList<>();
        devoluciones = (DevolucionesDAO.cargarDatos(context) != null) ? DevolucionesDAO.cargarDatos(context) : new ArrayList<>();

    }

    @Override
    public void prestarLibro(Prestamo prestamo) {
        contenedor.add(prestamo);
        PrestamosDAO.guardarDatos(context, contenedor);
    }

    @Override
    public Prestamo consultarPrestamo(String folio) {
        return (existe(folio))?contenedor.get(obtenerPosicion(folio)):null;
    }

    @Override
    public ArrayList<Prestamo> obtenerPrestamos() {
        return contenedor;
    }

    @Override
    public boolean devolverLibro(String folio) {

        if(existe(folio)){
            int posicion = obtenerPosicion(folio);
            Prestamo prestamo = consultarPrestamo(folio);
            contenedor.remove(posicion);
            devoluciones.add(prestamo);
            PrestamosDAO.guardarDatos(context, contenedor);
            DevolucionesDAO.guardarDatos(context, devoluciones);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public ArrayList<Prestamo> listarDevoluciones() {
        if (DevolucionesDAO.cargarDatos(context) != null){
            return DevolucionesDAO.cargarDatos(context) ;
        }else{
            return new ArrayList<Prestamo>();
        }

    }

    @Override
    public Prestamo obtenerDevolucion(String folio) {
        return (existeDevolucion(folio))?devoluciones.get(obtenerPosicionDevoluciones(folio)):null;
    }

    @Override
    public int obtenerPosicion(String folio) {
        int posicion = 0;
        for (Prestamo l : contenedor) {
            if (l.getFolio().equals(folio)) {
                return posicion;
            }
            posicion++;
        }
        return -1;
    }

    public int obtenerPosicionDevoluciones(String folio) {
        int posicion = 0;
        for (Prestamo l : devoluciones) {
            if (l.getFolio().equals(folio)) {
                return posicion;
            }
            posicion++;
        }
        return -1;
    }

    public boolean existeDevolucion(String folio) {
        for (Prestamo l : devoluciones) {
            if (l.getFolio().equals(folio)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existe(String folio) {
        for (Prestamo l : contenedor) {
            if (l.getFolio().equals(folio)) {
                return true;
            }
        }
        return false;
    }
}