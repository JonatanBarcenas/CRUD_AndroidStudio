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

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.example.app_biblioteca.servicios.ServicioPrestamo;
import com.example.app_biblioteca.servicios.ServicioUsuarios;

public class ConsultarPrestamoActivity extends AppCompatActivity {

private EditText txtFolio;
private EditText txtIsbn;
private EditText txtClaveUsuario;
private EditText txtFechaPrestamo;
private EditText txtFechaDevolucion;
private Button btnDevolver;

private Prestamo prestamo;
private ServicioPrestamo servicioPrestamo;
private ServicioEstante servicioEstante;
private ServicioUsuarios servicioUsuarios;
private String folio = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_prestamo);

        servicioPrestamo = new ServicioPrestamo(getApplicationContext());
        servicioEstante = new ServicioEstante(getApplicationContext());
        servicioUsuarios = new ServicioUsuarios(getApplicationContext());

        txtFolio = findViewById(R.id.txtFolio);
        txtIsbn = findViewById(R.id.txtIsbn);
        txtClaveUsuario = findViewById(R.id.txtClaveUsuario);
        txtFechaPrestamo = findViewById(R.id.txtFechaPrestamo);
        txtFechaDevolucion = findViewById(R.id.txtFechaDevolucion);
        btnDevolver = findViewById(R.id.buttonDevolver);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                folio = null;
            } else {
                folio = extras.getString("Folio");
            }
        } else {
            folio = (String) savedInstanceState.getSerializable("Folio");
        }

        prestamo = servicioPrestamo.consultarPrestamo(folio);

        if(prestamo != null){
            txtFolio.setText(prestamo.getFolio());
            txtIsbn.setText(prestamo.getIsbn());
            txtClaveUsuario.setText(prestamo.getClaveUsuario());
            txtFechaPrestamo.setText(prestamo.getFechaPrestamo());
            txtFechaDevolucion.setText(prestamo.getFechaDevolucion());

            txtFolio.setInputType(InputType.TYPE_NULL);
            txtIsbn.setInputType(InputType.TYPE_NULL);
            txtClaveUsuario.setInputType(InputType.TYPE_NULL);
            txtFechaPrestamo.setInputType(InputType.TYPE_NULL);
            txtFechaDevolucion.setInputType(InputType.TYPE_NULL);
        }

        btnDevolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarPrestamoActivity.this);
                builder.setMessage("¿Desea devolver el libro?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (servicioPrestamo.devolverLibro(folio)) {
                                    Usuario usuario = servicioUsuarios.obtenerUsuario(prestamo.getClaveUsuario());
                                    Libro libro = servicioEstante.obtenerLibro(prestamo.getIsbn());

                                    usuario.setTieneLibro(usuario.isTieneLibro()-1);
                                    libro.setEstaPrestado(false);

                                    int indexLibro = servicioEstante.obtenerPosicion(prestamo.getIsbn());
                                    int indexUsuario = servicioUsuarios.obtenerPosicion(prestamo.getClaveUsuario());

                                    servicioEstante.modificarLibro(indexLibro, libro);
                                    servicioUsuarios.modificarUsuario(indexUsuario, usuario);

                                    lista();
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
        Intent intent = new Intent(this, PrestamosCRUD.class);
        startActivity(intent);
    }
}