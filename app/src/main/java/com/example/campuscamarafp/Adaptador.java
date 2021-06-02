package com.example.campuscamarafp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.campuscamarafp.entidades.ImpartirSerializable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Adaptador extends BaseAdapter {
//https://elbauldelprogramador.com/adapter-personalizado-en-android/
    private ArrayList<ImpartirSerializable> listItems;
    private Context contexto;
    private ArrayList<String> items;

    public Adaptador(ArrayList<ImpartirSerializable> listItems, Context contexto) {
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

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        if(checkBox.isSelected()){
            listItems.get(position);
        }
        /*CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setTag(position);
        if(Inicio.isActionMode){
            checkBox.setVisibility(View.VISIBLE);
        }else{
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position = (int) buttonView.getTag();
                if(Inicio.userSelection.contains(listItems.get(position))){
                    Inicio.userSelection.remove(listItems.get(position));
                }
                Inicio.actionMode.setTitle(Inicio.userSelection.size() + " items");
            }
        });*/

        return convertView;
    }

    /*public void removeItems (List<String> items){
        for(String item : items){
            this.listItems.remove(item);
        }
        notifyDataSetChanged();
    }*/
}
