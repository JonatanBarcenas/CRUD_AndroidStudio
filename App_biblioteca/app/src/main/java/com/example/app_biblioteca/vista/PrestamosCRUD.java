package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.adaptadores.ListaPrestamoAdapter;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.servicios.ServicioPrestamo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PrestamosCRUD extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView txtBuscar;
    private RecyclerView listaPrestamos;
    private ArrayList<Prestamo> listaArrayPrestamo;
    private FloatingActionButton fabNuevo;

    private ListaPrestamoAdapter adapter;
    private ServicioPrestamo servicioPrestamo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos_crud);

        servicioPrestamo = new ServicioPrestamo(getApplicationContext());

        txtBuscar = findViewById(R.id.txtBuscar);
        listaPrestamos = findViewById(R.id.listaPrestamos);
        fabNuevo = findViewById(R.id.favNuevo);
        listaPrestamos.setLayoutManager(new LinearLayoutManager(this));

        listaArrayPrestamo = new ArrayList<>();

        adapter = new ListaPrestamoAdapter(servicioPrestamo.obtenerPrestamos(), getApplicationContext(), true);
        listaPrestamos.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }


    private void nuevoRegistro(){
        finish();
        Intent intent = new Intent(this, RegistarPrestamo.class);
        startActivity(intent);
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