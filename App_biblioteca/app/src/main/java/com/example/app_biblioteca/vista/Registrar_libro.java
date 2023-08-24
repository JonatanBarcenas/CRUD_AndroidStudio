package com.example.app_biblioteca.vista;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.dao.LibrosDAO;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.servicios.ServicioEstante;

public class Registrar_libro extends AppCompatActivity {

    private ServicioEstante servicioEstante;

    private EditText txtIsbn;
    private EditText txtTitulo;
    private EditText txtAutor;
    private EditText txtEditorial;
    private EditText txtEdicion;
    private EditText txtIdioma;
    private EditText txtVolumen;
    private Button btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_libro);


        servicioEstante = new ServicioEstante(getApplicationContext());


        txtIsbn = findViewById(R.id.editTextIsbn);
        txtTitulo = findViewById(R.id.editTextTitulo);
        txtAutor = findViewById(R.id.editTextAutor);
        txtEditorial = findViewById(R.id.editTextEditorial);
        txtEdicion = findViewById(R.id.editTextEdicion);
        txtIdioma = findViewById(R.id.editTextIdioma);
        txtVolumen = findViewById(R.id.editTextVolumen);

        btnGuardar = findViewById(R.id.buttonRegistrarLibro);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = txtIsbn.getText().toString();
                String titulo = txtTitulo.getText().toString();
                String autor = txtAutor.getText().toString();
                String editorial = txtEditorial.getText().toString();
                String edicion = txtEdicion.getText().toString();
                String idioma = txtIdioma.getText().toString();
                String volumen = txtVolumen.getText().toString();

                Libro libro = new Libro(isbn, titulo, autor, editorial, edicion, idioma, volumen);
                if (!servicioEstante.existe(isbn)){
                    if (!isbn.isEmpty() && !titulo.isEmpty() && !autor.isEmpty() && !edicion.isEmpty() && !editorial.isEmpty()
                            && !idioma.isEmpty() && !volumen.isEmpty()){
                        if (servicioEstante.agregarLibro(libro)){
                            Toast.makeText(getApplicationContext(), "Se registro con exito", Toast.LENGTH_LONG).show();
                            lista();
                        }else{
                            Toast.makeText(getApplicationContext(), "¡Error!", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "¡Todos los datos deben ser llenados!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "¡Este libro ya existe!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void lista(){
        finish();
        Intent intent = new Intent(this, LibrosCRUD.class);
        startActivity(intent);
    }


}