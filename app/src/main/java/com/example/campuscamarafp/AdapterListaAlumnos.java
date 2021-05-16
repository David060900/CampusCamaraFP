package com.example.campuscamarafp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.campuscamarafp.entidades.Alumno;

import java.util.ArrayList;

public class AdapterListaAlumnos extends BaseAdapter {

    private Context context;
    private ArrayList<Alumno> listItems;

    public AdapterListaAlumnos(Context context, ArrayList<Alumno> listItems) {
        this.context = context;
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
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista, null);
        }
        TextView nombre, apellidos;
        return null;

    }
}
