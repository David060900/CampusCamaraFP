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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.ayudas.AyudaImpartir;
import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.entidades.ImpartirSerializable;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class Inicio  extends AppCompatActivity {

    ArrayAdapter<String> adaptador;
    private ListView lv;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final ListView lv = (ListView)findViewById(R.id.listaBanco);
        final ArrayList<Impartir> lista;
        lista = lvBanco();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, lista);
        lv.setAdapter(adaptador);

        Quedar();
    }

    public void Quedar(){
        lv = (ListView)findViewById(R.id.listaBanco);
        btn = (Button)findViewById(R.id.btnQuedar);

        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(Inicio.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        Cursor fila = bd.rawQuery("select correo_alumnos from impartir", null);
        ImpartirSerializable impartir = new ImpartirSerializable();
        lv.setOnItemClickListener((parent, view, position, id) -> {
            if (fila.moveToPosition(position)) {
                String correo = fila.getString(0);
                impartir.setCorreo_alumnos(correo);
                Toast.makeText(Inicio.this, "Correo: " + impartir.getCorreo_alumnos() , Toast.LENGTH_SHORT).show();
            }
        });
        btn.setOnClickListener(v -> {
            Toast.makeText(Inicio.this, "Reservando disponibilidad" , Toast.LENGTH_SHORT).show();
            bd.execSQL("delete from impartir where correo_alumnos = '" + impartir.getCorreo_alumnos() + "';");
            finish();
        });
    }

    public ArrayList lvBanco(){
        ArrayList<String> lista = new ArrayList<>();
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String tabla_lista = "select asignatura, correo_alumnos, dia, tiempo from impartir";
        Cursor registro = bd.rawQuery(tabla_lista, null);
        if(registro.moveToFirst()){
            do{
                lista.add(registro.getString(0)
                        + "\t\t"
                        + registro.getString(1)
                        + "\t\t"
                        + registro.getString(2)
                        + "\t\t"
                        + registro.getString(3));
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
                int faltas = fila.getInt(6);
                alumnoEnvia.setFaltas(faltas);

                Intent i = new Intent(this, PerfilAlumno.class);
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

    public void Ayuda(){
        Toast.makeText(this,"Ayuda", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AyudaImpartir.class);
        startActivity(i);
    }

    //método que muestra los botones de acción
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menuacciones, menu);
        getMenuInflater().inflate(R.menu.menuayuda, menu);
        return true;
    }
    //método para agregar las acciones de los botones de acción
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.imgperfil:
                Perfil();
                return true;
            case R.id.imgayuda:
                Ayuda();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
