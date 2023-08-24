package com.example.app_biblioteca.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app_biblioteca.R;
import com.example.app_biblioteca.modelo.Libro;
import com.example.app_biblioteca.vista.ConsultarLibroActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaLibrosAdapter extends RecyclerView.Adapter<ListaLibrosAdapter.LibroViewHolder> {
    private ArrayList<Libro> listaLibros;
   private  ArrayList<Libro> listaOriginal;
   private boolean esCrud;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String isbn);
    }

    public ListaLibrosAdapter(ArrayList<Libro> listaLibros, boolean esCrud) {
        this.listaLibros = listaLibros;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaLibros);
        this.esCrud = esCrud;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_libro, null, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        holder.viewTitulo.setText(listaLibros.get(position).getTitulo());
        holder.viewIsbn.setText(listaLibros.get(position).getIsbn());
        holder.viewAutor.setText(listaLibros.get(position).getAutor());

        if (!esCrud && listaLibros.get(position).isEstaPrestado()){
            holder.viewTitulo.setTextColor(Color.RED);
        }

    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaLibros.clear();
            listaLibros.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Libro> collecion = listaLibros.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaLibros.clear();
                listaLibros.addAll(collecion);
            } else {
                for (Libro c : listaOriginal) {
                    if (c.getTitulo().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaLibros.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {

        private TextView viewIsbn, viewTitulo, viewAutor, viewEditorial, viewEdicion, viewIdioma, viewVolumen;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);

            viewTitulo = itemView.findViewById(R.id.viewTitulo);
            viewAutor = itemView.findViewById(R.id.viewAutor);
            viewIsbn = itemView.findViewById(R.id.viewIsbn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (esCrud){
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ConsultarLibroActivity.class);
                        intent.putExtra("Isbn", listaLibros.get(getAdapterPosition()).getIsbn());
                        context.startActivity(intent);
                    }else{
                        if (mListener != null) {
                            String isbn = listaLibros.get(getAdapterPosition()).getIsbn();
                            mListener.onItemClick(isbn);
                        }
                    }
                }
            });
        }
    }


}
