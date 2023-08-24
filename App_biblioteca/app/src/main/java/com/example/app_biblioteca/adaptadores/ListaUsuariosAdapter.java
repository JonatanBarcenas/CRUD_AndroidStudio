package com.example.app_biblioteca.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_biblioteca.R;

import com.example.app_biblioteca.modelo.Usuario;
import com.example.app_biblioteca.vista.ConsultarLibroActivity;
import com.example.app_biblioteca.vista.ConsultarUsuarioActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuarioViewHolder>  {
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Usuario> listaOriginal;

    private boolean esCrud;

    private ListaUsuariosAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String claveUsuario);
    }

    public ListaUsuariosAdapter(ArrayList<Usuario> listaUsuarios, boolean esCrud) {
        this.listaUsuarios = listaUsuarios;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaUsuarios);
        this.esCrud = esCrud;
    }

    public void setOnItemClickListener(ListaUsuariosAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ListaUsuariosAdapter.UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_usuario, null, false);
        return new ListaUsuariosAdapter.UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaUsuariosAdapter.UsuarioViewHolder holder, int position) {
        holder.viewNombre.setText(listaUsuarios.get(position).getNombre());
        holder.viewApellido.setText(listaUsuarios.get(position).getApellidoPaterno() + " "+listaUsuarios.get(position).getApellidoMaterno());
        holder.viewClave.setText(listaUsuarios.get(position).getClave());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaUsuarios.clear();
            listaUsuarios.addAll(listaOriginal);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Usuario> collecion = listaUsuarios.stream()
                        .filter(i -> i.getClave().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaUsuarios.clear();
                listaUsuarios.addAll(collecion);
            } else {
                for (Usuario c : listaOriginal) {
                    if (c.getClave().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaUsuarios.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        private TextView viewClave, viewNombre, viewApellido;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewApellido = itemView.findViewById(R.id.viewApellido);
            viewClave = itemView.findViewById(R.id.viewClave);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (esCrud){
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ConsultarUsuarioActivity.class);
                        intent.putExtra("Clave", listaUsuarios.get(getAdapterPosition()).getClave());
                        context.startActivity(intent);
                    }else {
                        if (mListener != null) {
                            String clave = listaUsuarios.get(getAdapterPosition()).getClave();
                            mListener.onItemClick(clave);
                        }
                    }
                }
            });
        }
    }
}
