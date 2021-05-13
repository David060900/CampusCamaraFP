package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.utilidades.Utilidades;

public class Impartir extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impartir);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1 = (EditText)findViewById(R.id.etTiempoImpartir);
        et2 = (EditText)findViewById(R.id.etDiaSemanaImpartir);
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
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String tiempoImpartir = et1.getText().toString();
        String diaSemanaImpartir = et2.getText().toString();
        String asignaturas = spinner1.getSelectedItem().toString();
        String lugarQuedada = spinner2.getSelectedItem().toString();

        ContentValues values = new ContentValues();

        if(!tiempoImpartir.isEmpty() || !diaSemanaImpartir.isEmpty()){
            values.put(Utilidades.CAMPO_TIEMPO_IMPARTIR, tiempoImpartir);
            values.put(Utilidades.CAMPO_DIA_IMPARTIR, diaSemanaImpartir);
            values.put(Utilidades.CAMPO_NOMBRE_ASIGNATURA, asignaturas);
            values.put(Utilidades.CAMPO_LUGAR_IMPARTIR, lugarQuedada);
            Toast.makeText(this,"Asignatura '" + asignaturas + "' lista para impartir", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert(Utilidades.TABLA_IMPARTIR, null, values);
        db.close();
    }
}
