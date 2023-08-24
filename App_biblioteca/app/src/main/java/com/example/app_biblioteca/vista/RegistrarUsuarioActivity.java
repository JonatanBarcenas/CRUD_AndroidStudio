package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.enums.Genero;
import com.example.app_biblioteca.enums.Orientacion;
import com.example.app_biblioteca.modelo.Domicilio;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioUsuarios;

public class RegistrarUsuarioActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        servicioUsuarios = new ServicioUsuarios(getApplicationContext());
        txtClave = findViewById(R.id.editTextClave);
        txtNombre = findViewById(R.id.editTextNombre);
        txtApellidoMaterno = findViewById(R.id.editTextApellidoMaterno);
        txtApellidoPaterno = findViewById(R.id.editTextApellidoPaterno);
        spinnerGenero = findViewById(R.id.rspinnerGenero);
        generoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Genero.values());
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(generoAdapter);
        correo = findViewById(R.id.editTextCorreo);
        telefono = findViewById(R.id.editTextTelefono);
        agregarDomicilio = findViewById(R.id.buttonAgregarDomicilio);

agregarDomicilio.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
mostrarFormularioFlotante();
    }
});
        btnGuardar = findViewById(R.id.buttonRegistrarUsuario);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String clave = txtClave.getText().toString();
                String nombre = txtNombre.getText().toString();
                String apellidoPaterno = txtApellidoPaterno.getText().toString();
                String apellidoMaterno = txtApellidoMaterno.getText().toString();
                Genero generoSeleccionado = (Genero) spinnerGenero.getSelectedItem();
                String correoUsuario = correo.getText().toString();
                String telefonoUsuario = telefono.getText().toString();

                Usuario usuario = new Usuario(clave, nombre, apellidoPaterno, apellidoMaterno, generoSeleccionado, correoUsuario, telefonoUsuario, domicilio);

                if (!servicioUsuarios.existe(clave)) {
                    if (!clave.isEmpty() && !nombre.isEmpty() && !apellidoMaterno.isEmpty() && !apellidoPaterno.isEmpty() && !correoUsuario.isEmpty()
                            && !telefonoUsuario.isEmpty() && domicilio != null && todosLosCamposDomicilioLlenos(domicilio)) {
                        if (servicioUsuarios.agregarUsuario(usuario)) {
                            Toast.makeText(getApplicationContext(), "Se registró con éxito", Toast.LENGTH_LONG).show();
                            lista();
                        } else {
                            Toast.makeText(getApplicationContext(), "¡Error!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "¡Todos los campos son requeridos!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "¡Este usuario ya existe!", Toast.LENGTH_LONG).show();
                    txtClave.requestFocus();
                }


            }
        });

    }

    private void lista(){
        Intent intent = new Intent(this, UsuariosCRUD.class);
        startActivity(intent);
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

        Spinner spinnerOrientacion = formularioView.findViewById(R.id.rspinnerOrientacion);
        ArrayAdapter<Orientacion> orientacionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Orientacion.values());
        orientacionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrientacion.setAdapter(orientacionAdapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrarUsuarioActivity.this);
        builder.setView(formularioView);
        builder.setTitle("AGREGAR DOMICILIO");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                domicilio = new Domicilio();
                try {
                    domicilio.setNumero(Integer.parseInt(numero.getText().toString()));
                    domicilio.setCodigoPostal(Integer.parseInt(codigoPostal.getText().toString()));
                }catch (NumberFormatException e){
                    Toast.makeText(RegistrarUsuarioActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                domicilio.setCalle(calle.getText().toString());
                domicilio.setColonia(colonia.getText().toString());
                domicilio.setCiudad(ciudad.getText().toString());
                domicilio.setEstado(estado.getText().toString());
                domicilio.setPais(pais.getText().toString());
                Orientacion orientacionSeleccionada = (Orientacion) spinnerOrientacion.getSelectedItem();
                domicilio.setOrientacion(orientacionSeleccionada);

                if (domicilio.getCalle().isEmpty() || domicilio.getColonia().isEmpty() || domicilio.getCiudad().isEmpty() ||
                domicilio.getEstado().isEmpty() || domicilio.getPais().isEmpty()){
                    Toast.makeText(RegistrarUsuarioActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarUsuarioActivity.this, "Domicilio agregado", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancelar", null);

        // Mostrar el cuadro de diálogo emergente
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private boolean todosLosCamposDomicilioLlenos(Domicilio domicilio) {
        return !domicilio.getCalle().isEmpty() && !domicilio.getColonia().isEmpty() && !domicilio.getCiudad().isEmpty() &&
                !domicilio.getEstado().isEmpty() && !domicilio.getPais().isEmpty();
    }
}