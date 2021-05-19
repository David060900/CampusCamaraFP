package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class PasarLista extends AppCompatActivity {

    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarlista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final ListView lv = (ListView)findViewById(R.id.lista);
        final ArrayList<Alumno> lista;
        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista);
        lv.setAdapter(adaptador);
        selectAlumnos();
        /*ListView lv
        List<Alumno> mAdapter = new ArrayList<Alumno>();
        Alumno match = new Alumno();

        match.setNombre("Hola");
        match.setApellidos("Adios");
        mAdapter.add(match);

        AdapterListaAlumnos adapter2 = new AdapterListaAlumnos(this, mAdapter);
        lv = (ListView) findViewById(R.id.lista);
        lv.setAdapter(adapter2);*/
    }

    public void selectAlumnos(){
        //final ArrayList<Alumno> lista = new ArrayList<>();
        final ListView lv = (ListView)findViewById(R.id.lista);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
            SQLiteDatabase bd = conexion.getWritableDatabase();

            Cursor fila = bd.rawQuery("select nombre, apellidos from alumnos", null);
            Alumno alumno = new Alumno();
            if (fila.moveToPosition(position)) {
                String nombre = fila.getString(0);
                String apellidos = fila.getString(1);
                alumno.setNombre(nombre);
                alumno.setApellidos(apellidos);
                Toast.makeText(PasarLista.this, "Nombre: " + alumno.getNombre()
                            + " Apellidos "+ alumno.getApellidos(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void guardarLista(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        selectAlumnos();
        ContentValues contentValues = new ContentValues();
        contentValues.put("faltas", "6");
        //es un update--bd.insert("alumnos",null, contentValues);
        Toast.makeText(this, "Faltas puestas", Toast.LENGTH_SHORT).show();
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
