package com.example.campuscamarafp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.Calendar;

public class Impartir extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private EditText et1, et2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impartir);

        et1 = (EditText)findViewById(R.id.etTiempoImpartir);
        et2 = (EditText)findViewById(R.id.etDiaSemanaImpartir);
        spinner1 = (Spinner)findViewById(R.id.spinnerAsignaturas);
        spinner2 = (Spinner)findViewById(R.id.spinnerLugarQuedar);

        String asignaturasArray [] = {"FOL", "Informática", "Matemáticas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, asignaturasArray);
        spinner1.setAdapter(adapter);
        String lugar [] = {"Hall CCFP", "Online"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_cursos, lugar);
        spinner2.setAdapter(adapter2);
    }

    public void RegistrarImpartir(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String tiempoImpartir = et1.getText().toString();
        String calendario = et2.getText().toString();
        String asignaturas = spinner1.getSelectedItem().toString();
        String lugarQuedada = spinner2.getSelectedItem().toString();

        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("correo_impartir");
        String correo_alumno = alumnoRecibe.getCorreo();

        ContentValues values = new ContentValues();

        if(!tiempoImpartir.isEmpty() || !et2.getText().toString().isEmpty()){
            values.put(Utilidades.CAMPO_TIEMPO_IMPARTIR, tiempoImpartir);
            values.put(Utilidades.CAMPO_DIA_IMPARTIR, calendario);
            values.put(Utilidades.CAMPO_NOMBRE_ASIGNATURA, asignaturas);
            values.put(Utilidades.CAMPO_LUGAR_IMPARTIR, lugarQuedada);
            values.put(Utilidades.CAMPO_FK_CORREO_ALUMNOS, correo_alumno);
            Toast.makeText(this,"Asignatura '" + asignaturas + "' lista para impartir", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert(Utilidades.TABLA_IMPARTIR, null, values);
        db.close();
    }

    public void etCalendario(View view) {
        et2 = (EditText)findViewById(R.id.etDiaSemanaImpartir);
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Impartir.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + month + "/" + year;
            et2.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
    }
}