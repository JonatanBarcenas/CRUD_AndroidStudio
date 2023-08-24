package com.example.app_biblioteca.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_biblioteca.R;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.modelo.Prestamo;
import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.servicios.ServicioEstante;
import com.example.app_biblioteca.servicios.ServicioUsuarios;
import com.example.app_biblioteca.vista.ConsultarDevolucionActivity;
import com.example.app_biblioteca.vista.ConsultarPrestamoActivity;
import com.example.app_biblioteca.vista.ConsultarUsuarioActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ListaPrestamoAdapter extends RecyclerView.Adapter<ListaPrestamoAdapter.PrestamoViewHolder>  {

    private ArrayList<Prestamo> listaPrestamo;
    private ArrayList<Prestamo> listaOriginal;
    private ServicioEstante servicioEstante;
    private ServicioUsuarios servicioUsuarios;
    private Context context;
    private boolean esPrestamo;

    public ListaPrestamoAdapter(ArrayList<Prestamo> listaPrestamo, Context context, boolean esPrestamo) {

        this.listaPrestamo = listaPrestamo;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaPrestamo);

        this.context = context;
        servicioEstante = new ServicioEstante(context);
        servicioUsuarios = new ServicioUsuarios(context);
        this.esPrestamo = esPrestamo;

    }

    @NonNull
    @Override
    public ListaPrestamoAdapter.PrestamoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_prestamo, null, false);
        return new ListaPrestamoAdapter.PrestamoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaPrestamoAdapter.PrestamoViewHolder holder, int position) {
        Prestamo prestamo = listaPrestamo.get(position);
        Libro libro = servicioEstante.obtenerLibro(prestamo.getIsbn().trim());
        Usuario usuario = servicioUsuarios.obtenerUsuario(prestamo.getClaveUsuario());
        holder.viewFolio.setText(listaPrestamo.get(position).getFolio());
        holder.viewFechaDevolucion.setText(listaPrestamo.get(position).getFechaDevolucion());
        holder.viewTitulo.setText(libro.getTitulo());
        holder.viewUsuario.setText(usuario.getNombre() + " " + usuario.getApellidoPaterno());

        Calendar calendar = Calendar.getInstance();
        Date fechaActual = calendar.getTime();
        String fechaDevolucionString = prestamo.getFechaDevolucion();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date fechaDevolucion = dateFormat.parse(fechaDevolucionString);

            if (fechaActual.after(fechaDevolucion)) {
                holder.viewFechaDevolucion.setTextColor(Color.RED); // Cambiar a color rojo
            } else {
                holder.viewFechaDevolucion.setTextColor(ContextCompat.getColor(context, R.color.black)); // Cambiar a color negro (opcional)
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaPrestamo.clear();
            listaPrestamo.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Prestamo> collecion = listaPrestamo.stream()
                        .filter(i -> i.getFolio().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaPrestamo.clear();
                listaPrestamo.addAll(collecion);
            } else {
                for (Prestamo c : listaOriginal) {
                    if (c.getFolio().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaPrestamo.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaPrestamo.size();
    }

    public class PrestamoViewHolder extends RecyclerView.ViewHolder {

        private TextView viewFolio, viewTitulo, viewUsuario, viewFechaDevolucion;

        public PrestamoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewFolio = itemView.findViewById(R.id.viewFolio);
            viewTitulo = itemView.findViewById(R.id.viewTitulo);
            viewUsuario = itemView.findViewById(R.id.viewUsuario);
            viewFechaDevolucion = itemView.findViewById(R.id.viewFechaDevolucion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
if (esPrestamo){
    Context context = view.getContext();
    Intent intent = new Intent(context, ConsultarPrestamoActivity.class);
    intent.putExtra("Folio", listaPrestamo.get(getAdapterPosition()).getFolio());
    context.startActivity(intent);
}else{
    Context context = view.getContext();
    Intent intent = new Intent(context, ConsultarDevolucionActivity.class);
    intent.putExtra("Folio", listaPrestamo.get(getAdapterPosition()).getFolio());
    context.startActivity(intent);
}

                }
            });
        }




    }
}
