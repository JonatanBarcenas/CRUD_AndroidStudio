package com.example.app_biblioteca.dao;

import android.content.Context;

import com.example.app_biblioteca.modelo.Prestamo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DevolucionesDAO {
    public static void guardarDatos(Context context, ArrayList<Prestamo> prestamos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(context.getFilesDir(), "Devoluciones.txt")))) {
            oos.writeObject(prestamos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Prestamo> cargarDatos(Context context) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(context.getFilesDir(), "Devoluciones.txt")))) {
            return (ArrayList<Prestamo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
