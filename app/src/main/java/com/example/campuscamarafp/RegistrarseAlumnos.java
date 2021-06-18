package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.CursoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class RegistrarseAlumnos extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword, etDNI;
    private Spinner spinner1;
    ArrayList<String> listaCursos;
    ArrayList<CursoSerial> cursoSerialLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumnos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etDNI = findViewById(R.id.etDNIAlumnos);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellidos);
        etCorreo = findViewById(R.id.etCorreoR);
        etPassword = findViewById(R.id.etPasswordR);
        spinner1 = findViewById(R.id.spinnerCursos);

        consultarCursos();

        //adaptador para el spinner de cursos
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                R.layout.spinner_cursos, listaCursos);
        spinner1.setAdapter(adaptador);
        spinner1.setOnItemSelectedListener(new spinnerSeleccionar());
    }

    public class spinnerSeleccionar implements AdapterView.OnItemSelectedListener{

        RadioGroup rg = findViewById(R.id.radio);

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- DAM")){
                rg.setVisibility(View.VISIBLE);
            }else{
                rg.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    //metodo que consulta los cursos para establecerlos en el spinner
    private void consultarCursos() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        cursoSerialLista = new ArrayList<CursoSerial>();
        CursoSerial cursoSerial = null;
        Cursor cursor = db.rawQuery("select id_curso, nombre, num_curso from curso;", null);

        while(cursor.moveToNext()){
            cursoSerial = new CursoSerial();
            cursoSerial.setId_curso(cursor.getInt(0));
            cursoSerial.setNombre(cursor.getString(1));
            cursoSerial.setNum_curso(cursor.getString(2));

            cursoSerialLista.add(cursoSerial);
        }
        obtenerListaCurso();
    }
    //obtiene los datos de la consulta y los aplica en un array
    public void obtenerListaCurso(){
        listaCursos = new ArrayList<String>();

        for(int i = 0; i< cursoSerialLista.size(); i++){
            listaCursos.add(cursoSerialLista.get(i).getNum_curso() + " ---- " + cursoSerialLista.get(i).getNombre());
        }
    }

    //llamamos al metodo que registra los alumnos
    public void Registrarse (View view){
        registrarUsuarios();
    }

    private void registrarUsuarios() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String dni = etDNI.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidos = etApellido.getText().toString();
        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();
        //comprobamos que ninguno de los campos estan vacios
        if(!dni.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
            //tabla alumnos
            values.put("dni_alumnos", dni);
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("correo_alumnos", correo);
            values.put("password", password);
            //tabla estudian
            values1.put("id_curso", spinner1.getSelectedItemId() + 1);
            values1.put("dni_alumnos", dni);
            values1.put("id_modulo", 1);

            Toast.makeText(this,"Alumno '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert("alumnos", null, values);
        db.insert("estudian", null, values1);
        db.close();
        etDNI.setText("");
        etCorreo.setText("");
        etApellido.setText("");
        etNombre.setText("");
        etPassword.setText("");
    }

}
