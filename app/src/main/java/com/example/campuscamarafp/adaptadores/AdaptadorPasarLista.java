package com.example.campuscamarafp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.ItemClickListener;
import com.example.campuscamarafp.R;
import com.example.campuscamarafp.serializable.AlumnoSerial;

import java.util.ArrayList;

public class AdaptadorPasarLista extends RecyclerView.Adapter<AdaptadorPasarLista.ViewHolderPasarLista> {

    ArrayList<AlumnoSerial> listaAlumnos;
    Context context;
    public ArrayList<AlumnoSerial> checkedAlumnos = new ArrayList<>();

    public AdaptadorPasarLista(ArrayList<AlumnoSerial> listaAlumnos, Context context){
        this.listaAlumnos = listaAlumnos;
        this.context = context;
    }

    @Override
    public ViewHolderPasarLista onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_alumnos, parent,false);
        return new ViewHolderPasarLista(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderPasarLista holder, final int position) {
        holder.tv1.setText(listaAlumnos.get(position).getNombre() + " " + listaAlumnos.get(position).getApellidos());

        holder.setItemClickListener((view, pos) -> {
            CheckBox checkBox = (CheckBox) view;

            //check if it is else if not
            if(checkBox.isChecked()){
                checkedAlumnos.add(listaAlumnos.get(pos));
            }else if(!checkBox.isChecked()){
                checkedAlumnos.remove(listaAlumnos.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public class ViewHolderPasarLista extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv1;
        CheckBox cb1;
        ItemClickListener itemClickListener;

        public ViewHolderPasarLista(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView8);
            cb1 = itemView.findViewById(R.id.checkBox);

            cb1.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
    }
}
