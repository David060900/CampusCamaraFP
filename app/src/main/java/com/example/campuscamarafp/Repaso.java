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
import com.example.campuscamarafp.utilidades.CrearTablas;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Calendar;

public class Repaso extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private EditText et1, et2;
    private TextView tv1;
    ArrayList<String> listaModulo;
    ArrayList<ModuloSerial> moduloLista;

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
        consultarModulos();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listaModulo);
        spinner1.setAdapter(adaptador);
        String lugar [] = {"Hall CCFP", "Online"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_cursos, lugar);
        spinner2.setAdapter(adapter2);
    }

    private void consultarModulos() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        moduloLista = new ArrayList<ModuloSerial>();
        ModuloSerial modulo = null;
        Cursor cursor = db.rawQuery("select id_modulo, nombre from modulo;", null);

        while(cursor.moveToNext()){
            modulo = new ModuloSerial();
            modulo.setId_modulo(cursor.getInt(0));
            modulo.setNombre(cursor.getString(1));

            moduloLista.add(modulo);
        }
        obtenerListaModulo();
    }

    public void obtenerListaModulo(){
        listaModulo = new ArrayList<String>();

        for(int i = 0; i< moduloLista.size(); i++){
            listaModulo.add(moduloLista.get(i).getNombre());
        }
    }

    //metodo que inserta en la tabla impartir de la base de datos
    public void RegistrarImpartir(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String tiempo_repaso = et1.getText().toString();
        String calendario = tv1.getText().toString();
        String hora = et2.getText().toString();
        String diahora = calendario + " " + hora;
        String modulo = spinner1.getSelectedItem().toString();
        String lugarQuedada = spinner2.getSelectedItem().toString();
        //recibimos datos del alumno
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("dni_impartir");
        String dni_alumno = alumnoSerialRecibe.getDni_alumno();

        ContentValues values = new ContentValues();
        //condicion mientras que no estén vacios los campos de texto
        if(!tiempo_repaso.isEmpty() && !calendario.isEmpty() && !hora.isEmpty()){
            values.put("nombre_modulo", modulo);
            values.put("dia_hora", diahora);
            values.put("lugar", lugarQuedada);
            values.put("horas_repasar", tiempo_repaso);
            values.put("dni_alumnos", dni_alumno);
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
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Repaso.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + month + "/" + year;
            tv1.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
    }
}