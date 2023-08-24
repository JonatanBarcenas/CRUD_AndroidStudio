package com.example.app_biblioteca.vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.app_biblioteca.R;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarLibroActivity extends AppCompatActivity {
    private EditText txtIsbn, txtTitulo, txtAutor, txtEditorial, txtEdicion, txtIdioma, txtVolumen;
    private Button btnGuarda;
    private FloatingActionButton fabEditar, fabEliminar;
    private Libro libro;
    private ServicioEstante servicioEstante;
    String isbn = null;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_libro);

        servicioEstante = new ServicioEstante(getApplicationContext());

        txtIsbn = findViewById(R.id.txtIsbn);
        txtIsbn.setInputType(InputType.TYPE_NULL);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        txtEditorial = findViewById(R.id.txtEditorial);
        txtEdicion = findViewById(R.id.txtEdicion);
        txtIdioma = findViewById(R.id.txtIdioma);
        txtVolumen = findViewById(R.id.txtVolumen);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                isbn = null;
            } else {
                isbn = extras.getString("Isbn");
            }
        } else {
            isbn = (String) savedInstanceState.getSerializable("Isbn");
        }

        libro = servicioEstante.obtenerLibro(isbn);

        if(libro != null) {
            txtIsbn.setText(libro.getIsbn());
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtEditorial.setText(libro.getEditorial());
            txtEdicion.setText(libro.getEdicion());
            txtIdioma.setText(libro.getIdioma());
            txtVolumen.setText(libro.getVolumen());
        }

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtTitulo.getText().toString().equals("") && !txtAutor.getText().toString().equals("") && !txtEditorial.getText().toString().equals("") && !txtEdicion.getText().toString().equals("") && !txtIdioma.getText().toString().equals("") && !txtVolumen.getText().toString().equals("")) {
                    Libro libroModificado = new Libro(libro.getIsbn(), txtTitulo.getText().toString(), txtAutor.getText().toString(), txtEditorial.getText().toString(), txtEdicion.getText().toString(), txtIdioma.getText().toString(), txtVolumen.getText().toString());
                    int posicion = servicioEstante.obtenerPosicion(libro.getIsbn());

                    if(servicioEstante.modificarLibro(posicion, libroModificado) != null){
                        Toast.makeText(EditarLibroActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarLibroActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarLibroActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void verRegistro(){
        finish();
        Intent intent = new Intent(this, ConsultarLibroActivity.class);
        intent.putExtra("Isbn", isbn);
        startActivity(intent);
    }
}
