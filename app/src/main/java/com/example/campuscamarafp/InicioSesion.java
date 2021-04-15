package com.example.campuscamarafp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campuscamarafp.utilidades.Utilidades;

public class InicioSesion extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1 = (EditText)findViewById(R.id.etCorreoIS);
        et2 = (EditText)findViewById(R.id.etPasswordIS);

        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
    }

    public void Registrar (View view){
        Intent i = new Intent(this, Registrarse.class);
        i.putExtra("dato", et1.getText().toString());
        startActivity(i);
        Toast.makeText(this,"Resgistro",Toast.LENGTH_SHORT).show();
    }

    public void Inicio (View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        Cursor fila = bd.rawQuery("select " + Utilidades.CAMPO_CORREO +
                ", " + Utilidades.CAMPO_PASSWORD + " from " + Utilidades.TABLA_USUARIOS
                + " where " + Utilidades.CAMPO_CORREO + " = '" + et1.getText().toString() + "'"
                , null);

        Intent i = new Intent(this, Inicio.class);
        i.putExtra("dato", et1.getText().toString());
        startActivity(i);
        Toast.makeText(this,"Inicio",Toast.LENGTH_SHORT).show();
    }

}