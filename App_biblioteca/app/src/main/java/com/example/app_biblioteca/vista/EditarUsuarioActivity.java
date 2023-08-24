package com.example.app_biblioteca.vista;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.enums.Genero;
import com.example.app_biblioteca.enums.Orientacion;
import com.example.app_biblioteca.modelo.Domicilio;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.example.app_biblioteca.servicios.ServicioUsuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarUsuarioActivity extends AppCompatActivity {
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
    private Button btnVerPrestamos;

    private FloatingActionButton fabEditar, fabEliminar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        servicioUsuarios = new ServicioUsuarios(getApplicationContext());

        servicioUsuarios = new ServicioUsuarios(getApplicationContext());
        txtClave = findViewById(R.id.txtClave);
        txtClave.setInputType(InputType.TYPE_NULL);
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
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar.setVisibility(View.INVISIBLE);
        btnVerPrestamos = findViewById(R.id.btnVerPrestamos);
        btnVerPrestamos.setVisibility(View.INVISIBLE);

        btnVerPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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



        }

        domicilio = usuario.getDomicilio();

        agregarDomicilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFormularioFlotante();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtClave.getText().toString().equals("") && !txtNombre.getText().toString().equals("") && !txtApellidoPaterno.getText().toString().equals("") && !txtApellidoMaterno.getText().toString().equals("") && !correo.getText().toString().equals("") && !telefono.getText().toString().equals("") && todosLosCamposDomicilioLlenos(domicilio)) {
                   usuario.setNombre(txtNombre.getText().toString());
                   usuario.setApellidoPaterno(txtApellidoPaterno.getText().toString());
                   usuario.setApellidoMaterno(txtApellidoMaterno.getText().toString());
                    Genero generoSeleccionado = (Genero) spinnerGenero.getSelectedItem();
                   usuario.setGenero(generoSeleccionado);
                   usuario.setCorreo(correo.getText().toString());
                   usuario.setTelefono(telefono.getText().toString());
                   usuario.setDomicilio(domicilio);
                    int posicion = servicioUsuarios.obtenerPosicion(clave);


                    if(servicioUsuarios.modificarUsuario(posicion, usuario) != null){
                        Toast.makeText(EditarUsuarioActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarUsuarioActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarUsuarioActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void mostrarFormularioFlotante() {

        LayoutInflater inflater = getLayoutInflater();
        View formularioView = inflater.inflate(R.layout.formulario_domicilio, null);



        EditText numero = formularioView.findViewById(R.id.editTextNumero);

        EditText calle = formularioView.findViewById(R.id.editTextCalle);

        EditText colonia = formularioView.findViewById(R.id.editTextColonia);

        EditText ciudad = formularioView.findViewById(R.id.editTextCiudad);

        EditText estado = formularioView.findViewById(R.id.editTextEstado);

        EditText codigoPostal = formularioView.findViewById(R.id.editTextCP);

        EditText pais = formularioView.findViewById(R.id.editTextPais);

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

        AlertDialog.Builder builder = new AlertDialog.Builder(EditarUsuarioActivity.this);
        builder.setView(formularioView);
        builder.setTitle("MODIFICAR DOMICILIO");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                domicilio = new Domicilio();
                try {
                    domicilio.setNumero(Integer.parseInt(numero.getText().toString()));
                    domicilio.setCodigoPostal(Integer.parseInt(codigoPostal.getText().toString()));
                }catch (NumberFormatException e){
                    Toast.makeText(EditarUsuarioActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                domicilio.setCalle(calle.getText().toString());
                domicilio.setColonia(colonia.getText().toString());
                domicilio.setCiudad(ciudad.getText().toString());
                domicilio.setEstado(estado.getText().toString());
                domicilio.setPais(pais.getText().toString());
                Orientacion orientacionSeleccionada = (Orientacion) spinnerOrientacion.getSelectedItem();
                domicilio.setOrientacion(orientacionSeleccionada);

                Toast.makeText(EditarUsuarioActivity.this, "Domicilio agregado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);

        // Mostrar el cuadro de diálogo emergente
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void verRegistro(){
        finish();
        Intent intent = new Intent(this, ConsultarUsuarioActivity.class);
        intent.putExtra("Clave", clave);
        startActivity(intent);
    }

    private boolean todosLosCamposDomicilioLlenos(Domicilio domicilio) {
        return !domicilio.getCalle().isEmpty() && !domicilio.getColonia().isEmpty() && !domicilio.getCiudad().isEmpty() &&
                !domicilio.getEstado().isEmpty() && !domicilio.getPais().isEmpty();
    }
}
