package com.example.app_biblioteca.dao;

import android.content.Context;

import com.example.app_biblioteca.modelo.Libro;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LibrosDAO {

    public static void guardarDatos(Context context, ArrayList<Libro> libros) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(context.getFilesDir(), "libros.txt")))) {
            oos.writeObject(libros);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Libro> cargarDatos(Context context) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(context.getFilesDir(), "libros.txt")))) {
            return (ArrayList<Libro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
