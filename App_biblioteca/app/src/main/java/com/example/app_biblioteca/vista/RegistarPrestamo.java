package com.example.app_biblioteca.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.adaptadores.ListaLibrosAdapter;
import com.example.app_biblioteca.adaptadores.ListaUsuariosAdapter;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.example.app_biblioteca.servicios.ServicioPrestamo;
import com.example.app_biblioteca.servicios.ServicioUsuarios;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RegistarPrestamo extends AppCompatActivity {

    private EditText txtIsbn;
    private EditText txtClaveUsuario;
    private TextInputEditText datepickerEdittext;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private ServicioPrestamo servicioPrestamo;
    private ServicioEstante servicioEstante;
    private ServicioUsuarios servicioUsuarios;
    private ImageButton btnBuscar;
    private Button btnGuarda;

    private ImageButton btnBuscarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar_prestamo);

        servicioPrestamo = new ServicioPrestamo(getApplicationContext());
        servicioEstante = new ServicioEstante(getApplicationContext());
        servicioUsuarios = new ServicioUsuarios(getApplicationContext());

        datepickerEdittext = findViewById(R.id.datepicker_edittext);
        txtClaveUsuario = findViewById(R.id.txtClaveUsuario);
        txtIsbn = findViewById(R.id.txtIsbn);
        btnGuarda = findViewById(R.id.btnGuarda);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscarUsuario = findViewById(R.id.btnBuscarClaveUsuario);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarListaLibros();
            }
        });

        btnBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarListaUsuarios();
            }
        });

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        datepickerEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarSelectorFecha();
            }
        });

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveUsuario = txtClaveUsuario.getText().toString().trim();
                String isbnLibro = txtIsbn.getText().toString().trim();
        if (servicioUsuarios.existe(claveUsuario) && !servicioUsuarios.debeLibro(claveUsuario) && !servicioUsuarios.tieneDevolucionesAtrasadas(claveUsuario)){
            if (servicioEstante.existe(isbnLibro) && !servicioEstante.estaDisponible(isbnLibro)){
                Random random = new Random();
                String folio = String.valueOf(random.nextInt(100000));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String fechaPrestamo = dateFormat.format(new Date());
                String fechaDevolucion = datepickerEdittext.getText().toString();

                Prestamo prestamo = new Prestamo(folio, isbnLibro, claveUsuario, fechaPrestamo, fechaDevolucion);
                servicioPrestamo.prestarLibro(prestamo);

                Libro libro = servicioEstante.obtenerLibro(isbnLibro);
                libro.setEstaPrestado(true);
                int posicionLibro = servicioEstante.obtenerPosicion(isbnLibro);
                servicioEstante.modificarLibro(posicionLibro, libro);

                Usuario usuario = servicioUsuarios.obtenerUsuario(claveUsuario);

                usuario.setTieneLibro(usuario.isTieneLibro()+1);
                int usuarioIndex = servicioUsuarios.obtenerPosicion(claveUsuario);
                servicioUsuarios.modificarUsuario(usuarioIndex, usuario);

                Toast.makeText(getApplicationContext(), "Fecha a devolver: \n"+fechaDevolucion, Toast.LENGTH_LONG).show();
                finish();

            }else{
                Toast.makeText(getApplicationContext(), "Libro no disponible", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "El usuario no puede recibir prestamos en este momento", Toast.LENGTH_LONG).show();
        }
                    }
                });
            }

    public void mostrarSelectorFecha() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.set(year, monthOfYear, dayOfMonth);

                Calendar currentDate = Calendar.getInstance();

                if (selectedCalendar.before(currentDate)) {
                    Toast.makeText(getApplicationContext(), "No puedes seleccionar una fecha anterior a la actual", Toast.LENGTH_LONG).show();
                    return;
                }

                String fechaSeleccionada = dateFormat.format(selectedCalendar.getTime());
                datepickerEdittext.setText(fechaSeleccionada);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void mostrarListaLibros() {
        LayoutInflater inflater = getLayoutInflater();
        View formularioView = inflater.inflate(R.layout.ventana_buscar_libro, null);

        SearchView txtBuscar = formularioView.findViewById(R.id.txtBuscar);

        ArrayList<Libro> listaArrayLibros = new ArrayList<>();
        RecyclerView listaLibros = formularioView.findViewById(R.id.listaLibros);

        ListaLibrosAdapter adapter = new ListaLibrosAdapter(servicioEstante.obtenerLibros(), false);
        listaLibros.setLayoutManager(new LinearLayoutManager(formularioView.getContext()));
        listaLibros.setAdapter(adapter);




        txtBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrado(newText);
                return false;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistarPrestamo.this);
        builder.setView(formularioView);
        builder.setTitle("BUSCAR LIBRO");

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        adapter.setOnItemClickListener(new ListaLibrosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String isbn) {
                txtIsbn.setText(isbn);
                dialog.dismiss();
            }
        });
    }

    private void mostrarListaUsuarios() {
        LayoutInflater inflater = getLayoutInflater();
        View formularioView = inflater.inflate(R.layout.ventana_buscar_usuario, null);

        SearchView txtBuscar = formularioView.findViewById(R.id.txtBuscarUsuario);

        ArrayList<Usuario> listaArrayUsuario = new ArrayList<>();
        RecyclerView listaUsuario = formularioView.findViewById(R.id.listaUsuarios);

        ListaUsuariosAdapter adapter = new ListaUsuariosAdapter(servicioUsuarios.obtenerUsuarios(), false);
        listaUsuario.setLayoutManager(new LinearLayoutManager(formularioView.getContext()));
        listaUsuario.setAdapter(adapter);


        txtBuscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrado(newText);
                return false;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistarPrestamo.this);
        builder.setView(formularioView);
        builder.setTitle("BUSCAR USUARIO");

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        adapter.setOnItemClickListener(new ListaUsuariosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String claveUsuario) {
                txtClaveUsuario.setText(claveUsuario);
                dialog.dismiss();
            }
        });

    }





}