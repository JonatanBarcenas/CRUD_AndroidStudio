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
import com.example.app_biblioteca.adaptadores.ListaLibrosAdapter;
import com.example.app_biblioteca.adaptadores.ListaUsuariosAdapter;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.example.app_biblioteca.servicios.ServicioUsuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class UsuariosCRUD extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView txtBuscar;
    private RecyclerView listaUsuarios;
    private ArrayList<Usuario> listaArrayUsuarios;
    private FloatingActionButton fabNuevo;
    private ListaUsuariosAdapter adapter;
    private ServicioUsuarios servicioUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_crud);

        servicioUsuarios = new ServicioUsuarios(getApplicationContext());

        txtBuscar = findViewById(R.id.txtBuscar);
        listaUsuarios = findViewById(R.id.listaUsuarios);
        fabNuevo = findViewById(R.id.favNuevo);
        listaUsuarios.setLayoutManager(new LinearLayoutManager(this));

        listaArrayUsuarios = new ArrayList<>();


        adapter = new ListaUsuariosAdapter(servicioUsuarios.obtenerUsuarios(), true);
        listaUsuarios.setAdapter(adapter);

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
        Intent intent = new Intent(this, RegistrarUsuarioActivity.class);
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