package com.example.campuscamarafp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
    public class Registrarse extends AppCompatActivity {

        private Spinner spinner1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_registro);

            spinner1 = (Spinner)findViewById(R.id.spinnerCursos);

            String cursos [] = {"DAM", "Marketing y Publicidad", "Comercio Internacional"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, cursos);
            spinner1.setAdapter(adapter);
        }
    }
