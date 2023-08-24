package com.example.app_biblioteca.dao;

import android.content.Context;

import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UsuariosDAO {

    public static void guardarDatos(Context context, ArrayList<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(context.getFilesDir(), "usuarios.txt")))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Usuario> cargarDatos(Context context) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(context.getFilesDir(), "usuarios.txt")))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
