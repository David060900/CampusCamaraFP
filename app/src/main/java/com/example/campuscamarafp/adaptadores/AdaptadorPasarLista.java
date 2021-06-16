package com.example.campuscamarafp.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.R;
import com.example.campuscamarafp.serializable.AlumnoSerial;

import java.util.ArrayList;

public class AdaptadorPasarLista extends RecyclerView.Adapter<AdaptadorPasarLista.ViewHolderPasarLista> {

    ArrayList<AlumnoSerial> listaAlumnos;

    public AdaptadorPasarLista(ArrayList<AlumnoSerial> listaAlumnos){
        this.listaAlumnos = listaAlumnos;
    }

    @Override
    public ViewHolderPasarLista onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_alumnos,null,false);
        return new ViewHolderPasarLista(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPasarLista holder, int position) {
        holder.tv1.setText(listaAlumnos.get(position).getNombre());
        holder.tv2.setText(listaAlumnos.get(position).getApellidos());
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public class ViewHolderPasarLista extends RecyclerView.ViewHolder {

        TextView tv1,tv2;

        public ViewHolderPasarLista(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView8);
            tv2 = itemView.findViewById(R.id.textView12);

        }
    }
}
