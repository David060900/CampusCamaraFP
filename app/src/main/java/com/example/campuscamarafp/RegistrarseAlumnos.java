package com.example.campuscamarafp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.CursoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class RegistrarseAlumnos extends AppCompatActivity {

    private EditText etNombre, etApellido, etCorreo, etPassword, etDNI;
    private Spinner spinner1;
    private CheckBox cb1,cb2,cb3,cb4;//PRIMERO DAM
    private CheckBox cb5,cb6,cb7,cb8;//PRIMERO MYP
    private CheckBox cb9,cb10,cb11;//PRIMERO CIN
    private Button btn;
    ArrayList<String> listaCursos;
    ArrayList<CursoSerial> cursoSerialLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_alumnos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn = findViewById(R.id.btnRegistrarse);
        etDNI = findViewById(R.id.etDNIAlumnos);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellidos);
        etCorreo = findViewById(R.id.etCorreoR);
        etPassword = findViewById(R.id.etPasswordR);
        spinner1 = findViewById(R.id.spinnerCursos);
        setCheckBox();//relaciona las checkbox declaradas

        consultarCursos();

        //adaptador para el spinner de cursos
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                R.layout.spinner_cursos, listaCursos);
        spinner1.setAdapter(adaptador);
        spinner1.setOnItemSelectedListener(new spinnerSeleccionarAlumnos());
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

    public void registrarAlumnos(View view) {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        String dni = etDNI.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidos = etApellido.getText().toString();
        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        //comprobamos que ninguno de los campos estan vacios
        if(!dni.isEmpty() && !nombre.isEmpty() && !apellidos.isEmpty() && !correo.isEmpty() && !password.isEmpty()){
            //tabla alumnos
            db.execSQL("insert into alumnos (dni_alumnos, nombre, apellidos, correo_alumnos, password) " +
                    "values ('" + dni +"', '" + nombre + "', '" + apellidos + "', " +
                    "'" + correo + "', '" + password + "');");

            insertEstudian(view, dni);

            Toast.makeText(this,"Alumno '" + nombre + "' registrado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Introduce todos los campos", Toast.LENGTH_SHORT).show();
        }
        etDNI.setText("");
        etCorreo.setText("");
        etApellido.setText("");
        etNombre.setText("");
        etPassword.setText("");
    }

    public void insertEstudian(View view, String dni){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        long spinner = spinner1.getSelectedItemId() + 1;
        if(view.getId() == R.id.btnRegistrarse){
            if(cb1.isChecked() == true){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 1 + ", '" + dni + "');");
            }
            if (cb2.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 3 + ", '" + dni + "');");
            }
            if (cb3.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 4 + ", '" + dni + "');");
            }
            if(cb4.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 5 + ", '" + dni + "');");
            }
            if(cb5.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 1 + ", '" + dni + "');");
            }
            if(cb6.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 3 + ", '" + dni + "');");
            }
            if(cb7.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 2 + ", '" + dni + "');");
            }
            if(cb8.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 6 + ", '" + dni + "');");
            }
            if(cb9.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 1 + ", '" + dni + "');");
            }
            if(cb10.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 3 + ", '" + dni + "');");
            }
            if(cb11.isChecked()){
                //tabla estudian
                db.execSQL("insert into estudian (id_curso, id_modulo, dni_alumnos) " +
                        "values (" + spinner + ", " + 2 + ", '" + dni + "');");
            }
        }
    }

    public void setCheckBox(){
        cb1 = findViewById(R.id.cbFOLAlum);
        cb2 = findViewById(R.id.cbInglesAlum);
        cb3 = findViewById(R.id.cbBasesAlum);
        cb4 = findViewById(R.id.cbProgramAlum);
        cb5 = findViewById(R.id.cbFOLMYP1Alum);
        cb6 = findViewById(R.id.cbInglesMYP1Alum);
        cb7 = findViewById(R.id.cbGEYFEMYPAlum);
        cb8 = findViewById(R.id.cbPLMKMYPAlum);
        cb9 = findViewById(R.id.cbFOLCIN1Alum);
        cb10 = findViewById(R.id.cbInglesCIN1Alum);
        cb11 = findViewById(R.id.cbGEYFECINAlum);
    }

    public class spinnerSeleccionarAlumnos implements AdapterView.OnItemSelectedListener{

        RadioGroup rgDAM1 = findViewById(R.id.radioPrimeroDAMAlum);
        RadioGroup rgMYP1 = findViewById(R.id.radioPrimeroMYPAlum);
        RadioGroup rgCIN1 = findViewById(R.id.radioPrimeroCINAlum);

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- DAM")){
                rgDAM1.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.radioPrimeroDAMAlum);
                btn.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                btn.setLayoutParams(params);
            }else{
                rgDAM1.setVisibility(View.GONE);
            }
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- Marketing y Publicidad")){
                rgMYP1.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.radioPrimeroMYPAlum);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                btn.setLayoutParams(params);
            }else{
                rgMYP1.setVisibility(View.GONE);
            }
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- Comercio Internacional")){
                rgCIN1.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.radioPrimeroCINAlum);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                btn.setLayoutParams(params);
            }else{
                rgCIN1.setVisibility(View.GONE);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
