package com.example.campuscamarafp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class PasarLista extends AppCompatActivity {

    private ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarlista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lv = (ListView)findViewById(R.id.lista);

        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista);
        lv.setAdapter(adaptador);
    }

    public void guardarLista(View view){
        lv.getSelectedItem();
        Toast.makeText(this, "Faltas puestas", Toast.LENGTH_LONG).show();
        finish();
    }

    public ArrayList llenar_lv(){
        ArrayList<String> lista = new ArrayList<>();
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String tabla_lista = "select nombre, apellidos from alumnos";
        Cursor registro = bd.rawQuery(tabla_lista, null);
        if(registro.moveToFirst()){
            do{
                lista.add(registro.getString(0)
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                        + registro.getString(1));
            }while(registro.moveToNext());
        }
        return lista;
    }

}
