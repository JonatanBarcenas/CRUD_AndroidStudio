package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_biblioteca.R;

public class HomeActivity extends AppCompatActivity {

    private Button opcionLibros;
    private Button opcionUsuarios;
    private Button opcionPrestamos;
    private Button opcionDevoluciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opcionLibros = findViewById(R.id.buttonLibros);
        opcionUsuarios = findViewById(R.id.buttonUsuarios);
        opcionPrestamos = findViewById(R.id.buttonPrestamos);
        opcionDevoluciones = findViewById(R.id.buttonDevoluciones);

        opcionLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Opción LIBROS seleccionada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, LibrosCRUD.class));
            }
        });

        opcionUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Opción USUARIOS seleccionada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, UsuariosCRUD.class));
            }
        });

        opcionPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Opción PRESTAMOS seleccionada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, PrestamosCRUD.class));
            }
        });

        opcionDevoluciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Opción DEVOLUCIONES seleccionada", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeActivity.this, ListaDevolucionesActivity.class));
            }
        });

    }


}