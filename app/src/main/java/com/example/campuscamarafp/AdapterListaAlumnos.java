package com.example.campuscamarafp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.campuscamarafp.entidades.Alumno;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AdapterListaAlumnos extends BaseAdapter {

    protected Activity activity;
    protected List<Alumno> listItems;

    public AdapterListaAlumnos(Activity activity, List<Alumno> listItems) {
        this.activity = activity;
        this.listItems = listItems;
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
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista, null);
            Alumno alu = listItems.get(position);
            if(!alu.getNombre().isEmpty()){
                TextView nombre, apellidos;
                nombre=(TextView)v.findViewById(R.id.tvPrueba);
                nombre.setText(alu.getNombre());
                apellidos=(TextView)v.findViewById(R.id.tvPrueba2);
                apellidos.setText(alu.getApellidos());
            }
        }
        return null;
    }
}
