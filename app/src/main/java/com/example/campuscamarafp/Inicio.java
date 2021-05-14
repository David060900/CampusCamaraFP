package com.example.campuscamarafp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class Inicio  extends AppCompatActivity {

    private ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lv = (ListView)findViewById(R.id.listaBanco);

        lista = lvBanco();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, lista);
        lv.setAdapter(adaptador);
    }

    public ArrayList lvBanco(){
        ArrayList<String> lista = new ArrayList<>();
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String tabla_lista = "select * from impartir";
        Cursor registro = bd.rawQuery(tabla_lista, null);
        if(registro.moveToFirst()){
            do{
                lista.add(registro.getString(0));
            }while(registro.moveToNext());
        }
        return lista;
    }
    //método que da paso a la actividad Perfil
    public void Perfil (){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("alumno_iniciosesion");

        Cursor fila = bd.rawQuery("select * from " + Utilidades.TABLA_ALUMNOS
                        + " where " + Utilidades.CAMPO_CORREO_ALUMNOS + " = '" + alumnoRecibe.getCorreo()
                        + "'"
                , null);
        //corrección de errores
        try{
            if(fila.moveToFirst()){
                Alumno alumnoEnvia = new Alumno();
                String nom = fila.getString(0);
                alumnoEnvia.setNombre(nom);
                String ape = fila.getString(1);
                alumnoEnvia.setApellidos(ape);
                String cor= fila.getString(2);
                alumnoEnvia.setCorreo(cor);
                String pass = fila.getString(3);
                alumnoEnvia.setPassword(pass);
                String cur = fila.getString(4);
                alumnoEnvia.setCurso(cur);
                String ncur = fila.getString(5);
                alumnoEnvia.setNumcurso(ncur);

                Intent i = new Intent(this, Perfil.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datos_alumnos", alumnoEnvia);
                i.putExtras(bundle);
                startActivity(i);
            }

        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Impartir(View view){
        Intent i = new Intent(this, Impartir.class);
        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("alumno_iniciosesion");
        String correo_alumno = alumnoRecibe.getCorreo();

        Bundle bundle = new Bundle();
        Alumno alumnoEnvia = new Alumno();
        alumnoEnvia.setCorreo(correo_alumno);
        bundle.putSerializable("correo_impartir", alumnoEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }

    //método que muestra los botones de acción
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menuacciones, menu);
        return true;
    }
    //método para agregar las acciones de los botones de acción
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.imgperfil:
                Perfil();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
