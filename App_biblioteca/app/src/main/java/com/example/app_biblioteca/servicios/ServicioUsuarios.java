package com.example.app_biblioteca.servicios;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.dao.LibrosDAO;
import com.example.app_biblioteca.dao.PrestamosDAO;
import com.example.app_biblioteca.dao.UsuariosDAO;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class ServicioUsuarios implements IServicioUsuarios{
    private ArrayList<Usuario> contenedor;
    private ArrayList<Prestamo> prestamos;
    private Context context;

    public ServicioUsuarios(Context context) {
        this.context = context;
        contenedor = (UsuariosDAO.cargarDatos(context) != null) ? UsuariosDAO.cargarDatos(context) : new ArrayList<>();
        prestamos = (PrestamosDAO.cargarDatos(context) != null) ? PrestamosDAO.cargarDatos(context) : new ArrayList<>();
    }
    @Override
    public boolean agregarUsuario(Usuario usuario) {
        if (!existe(usuario.getClave()) && usuario!=null){
            contenedor.add(usuario);
            UsuariosDAO.guardarDatos(context,contenedor);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(String clave) {
        if (existe(clave)){
            Usuario usuario = obtenerUsuario(clave);
            int posicion = obtenerPosicion(clave);
            contenedor.remove(posicion);
            UsuariosDAO.guardarDatos(context,contenedor);
            return true;
        }
        return false;
    }

    @Override
    public Usuario modificarUsuario(int posicion, Usuario usuario) {
        Usuario usuarioModificado = contenedor.set(posicion, usuario);
        UsuariosDAO.guardarDatos(context,contenedor);
        return usuarioModificado;
    }

    @Override
    public Usuario obtenerUsuario(String clave) {
        return (existe(clave))?contenedor.get(obtenerPosicion(clave)):null;
    }

    @Override
    public int obtenerPosicion(String clave) {
        int posicion = 0;
        for (Usuario usuario : contenedor) {
            if (usuario.getClave().equals(clave)) {
                return posicion;
            }
            posicion++;
        }

        return -1;
    }

    @Override
    public boolean existe(String clave) {
        for (Usuario usuario: contenedor){
            if (usuario.getClave().equals(clave)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean debeLibro(String clave) {
        Usuario usuario = obtenerUsuario(clave);
        return usuario.isTieneLibro() > 3;
    }

    @Override
    public boolean tieneDevolucionesAtrasadas(String clave) {
        ArrayList<Prestamo> prestamosUsuario = prestamos.stream()
                .filter(x -> x.getClaveUsuario().equals(clave))
                .collect(Collectors.toCollection(ArrayList::new));

        for (Prestamo prestamo: prestamosUsuario){
            Calendar calendar = Calendar.getInstance();
            Date fechaActual = calendar.getTime();
            String fechaDevolucionString = prestamo.getFechaDevolucion();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date fechaDevolucion = dateFormat.parse(fechaDevolucionString);

                if (fechaActual.after(fechaDevolucion)) {
                   return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ArrayList<Usuario> obtenerUsuarios() {
        return contenedor;
    }

    @Override
    public ArrayList<Prestamo> obtenerPrestamos(String clave) {

        return  prestamos.stream()
                .filter(x -> x.getClaveUsuario().equals(clave))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
