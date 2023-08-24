package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.adaptadores.ListaLibrosAdapter;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.servicios.IServicioEstante;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LibrosCRUD extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchView txtBuscar;
    private RecyclerView listaLibros;
    private ArrayList<Libro> listaArrayLibros;
    private FloatingActionButton fabNuevo;
    private ListaLibrosAdapter adapter;
    private ServicioEstante servicioEstante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_crud);

        servicioEstante = new ServicioEstante(getApplicationContext());

        txtBuscar = findViewById(R.id.txtBuscar);
        listaLibros = findViewById(R.id.listaLibros);
        fabNuevo = findViewById(R.id.favNuevo);
        listaLibros.setLayoutManager(new LinearLayoutManager(this));

        listaArrayLibros = servicioEstante.obtenerLibros();

        adapter = new ListaLibrosAdapter(listaArrayLibros, true);
        listaLibros.setAdapter(adapter);

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.listaOriginal){
            Toast.makeText(this, "Lista original", Toast.LENGTH_SHORT).show();
            adapter = new ListaLibrosAdapter( servicioEstante.obtenerLibros(), true);
            listaLibros.setAdapter(adapter);
            return true;
        }

        if (item.getItemId() == R.id.listaEspañol){
            Toast.makeText(this, "Lista de libros en español", Toast.LENGTH_SHORT).show();
            ArrayList<Libro> librosEspañol = servicioEstante.obtenerLibros().stream()
                    .filter(x -> x.getIdioma().trim().equalsIgnoreCase("español"))
                    .collect(Collectors.toCollection(ArrayList::new));
            adapter = new ListaLibrosAdapter(librosEspañol, true);
            listaLibros.setAdapter(adapter);
            return true;
        }

        if (item.getItemId() == R.id.listaIngles){
            Toast.makeText(this, "Lista de libros en ingles", Toast.LENGTH_SHORT).show();
            ArrayList<Libro> librosIngles = servicioEstante.obtenerLibros().stream()
                    .filter(x -> x.getIdioma().trim().equalsIgnoreCase("ingles"))
                    .collect(Collectors.toCollection(ArrayList::new));
            adapter = new ListaLibrosAdapter(librosIngles, true);
            listaLibros.setAdapter(adapter);

            return true;
        }


        return super.onOptionsItemSelected(item);

    }
    private void nuevoRegistro(){
        finish();
        Intent intent = new Intent(this, Registrar_libro.class);
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

}