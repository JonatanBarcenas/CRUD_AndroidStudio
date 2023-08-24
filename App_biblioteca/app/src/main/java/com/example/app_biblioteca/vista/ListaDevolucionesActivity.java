package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.adaptadores.ListaPrestamoAdapter;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.servicios.ServicioPrestamo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaDevolucionesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView txtBuscar;
    private RecyclerView listaPrestamos;
    private ArrayList<Prestamo> listaArrayPrestamo;

    private ListaPrestamoAdapter adapter;
    private ServicioPrestamo servicioPrestamo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_devoluciones);

        servicioPrestamo = new ServicioPrestamo(getApplicationContext());

        txtBuscar = findViewById(R.id.txtBuscar);
        listaPrestamos = findViewById(R.id.listaPrestamos);
        listaPrestamos.setLayoutManager(new LinearLayoutManager(this));


        listaArrayPrestamo = new ArrayList<>();

        adapter = new ListaPrestamoAdapter(servicioPrestamo.listarDevoluciones(), getApplicationContext(), false);
        listaPrestamos.setAdapter(adapter);


        txtBuscar.setOnQueryTextListener(this);
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}