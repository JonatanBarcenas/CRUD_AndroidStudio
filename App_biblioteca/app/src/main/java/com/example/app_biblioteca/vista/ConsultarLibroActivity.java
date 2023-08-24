package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ConsultarLibroActivity extends AppCompatActivity {

    private EditText txtIsbn, txtTitulo, txtAutor, txtEditorial, txtEdicion, txtIdioma, txtVolumen;
    private Button btnGuarda;
    private FloatingActionButton fabEditar, fabEliminar;
    private Libro libro;
    private ServicioEstante servicioEstante;
    private String isbn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_libro);

        servicioEstante = new ServicioEstante(getApplicationContext());

        txtIsbn = findViewById(R.id.txtIsbn);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        txtEditorial = findViewById(R.id.txtEditorial);
        txtEdicion = findViewById(R.id.txtEdicion);
        txtIdioma = findViewById(R.id.txtIdioma);
        txtVolumen = findViewById(R.id.txtVolumen);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnGuarda.setVisibility(View.INVISIBLE);

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

        if(libro != null){
            txtIsbn.setText(libro.getIsbn());
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtEditorial.setText(libro.getEditorial());
            txtEdicion.setText(libro.getEdicion());
            txtIdioma.setText(libro.getIdioma());
            txtVolumen.setText(libro.getVolumen());

            txtIsbn.setInputType(InputType.TYPE_NULL);
            txtTitulo.setInputType(InputType.TYPE_NULL);
            txtAutor.setInputType(InputType.TYPE_NULL);
            txtEditorial.setInputType(InputType.TYPE_NULL);
            txtEdicion.setInputType(InputType.TYPE_NULL);
            txtIdioma.setInputType(InputType.TYPE_NULL);
            txtVolumen.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ConsultarLibroActivity.this, EditarLibroActivity.class);
                intent.putExtra("Isbn", isbn);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarLibroActivity.this);
                builder.setMessage("¿Desea eliminar este libro?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(!libro.isEstaPrestado()){
                                    if (servicioEstante.eliminarLibro(isbn)) {
                                        lista();
                                    }
                                }else{
                                    Toast.makeText(ConsultarLibroActivity.this, "El libro esta prestado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void lista(){
        finish();
        Intent intent = new Intent(this, LibrosCRUD.class);
        startActivity(intent);
    }



}