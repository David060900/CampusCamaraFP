package com.example.campuscamarafp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Usuario;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;

public class Inicio  extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        tv = (TextView)findViewById(R.id.txtPrueba);

        Bundle objEnviado = getIntent().getExtras();
        Usuario usu;
        usu = (Usuario) objEnviado.getSerializable("usuarios");
        tv.setText(usu.getCorreo());


    }

    public void Perfil (View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        Bundle objEnviado = getIntent().getExtras();
        Usuario usu;
        usu = (Usuario) objEnviado.getSerializable("usuarios");

        Cursor fila = bd.rawQuery("select * from " + Utilidades.TABLA_USUARIOS
                        + " where " + Utilidades.CAMPO_CORREO + " = '" + usu.getCorreo()
                        + "'"
                , null);
        //corrección de errores
        try{
            if(fila.moveToFirst()){
                String nom = fila.getString(0);
                String ape = fila.getString(1);
                String cor= fila.getString(2);
                String pass = fila.getString(3);
                String cur = fila.getString(4);
                String ncur = fila.getString(5);
                //condicion si coinciden los datos abrimos la siguiente ventana
                if(usu.getCorreo().equals(cor) && usu.getPassword().equals(pass)){
                    Intent i = new Intent(this, Perfil.class);
                    Usuario usu = new Usuario();
                    usu.setCorreo(correo);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuarios", usu);
                    i.putExtras(bundle);
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
