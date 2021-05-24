package com.example.campuscamarafp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
//actividad que se abre al iniciar la aplicacion
//sirve como transicion
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, InicioSesion.class);
                startActivity(intent);
                finish();
            }
        },2000);//milisegundos que mantiene la actividad abierta
    }
}