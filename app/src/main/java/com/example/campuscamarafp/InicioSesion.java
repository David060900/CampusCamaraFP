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

        String correo = et1.getText().toString();
        String password = et2.getText().toString();

        Cursor fila = bd.rawQuery("select " + Utilidades.CAMPO_CORREO +
                ", " + Utilidades.CAMPO_PASSWORD + " from " + Utilidades.TABLA_USUARIOS
                + " where " + Utilidades.CAMPO_CORREO + " = '" + correo
                        + "' and " + Utilidades.CAMPO_PASSWORD + " = '" + password + "'"
                , null);
        try{
            if(fila.moveToFirst()){
                String cor = fila.getString(0);
                String pass = fila.getString(1);

                if(correo.equals(cor) && password.equals(password)){
                    Intent i = new Intent(this, Inicio.class);
                    startActivity(i);
                    Toast.makeText(this,"Inicio",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}