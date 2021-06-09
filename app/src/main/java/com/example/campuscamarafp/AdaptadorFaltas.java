package com.example.campuscamarafp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.serializable.FaltasSerial;

import java.util.ArrayList;

public class AdaptadorFaltas extends RecyclerView.Adapter<AdaptadorFaltas.ViewHolderFaltas> {

    ArrayList<FaltasSerial> listaFaltas;

    public AdaptadorFaltas(ArrayList<FaltasSerial> listaFaltas) {
        this.listaFaltas = listaFaltas;
    }

    @Override
    public ViewHolderFaltas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_faltas,null,false);
        return new ViewHolderFaltas(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderFaltas holder, int position) {
        int hola = listaFaltas.get(position).getNum_falta();
        String holaS = String.valueOf(hola);
        holder.tv1.setText(holaS);
        holder.tv2.setText(listaFaltas.get(position).getDni_profesores());
        holder.tv3.setText(listaFaltas.get(position).getDia_hora());
    }

    @Override
    public int getItemCount() {
        return listaFaltas.size();
    }

    public class ViewHolderFaltas extends RecyclerView.ViewHolder {

        TextView tv1,tv2,tv3;

        public ViewHolderFaltas(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView4);
            tv2 = itemView.findViewById(R.id.textView5);
            tv3 = itemView.findViewById(R.id.textView6);
        }
    }
}
