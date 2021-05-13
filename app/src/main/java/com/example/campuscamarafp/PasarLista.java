package com.example.campuscamarafp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class PasarLista extends AppCompatActivity {

    private ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impartir);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lv = (ListView)findViewById(R.id.lista);

        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lv.setAdapter(adaptador);
    }

    public ArrayList llenar_lv(){
        ArrayList<String> lista = new ArrayList<>();
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String tabla_lista = "select * from alumnos";
        Cursor registro = bd.rawQuery(tabla_lista, null);
        if(registro.moveToFirst()){
            do{
                lista.add(registro.getString(0));
            }while(registro.moveToNext());
        }
        return lista;
    }

}
