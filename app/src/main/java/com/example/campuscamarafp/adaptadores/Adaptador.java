package com.example.campuscamarafp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.campuscamarafp.R;
import com.example.campuscamarafp.serializable.RepasoSerial;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
//https://elbauldelprogramador.com/adapter-personalizado-en-android/
    private ArrayList<RepasoSerial> listItems;
    private Context contexto;

    public Adaptador(ArrayList<RepasoSerial> listItems, Context contexto) {
        this.listItems = listItems;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepasoSerial repasoSerial = (RepasoSerial) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista, null);
        TextView tv1, tv2, tv3, tv4;

        tv1 = convertView.findViewById(R.id.tvListaAsignatura);
        tv2 = convertView.findViewById(R.id.tvListaCorreo);
        tv3 = convertView.findViewById(R.id.tvListaDiaHora);
        tv4 = convertView.findViewById(R.id.tvListaHoras);

        tv1.setText(repasoSerial.getModulo());
        tv2.setText(repasoSerial.getDni_alumnos());
        tv3.setText(repasoSerial.getDia());
        tv4.setText(repasoSerial.getTiempo());

        return convertView;
    }
}
