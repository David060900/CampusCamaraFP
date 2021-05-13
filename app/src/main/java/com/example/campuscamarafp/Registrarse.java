package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.utilidades.Utilidades;

public class Registrarse extends AppCompatActivity {

        private EditText etNombre, etApellido, etCorreo, etPassword;
        private Spinner spinner1;
        private RadioButton cb1, cb2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            etNombre = (EditText)findViewById(R.id.etNombre);
            etApellido = (EditText)findViewById(R.id.etApellidos);
            etCorreo = (EditText)findViewById(R.id.etCorreoR);
            etPassword = (EditText)findViewById(R.id.etPasswordR);
            spinner1 = (Spinner)findViewById(R.id.spinnerCursos);
            cb1 = (RadioButton) findViewById(R.id.rbPrimero);
            cb2 = (RadioButton)findViewById(R.id.rbSegundo);

            String cursos [] = {"DAM", "Marketing y Publicidad", "Comercio Internacional", "Comercio Internacional"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, cursos);
            spinner1.setAdapter(adapter);
        }
        //llamamos a la clase que comprueba el registro de usuarios y que no hayan espacios a la hora de registrar
        public void Registrarse (View view){
            registrarUsuarios();
        }

        private void registrarUsuarios() {
            AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            String nombre = etNombre.getText().toString();
            String apellidos = etApellido.getText().toString();
            String correo = etCorreo.getText().toString();
            String password = etPassword.getText().toString();
            String curso = spinner1.getSelectedItem().toString();
            String curso1 = cb1.getText().toString();
            String curso2 = cb2.getText().toString();

            ContentValues values = new ContentValues();

            if(!nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
                if(!curso1.isEmpty()){
                    values.put(Utilidades.CAMPO_NOMBRE_ALUMNOS, nombre);
                    values.put(Utilidades.CAMPO_APELLIDOS_ALUMNOS, apellidos);
                    values.put(Utilidades.CAMPO_CORREO_ALUMNOS, correo);
                    values.put(Utilidades.CAMPO_PASSWORD_ALUMNOS, password);
                    values.put(Utilidades.CAMPO_CURSO_ALUMNOS, curso);
                    values.put(Utilidades.CAMPO_NUMCURSO_ALUMNOS, curso1);
                    Toast.makeText(this,"Alumno '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
                }
                if(!curso2.isEmpty()){
                    values.put(Utilidades.CAMPO_NOMBRE_ALUMNOS, nombre);
                    values.put(Utilidades.CAMPO_APELLIDOS_ALUMNOS, apellidos);
                    values.put(Utilidades.CAMPO_CORREO_ALUMNOS, correo);
                    values.put(Utilidades.CAMPO_PASSWORD_ALUMNOS, password);
                    values.put(Utilidades.CAMPO_CURSO_ALUMNOS, curso);
                    values.put(Utilidades.CAMPO_NUMCURSO_ALUMNOS, curso2);
                    Toast.makeText(this,"Alumno '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
            }
            db.insert(Utilidades.TABLA_ALUMNOS, null, values);
            db.close();
        }

    }
