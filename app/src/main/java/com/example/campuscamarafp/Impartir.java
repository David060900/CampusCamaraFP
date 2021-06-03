package com.example.campuscamarafp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private TextView tv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impartir);

        et1 = (EditText)findViewById(R.id.etTiempoImpartir);
        tv1 = findViewById(R.id.tvDiaSemanaU);
        et2 = findViewById(R.id.etHoraU);
        spinner1 = (Spinner)findViewById(R.id.spinnerAsignaturas);
        spinner2 = (Spinner)findViewById(R.id.spinnerLugarQuedar);
        //declaramos con adaptadores nuestros propios spinners
        String asignaturasArray [] = {"FOL", "Informática", "Matemáticas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, asignaturasArray);
        spinner1.setAdapter(adapter);
        String lugar [] = {"Hall CCFP", "Online"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_cursos, lugar);
        spinner2.setAdapter(adapter2);
    }
    //metodo que inserta en la tabla impartir de la base de datos
    public void RegistrarImpartir(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String tiempoImpartir = et1.getText().toString();
        String calendario = tv1.getText().toString();
        String hora = et2.getText().toString();
        String diahora = calendario + " " + hora;
        String asignaturas = spinner1.getSelectedItem().toString();
        String lugarQuedada = spinner2.getSelectedItem().toString();
        //recibimos datos del alumno
        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("correo_impartir");
        String correo_alumno = alumnoRecibe.getCorreo();

        ContentValues values = new ContentValues();
        //condicion mientras que no estén vacios los campos de texto
        if(!tiempoImpartir.isEmpty() && !calendario.isEmpty() && !hora.isEmpty()){
            values.put(Utilidades.CAMPO_TIEMPO_IMPARTIR, tiempoImpartir);
            values.put(Utilidades.CAMPO_DIA_IMPARTIR, diahora);
            values.put(Utilidades.CAMPO_NOMBRE_ASIGNATURA, asignaturas);
            values.put(Utilidades.CAMPO_LUGAR_IMPARTIR, lugarQuedada);
            values.put(Utilidades.CAMPO_FK_CORREO_ALUMNOS, correo_alumno);
            Toast.makeText(this,"Asignatura '" + asignaturas + "' lista para impartir", Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");
            tv1.setText("");
            finish();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert(Utilidades.TABLA_IMPARTIR, null, values);
        db.close();

    }
    //metodo que abre el calendario de la caja de texto
    public void etCalendario(View view) {
        tv1 = findViewById(R.id.tvDiaSemanaU);
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Impartir.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + month + "/" + year;
            tv1.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
    }
}