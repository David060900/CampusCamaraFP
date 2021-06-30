package com.example.campuscamarafp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.serializable.ModuloSerial;
import com.example.campuscamarafp.serializable.RepasoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class Repaso extends AppCompatActivity {

    private Spinner spinner2;
    private EditText et1, et2, et3;
    private TextView tv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repaso);

        et1 = findViewById(R.id.etTiempoImpartir);
        tv1 = findViewById(R.id.tvDiaSemanaU);
        et2 = findViewById(R.id.etHoraU);
        et3 = findViewById(R.id.etQueImpartir);
        spinner2 = findViewById(R.id.spinnerLugarQuedar);

        //spinner para decidir el lugar donde quedar
        String lugar [] = {"Hall CCFP", "Online"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_cursos, lugar);
        spinner2.setAdapter(adapter2);
    }
    //metodo que inserta en la tabla impartir de la base de datos
    public void RegistrarImpartir(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String tiempo_repaso = et1.getText().toString();
        String calendario = tv1.getText().toString();
        String hora = et2.getText().toString();
        String diahora = calendario + " " + hora;
        String modulo = et3.getText().toString();
        String lugarQuedada = spinner2.getSelectedItem().toString();
        String nombre = "";
        String apellidos = "";

        //recibimos datos del alumno
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("dni_impartir");
        String dni_alumno = alumnoSerialRecibe.getDni_alumno();

        RepasoSerial repasoSerial = new RepasoSerial();
        Cursor fila = db.rawQuery("select nombre, apellidos from alumnos where dni_alumnos = '" + dni_alumno + "';"
                , null);
        //condicion que recoge esos valores y los inserta en la clase serializable alumno
        if(fila.moveToFirst()){
            nombre = fila.getString(0);
            repasoSerial.setNombre(nombre);
            apellidos = fila.getString(1);
            repasoSerial.setApellidos(apellidos);
        }

        ContentValues values = new ContentValues();
        //condicion mientras que no estén vacios los campos de texto
        if(!tiempo_repaso.isEmpty() && !calendario.isEmpty() && !hora.isEmpty()){
            values.put("nombre_modulo", modulo);
            values.put("dia_hora", diahora);
            values.put("lugar", lugarQuedada);
            values.put("horas_repasar", tiempo_repaso);
            values.put("dni_alumnos", dni_alumno);
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);

            Toast.makeText(this,"Asignatura '" + modulo + "' lista para impartir", Toast.LENGTH_SHORT).show();
            et1.setText("");
            et2.setText("");
            tv1.setText("");
            finish();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert("repaso", null, values);
        db.close();

    }
    //metodo que abre el calendario de la caja de texto
    public void etCalendario(View view) {
        tv1 = findViewById(R.id.tvDiaSemanaU);
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH) + 1;
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Repaso.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + month + "/" + year;
            tv1.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
    }
}