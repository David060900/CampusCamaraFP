package com.example.campuscamarafp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Usuario;

import java.util.ArrayList;

public class Inicio  extends AppCompatActivity {

    TextView tv;

    AdminSQLiteOpenHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        conn = new AdminSQLiteOpenHelper(this, "campus", null, 1);

        tv = (TextView)findViewById(R.id.txtPrueba);

        Bundle objEnviado = getIntent().getExtras();
        Usuario usuario = null;
        usuario = (Usuario) objEnviado.getSerializable("usuarios");
        tv.setText(usuario.getCorreo());


    }

    public void hola(View view){

    }

}
