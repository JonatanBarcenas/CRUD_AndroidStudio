package com.example.app_biblioteca.servicios;

import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;

import java.util.ArrayList;

public interface IServicioUsuarios {
    boolean agregarUsuario(Usuario usuario);
    boolean eliminarUsuario(String clave);
    Usuario modificarUsuario(int posicion, Usuario usuario);
    Usuario obtenerUsuario(String clave);
    int obtenerPosicion(String clave);
    boolean existe(String clave);
    boolean debeLibro(String clave);
    boolean tieneDevolucionesAtrasadas(String clave);
    ArrayList<Usuario> obtenerUsuarios();

    ArrayList<Prestamo> obtenerPrestamos(String clave);
}
