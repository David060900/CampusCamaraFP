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

import com.example.campuscamarafp.serializable.CursoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class RegistrarseProfesores extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword, etDni;
    private Spinner spinner1;
    ArrayList<String> listaCursos;
    ArrayList<CursoSerial> cursoSerialLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_profesores);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etDni = findViewById(R.id.etDNIProfesores);
        etNombre = findViewById(R.id.etNombreP);
        etApellido = findViewById(R.id.etApellidosP);
        etCorreo = findViewById(R.id.etCorreoRP);
        etPassword = findViewById(R.id.etPasswordRP);
        spinner1 = findViewById(R.id.spinnerCursos2);

        //con un adapter adaptamos a nuestro gusto nuestro spinner
        String cursos [] = {"DAM", "Marketing y Publicidad", "Comercio Internacional", "Transporte y Logística"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_cursos, cursos);
        spinner1.setAdapter(adapter);

        consultarCursos();

        //adaptador para el spinner de cursos
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                R.layout.spinner_cursos, listaCursos);
        spinner1.setAdapter(adaptador);
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
    //metodo que registra a los profesores
    public void registrarProfesores(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String dni = etDni.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidos = etApellido.getText().toString();
        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        int [] valoresModulo = {1,2,3,4,5,6};

        //comprobamos que ninguno de los campos estan vacios
        if(!dni.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
            //tabla alumnos
            db.execSQL("insert into profesores (dni_profesores, nombre, apellidos, correo_profesores, password) " +
                    "values ('" + dni +"', '" + nombre + "', '" + apellidos + "', " +
                    "'" + correo + "', '" + password + "');");
            //tabla imparten
            db.execSQL("insert into imparten (dni_profesores, id_modulo, id_curso) " +
                    "values ('" + dni +"', " + 1 + "," + spinner1.getSelectedItemId()  + 1 + ");");
            Toast.makeText(this,"Profesor '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        db.close();
        etDni.setText("");
        etCorreo.setText("");
        etApellido.setText("");
        etNombre.setText("");
        etPassword.setText("");
    }
}