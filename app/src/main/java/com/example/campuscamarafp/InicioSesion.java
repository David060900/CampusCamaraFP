package com.example.campuscamarafp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InicioSesion extends AppCompatActivity {

    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1 = (EditText)findViewById(R.id.etUsuario);
    }

    public void Registrar (View view){
        Intent i = new Intent(this, Registrarse.class);
        i.putExtra("dato", et1.getText().toString());
        startActivity(i);
        Toast.makeText(this,"Resgistro",Toast.LENGTH_SHORT).show();
    }

}