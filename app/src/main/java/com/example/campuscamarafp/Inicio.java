package com.example.campuscamarafp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.utilidades.Utilidades;

public class Inicio  extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tv = (TextView)findViewById(R.id.txtPrueba);

        Bundle objEnviado = getIntent().getExtras();
        Alumno usu;
        usu = (Alumno) objEnviado.getSerializable("usu_iniciosesion");
        tv.setText(usu.getCorreo());
    }
    //método que da paso a la actividad Perfil
    public void Perfil (){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        Bundle objEnviado = getIntent().getExtras();
        Alumno usu;
        usu = (Alumno) objEnviado.getSerializable("usu_iniciosesion");

        Cursor fila = bd.rawQuery("select * from " + Utilidades.TABLA_ALUMNOS
                        + " where " + Utilidades.CAMPO_CORREO_ALUMNOS + " = '" + usu.getCorreo()
                        + "'"
                , null);
        //corrección de errores
        try{
            if(fila.moveToFirst()){
                Alumno alum = new Alumno();
                String nom = fila.getString(0);
                alum.setNombre(nom);
                String ape = fila.getString(1);
                alum.setApellidos(ape);
                String cor= fila.getString(2);
                alum.setCorreo(cor);
                String pass = fila.getString(3);
                alum.setPassword(pass);
                String cur = fila.getString(4);
                alum.setCurso(cur);
                String ncur = fila.getString(5);
                alum.setNumcurso(ncur);

                Intent i = new Intent(this, Perfil.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datos_usuarios", alum);
                i.putExtras(bundle);
                startActivity(i);
            }

        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void Impartir(View view){
        Intent i = new Intent(this, Impartir.class);
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
