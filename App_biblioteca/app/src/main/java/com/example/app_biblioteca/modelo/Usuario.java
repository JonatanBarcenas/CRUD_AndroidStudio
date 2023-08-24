package com.example.app_biblioteca.modelo;

import com.example.app_biblioteca.enums.Genero;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String clave;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Genero genero;
    private String correo;
    private String telefono;
    private Domicilio domicilio;
    private int tieneLibro;

    public Usuario(){

    }

    public Usuario(String clave, String nombre, String apellidoPaterno, String apellidoMaterno, Genero genero, String correo, String telefono, Domicilio domicilio) {
        this.clave = clave;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.genero = genero;
        this.correo = correo;
        this.telefono = telefono;
        this.domicilio = domicilio;
    }

    public int isTieneLibro() {
        return tieneLibro;
    }

    public void setTieneLibro(int tieneLibro) {
        this.tieneLibro = tieneLibro;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", genero=" + genero +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", domicilio=" + domicilio +
                ", tieneLibro=" + tieneLibro +
                '}';
    }
}
