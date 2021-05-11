package com.example.campuscamarafp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Impartir extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impartir);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1 = (EditText)findViewById(R.id.etTiempoImpartir);
        et2 = (EditText)findViewById(R.id.etDiaSemana);
        spinner1 = (Spinner)findViewById(R.id.spinnerAsignaturas);
        spinner2 = (Spinner)findViewById(R.id.spinnerLugarQuedar);

        String asignaturas [] = {"FOL", "Informática", "Matemáticas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, asignaturas);
        spinner1.setAdapter(adapter);
        String lugar [] = {"Hall CCFP", "Online"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_cursos, lugar);
        spinner2.setAdapter(adapter2);
    }

    public void RegistrarImpartir(View view){

    }
}
