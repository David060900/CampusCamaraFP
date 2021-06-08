package com.example.campuscamarafp;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.Curso;

import java.util.ArrayList;

public class RegistrarseProfesores extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword, etDni;
    private Spinner spinner1;
    ArrayList<String> listaCursos;
    ArrayList<Curso> CursoLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesores);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etDni = findViewById(R.id.etDNIProfesores);
        etNombre = (EditText)findViewById(R.id.etNombreP);
        etApellido = (EditText)findViewById(R.id.etApellidosP);
        etCorreo = (EditText)findViewById(R.id.etCorreoRP);
        etPassword = (EditText)findViewById(R.id.etPasswordRP);
        spinner1 = (Spinner)findViewById(R.id.spinnerCursos2);

        //con un adapter adaptamos a nuestro gusto nuestro spinner
        String cursos [] = {"DAM", "Marketing y Publicidad", "Comercio Internacional", "Transporte y Logística"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, cursos);
        spinner1.setAdapter(adapter);

        consultarCursos();
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, listaCursos);
        spinner1.setAdapter(adaptador);

    }

    private void consultarCursos() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        CursoLista = new ArrayList<Curso>();
        Curso curso = null;
        Cursor cursor = db.rawQuery("select id_curso, nombre, num_curso from curso;", null);

        while(cursor.moveToNext()){
            curso = new Curso();
            curso.setId_curso(cursor.getInt(0));
            curso.setNombre(cursor.getString(1));
            curso.setNum_curso(cursor.getString(2));

            CursoLista.add(curso);
        }
        obtenerListaCurso();
    }

    public void obtenerListaCurso(){
        listaCursos = new ArrayList<String>();

        for(int i = 0;i<CursoLista.size();i++){
            listaCursos.add(CursoLista.get(i).getNum_curso() + " ---- " + CursoLista.get(i).getNombre());
        }
    }
    //metodo que registra a los profesores
    public void registrarProfesores(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String dni = etDni.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidos = etApellido.getText().toString();
        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        ContentValues values = new ContentValues();//valores para alumnos
        ContentValues values1 = new ContentValues();//valores para imparten
        //comprobamos que ninguno de los campos estan vacios
        if(!dni.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
            //tabla alumnos
            values.put("dni_profesores", dni);
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("correo_profesores", correo);
            values.put("password", password);
            //tabla imparten
            values1.put("id_curso", spinner1.getSelectedItemId() + 1);
            values1.put("dni_profesores", dni);
            values1.put("id_modulo", 1);

            Toast.makeText(this,"Profesor '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.insert("profesores", null, values);
        db.insert("imparten", null, values1);
        db.close();
        etDni.setText("");
        etCorreo.setText("");
        etApellido.setText("");
        etNombre.setText("");
        etPassword.setText("");
    }

}
