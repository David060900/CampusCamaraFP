package com.example.campuscamarafp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.R;
import com.example.campuscamarafp.serializable.AlumnoSerial;

import java.util.ArrayList;

public class AdaptadorPasarLista extends RecyclerView.Adapter<AdaptadorPasarLista.ViewHolderPasarLista> {

    private ArrayList<AlumnoSerial> listaAlumnos;
    private Context context;

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
        final AlumnoSerial alumnoSerial = listaAlumnos.get(position);

        holder.tv1.setText(listaAlumnos.get(position).getNombre() + " " + listaAlumnos.get(position).getApellidos());
        if(alumnoSerial.isSelected()){
            holder.cb1.setChecked(true);
        }else{
            holder.cb1.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public class ViewHolderPasarLista extends RecyclerView.ViewHolder {
        TextView tv1;
        CheckBox cb1;

        public ViewHolderPasarLista(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.textView8);
            cb1 = itemView.findViewById(R.id.checkBox);

            cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Toast.makeText(AdaptadorPasarLista.this.context, "Alumno: " + tv1.getText(), Toast.LENGTH_SHORT).show();
                    }else{

                    }
                }
            });

        }
    }
}
