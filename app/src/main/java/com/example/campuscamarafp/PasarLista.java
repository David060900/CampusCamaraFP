package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class PasarLista extends AppCompatActivity {

    ArrayAdapter<String> adaptador;
    private ListView lv;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarlista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lv = (ListView)findViewById(R.id.lista);
        final ArrayList<Alumno> lista;

        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista);
        lv.setAdapter(adaptador);

        selectAlumnos();
    }

    public void selectAlumnos(){
        lv = (ListView)findViewById(R.id.lista);
        btn = (Button)findViewById(R.id.btnPasarLista);
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        Cursor fila = bd.rawQuery("select correo from alumnos", null);
        Alumno alumno = new Alumno();
        lv.setOnItemClickListener((parent, view, position, id) -> {
            if (fila.moveToPosition(position)) {
                String correo = fila.getString(0);
                alumno.setCorreo(correo);
                Toast.makeText(PasarLista.this, "Correo: " + alumno.getCorreo() , Toast.LENGTH_SHORT).show();
            }
        });
        btn.setOnClickListener(v -> {
            Toast.makeText(PasarLista.this, "Hola " , Toast.LENGTH_SHORT).show();
            bd.execSQL("delete from alumnos where correo = '" + alumno.getCorreo() + "';");
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
        //finish();
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
