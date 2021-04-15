package com.example.campuscamarafp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registrarse extends AppCompatActivity {

        private EditText etNombre, etApellido, etCorreo, etPassword;
        private Spinner spinner1;
        private RadioButton cb1, cb2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registro);

            etNombre = (EditText)findViewById(R.id.etNombre);
            etApellido = (EditText)findViewById(R.id.etApellidos);
            etCorreo = (EditText)findViewById(R.id.etCorreo);
            etPassword = (EditText)findViewById(R.id.etPasswordR);
            spinner1 = (Spinner)findViewById(R.id.spinnerCursos);
            cb1 = (RadioButton) findViewById(R.id.rbPrimero);
            cb2 = (RadioButton)findViewById(R.id.rbSegundo);

            String cursos [] = {"DAM", "Marketing y Publicidad", "Comercio Internacional"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, cursos);
            spinner1.setAdapter(adapter);


        }

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
            String cursos = spinner1.toString();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("correo", correo);
            values.put("password", password);


            db.insert("usuarios", null, values);
            db.close();

            Toast.makeText(this,"Usuario '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
        }

        /*public void MensajeEspacios (){
            if(etNombre.getText().toString().isEmpty()){
                Toast.makeText(this,"Introduce el Nombre", Toast.LENGTH_SHORT).show();
            }else{
                if(etApellido.getText().toString().isEmpty()){
                    Toast.makeText(this,"Introduce los Apellidos", Toast.LENGTH_SHORT).show();
                }else{
                    if(etCorreo.getText().toString().isEmpty()){
                        Toast.makeText(this,"Introduce el Correo", Toast.LENGTH_SHORT).show();
                    }else{
                        if(etPassword.getText().toString().isEmpty()){
                            Toast.makeText(this,"Introduce la Contraseña", Toast.LENGTH_SHORT).show();
                        }else{
                            if(!cb1.isChecked() && !cb2.isChecked()){
                                Toast.makeText(this,"Selecciona en que curso estás", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(this,"Registrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        }*/

    }
