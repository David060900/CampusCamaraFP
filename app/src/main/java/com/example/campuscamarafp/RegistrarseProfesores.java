package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.utilidades.Utilidades;

public class RegistrarseProfesores extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword;
    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesores);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etNombre = (EditText)findViewById(R.id.etNombreP);
        etApellido = (EditText)findViewById(R.id.etApellidosP);
        etCorreo = (EditText)findViewById(R.id.etCorreoRP);
        etPassword = (EditText)findViewById(R.id.etPasswordRP);
        spinner1 = (Spinner)findViewById(R.id.spinnerCursos2);

        String cursos [] = {"DAM", "Marketing y Publicidad", "Comercio Internacional", "Transporte y Logística"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, cursos);
        spinner1.setAdapter(adapter);

    }

    public void registrarProfesores(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String nombre = etNombre.getText().toString();
        String apellidos = etApellido.getText().toString();
        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();
        String curso = spinner1.getSelectedItem().toString();

        ContentValues values = new ContentValues();

        if(!nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
                values.put(Utilidades.CAMPO_NOMBRE_PROFESORES, nombre);
                values.put(Utilidades.CAMPO_APELLIDOS_PROFESORES, apellidos);
                values.put(Utilidades.CAMPO_CORREO_PROFESORES, correo);
                values.put(Utilidades.CAMPO_PASSWORD_PROFESORES, password);
                values.put(Utilidades.CAMPO_CURSO_PROFESORES, curso);
                Toast.makeText(this,"Profesor '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert(Utilidades.TABLA_PROFESORES, null, values);
        db.close();
    }

}
