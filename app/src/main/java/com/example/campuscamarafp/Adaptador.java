package com.example.campuscamarafp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.campuscamarafp.entidades.ImpartirSerializable;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private ArrayList<ImpartirSerializable> listItems;
    private Context contexto;
    private int tipo;

    public Adaptador(ArrayList<ImpartirSerializable> listItems, Context contexto, int type) {
        this.listItems = listItems;
        this.contexto = contexto;
        this.tipo = type;
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
        ImpartirSerializable impartir = (ImpartirSerializable) getItem(position);

        convertView = LayoutInflater.from(contexto).inflate(R.layout.elemento_lista, null);
        TextView tv1, tv2, tv3, tv4;
        tv1 = convertView.findViewById(R.id.tvListaAsignatura);
        tv2 = convertView.findViewById(R.id.tvListaCorreo);
        tv3 = convertView.findViewById(R.id.tvListaDiaHora);
        tv4 = convertView.findViewById(R.id.tvListaHoras);

        tv1.setText(impartir.getAsignatura());
        tv2.setText(impartir.getCorreo_alumnos());
        tv3.setText(impartir.getDia());
        tv4.setText(impartir.getTiempo());

        return convertView;
    }
}
