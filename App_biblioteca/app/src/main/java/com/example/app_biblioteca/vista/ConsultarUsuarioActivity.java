package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.adaptadores.ListaLibrosAdapter;
import com.example.app_biblioteca.enums.Genero;
import com.example.app_biblioteca.enums.Orientacion;
import com.example.app_biblioteca.modelo.Domicilio;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.example.app_biblioteca.servicios.ServicioUsuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ConsultarUsuarioActivity extends AppCompatActivity {

    private ServicioUsuarios servicioUsuarios;

    private EditText txtClave;
    private EditText txtNombre;
    private EditText txtApellidoPaterno;
    private EditText txtApellidoMaterno;
    private Spinner spinnerGenero;
    private ArrayAdapter<Genero> generoAdapter;
    private EditText correo;
    private EditText telefono;
    private Button agregarDomicilio;
    private Button btnGuardar;
    private Domicilio domicilio = null;
    private String clave;
    private Usuario usuario;
    private FloatingActionButton fabEditar, fabEliminar;
    private Button btnVerPrestamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        servicioUsuarios = new ServicioUsuarios(getApplicationContext());
        txtClave = findViewById(R.id.txtClave);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidoMaterno = findViewById(R.id.txtApellidoMaterno);
        txtApellidoPaterno = findViewById(R.id.txtApellidoPaterno);
        spinnerGenero = findViewById(R.id.spinnerGenero);
        generoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Genero.values());
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(generoAdapter);
        correo = findViewById(R.id.txtCorreo);
        telefono = findViewById(R.id.txtTelefono);
        agregarDomicilio = findViewById(R.id.btnDomicilio);
        btnGuardar = findViewById(R.id.btnGuarda);
        btnGuardar.setVisibility(View.INVISIBLE);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnVerPrestamos = findViewById(R.id.btnVerPrestamos);

        btnVerPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarLibrosPrestados();
            }
        });

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                clave = null;
            } else {
                clave = extras.getString("Clave");
            }
        } else {
            clave = (String) savedInstanceState.getSerializable("Clave");
        }

        usuario = servicioUsuarios.obtenerUsuario(clave);

        if (usuario!=null){
            txtClave.setText(usuario.getClave());
            txtNombre.setText(usuario.getNombre());
            txtApellidoMaterno.setText(usuario.getApellidoMaterno());
            txtApellidoPaterno.setText(usuario.getApellidoPaterno());
            if (usuario.getGenero() == Genero.MASCULINO){
                spinnerGenero.setSelection(0);
            }else{
                spinnerGenero.setSelection(1);
            }
            correo.setText(usuario.getCorreo());
            telefono.setText(usuario.getTelefono());

            txtClave.setInputType(InputType.TYPE_NULL);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtApellidoMaterno.setInputType(InputType.TYPE_NULL);
            txtApellidoPaterno.setInputType(InputType.TYPE_NULL);
            spinnerGenero.setEnabled(false);
            correo.setInputType(InputType.TYPE_NULL);
            telefono.setInputType(InputType.TYPE_NULL);

        }

        domicilio = usuario.getDomicilio();

        agregarDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFormularioFlotante();
            }
        });

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ConsultarUsuarioActivity.this, EditarUsuarioActivity.class);
                intent.putExtra("Clave", clave);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ConsultarUsuarioActivity.this);
                builder.setMessage("¿Desea eliminar este usuario?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (usuario.isTieneLibro()==0){
                                    if (servicioUsuarios.eliminarUsuario(clave)) {
                                        lista();
                                    }
                                }else{
                                    Toast.makeText(ConsultarUsuarioActivity.this, "El usuario debe un prestamo", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, UsuariosCRUD.class);
        startActivity(intent);
    }


    private void mostrarFormularioFlotante() {

        LayoutInflater inflater = getLayoutInflater();
        View formularioView = inflater.inflate(R.layout.formulario_domicilio, null);



        EditText numero = formularioView.findViewById(R.id.editTextNumero);
        numero.setInputType(InputType.TYPE_NULL);
        EditText calle = formularioView.findViewById(R.id.editTextCalle);
        calle.setInputType(InputType.TYPE_NULL);
        EditText colonia = formularioView.findViewById(R.id.editTextColonia);
        colonia.setInputType(InputType.TYPE_NULL);
        EditText ciudad = formularioView.findViewById(R.id.editTextCiudad);
        ciudad.setInputType(InputType.TYPE_NULL);
        EditText estado = formularioView.findViewById(R.id.editTextEstado);
        estado.setInputType(InputType.TYPE_NULL);
        EditText codigoPostal = formularioView.findViewById(R.id.editTextCP);
        codigoPostal.setInputType(InputType.TYPE_NULL);
        EditText pais = formularioView.findViewById(R.id.editTextPais);
        pais.setInputType(InputType.TYPE_NULL);

        numero.setText(String.valueOf(domicilio.getNumero()));
        calle.setText(domicilio.getCalle());
        colonia.setText(domicilio.getColonia());
        ciudad.setText(domicilio.getCiudad());
        estado.setText(domicilio.getEstado());
        codigoPostal.setText(String.valueOf(domicilio.getCodigoPostal()));
        pais.setText(domicilio.getPais());

        Spinner spinnerOrientacion = formularioView.findViewById(R.id.rspinnerOrientacion);
        ArrayAdapter<Orientacion> orientacionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Orientacion.values());
        orientacionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrientacion.setAdapter(orientacionAdapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarUsuarioActivity.this);
        builder.setView(formularioView);
        builder.setTitle("CONSULTAR DOMICILIO");

        builder.setNegativeButton("Cancelar", null);

        // Mostrar el cuadro de diálogo emergente
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mostrarLibrosPrestados() {

        LayoutInflater inflater = getLayoutInflater();
        View formularioView = inflater.inflate(R.layout.visualizar_prestamos, null);
        ArrayList<Libro> listaArrayLibros = new ArrayList<>();
        ServicioEstante servicioEstante = new ServicioEstante(getApplicationContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarUsuarioActivity.this);
        builder.setView(formularioView);
        builder.setTitle("LIBROS");

        builder.setNegativeButton("Cancelar", null);

        // Mostrar el cuadro de diálogo emergente
        AlertDialog dialog = builder.create();
        dialog.show();

        if (usuario.isTieneLibro()!=0){

            ArrayList<Prestamo> prestamos = servicioUsuarios.obtenerPrestamos(usuario.getClave().trim());
            for (Prestamo prestamo: prestamos){
                Libro libro = servicioEstante.obtenerLibro(prestamo.getIsbn());
                listaArrayLibros.add(libro);
            }

        }else{
            dialog.dismiss();
            Toast.makeText(ConsultarUsuarioActivity.this, "El usuario no tiene prestamos", Toast.LENGTH_SHORT).show();
        }


        RecyclerView lista = formularioView.findViewById(R.id.listaLibros);
        lista.setLayoutManager(new LinearLayoutManager(this));
        ListaLibrosAdapter adapter = new ListaLibrosAdapter(listaArrayLibros, true);
        lista.setAdapter(adapter);


    }


}