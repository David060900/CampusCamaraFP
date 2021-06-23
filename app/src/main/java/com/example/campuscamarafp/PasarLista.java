package com.example.campuscamarafp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.adaptadores.AdaptadorPasarLista;
import com.example.campuscamarafp.ayudas.AyudaPasarLista;
import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.serializable.ModuloSerial;
import com.example.campuscamarafp.serializable.ProfesorSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PasarLista extends AppCompatActivity {

    private Spinner spinner1;
    private FloatingActionButton fab;
    ArrayList<AlumnoSerial> listaAlumnos;
    RecyclerView recyclerAlumnos;
    ArrayList<String> listaModulos;
    ArrayList<ModuloSerial> moduloSerialLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarlista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fab = findViewById(R.id.floatingActionButton2);
        spinner1 = findViewById(R.id.spinnerElegirModulo);

        consultarModulos();

        listaAlumnos = new ArrayList<>();
        llenarListaAlumnos();
        AdaptadorPasarLista adapter = new AdaptadorPasarLista(listaAlumnos, getApplicationContext());

        //recibimos objetos de la clase inicio sesion
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("profesor_iniciosesion");

        fab.setOnClickListener(v -> {

            for(AlumnoSerial alumnoSerial : adapter.checkedAlumnos){
                insertarFaltas(alumnoSerial.getDni_alumno(),profesorSerialRecibe.getDni_profesores());
            }

            if(adapter.checkedAlumnos.size()>0){
                Toast.makeText(PasarLista.this, "Faltas guardadas", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(PasarLista.this, "Hoy no ha faltado nadie!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerAlumnos = findViewById(R.id.recyclerAlumnos);
        recyclerAlumnos.setLayoutManager(new LinearLayoutManager(this));
        recyclerAlumnos.setAdapter(adapter);

        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                R.layout.spinner_cursos, listaModulos);
        spinner1.setAdapter(adaptador);
        //spinner1.setOnItemSelectedListener(new spinnerSeleccionarModulos());
    }

    private void insertarFaltas(String dni_al, String dni_prof) {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        AlumnoSerial alumnoSerial = new AlumnoSerial();
        //instruccion que incrementa en 1 la columna de las faltas de los alumnos
        bd.execSQL("insert into faltas (num_falta, dni_alumnos, dni_profesores, dia_hora) " +
                "values (1,'" + dni_al +"' " +
                ", '" + dni_prof + "', datetime('now', 'localtime'));");
    }

    //metodo que recoge los valores de la base de datos y los proyecta en una lista
    public void llenarListaAlumnos(){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //recibimos objetos de la clase inicio sesion
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("profesor_iniciosesion");

        //consulta el id del curso al que pertenece el profesor que ha iniciado sesion
        Cursor curso = bd.rawQuery("select id_modulo from imparten " +
                "where dni_profesores = '" + profesorSerialRecibe.getDni_profesores() + "';",null);
        int idmodulo = 0;
        while(curso.moveToNext()){
            idmodulo = curso.getInt(0);
        }
        //consulta el nombre y los apellidos de los alumnos que estudian el curso que imparte el profesor
        Cursor alumnos = bd.rawQuery("select nombre, apellidos, alumnos.dni_alumnos from alumnos left join estudian on" +
                " alumnos.dni_alumnos = estudian.dni_alumnos where estudian.id_modulo = '" + idmodulo + "' group by alumnos.dni_alumnos;", null);
        if(alumnos.moveToFirst()){
            do{
                String nombre = alumnos.getString(0);
                String apellidos = alumnos.getString(1);
                String dni_alumnos = alumnos.getString(2);
                listaAlumnos.add(new AlumnoSerial(nombre, apellidos, dni_alumnos));
            }while(alumnos.moveToNext());
        }
    }
    //método que da paso a la actividad Perfil
    public void Perfil (){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //recibe objetos de la clase profesor
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("profesor_iniciosesion");

        //consulta a la base de datos todos los valores
        Cursor fila = bd.rawQuery("select * from profesores where dni_profesores " +
                        "= '" + profesorSerialRecibe.getDni_profesores() + "';"
                , null);
        //corrección de errores
        try{
            //condicion que recoge esos valores y los inserta en la clase serializable alumno
            if(fila.moveToFirst()){
                ProfesorSerial profesorSerialEnvia = new ProfesorSerial();
                String dnii = fila.getString(0);
                profesorSerialEnvia.setDni_profesores(dnii);
                String correo = fila.getString(1);
                profesorSerialEnvia.setCorreo(correo);
                String nombre= fila.getString(2);
                profesorSerialEnvia.setNombre(nombre);
                String apellidos = fila.getString(3);
                profesorSerialEnvia.setApellidos(apellidos);
                String passwordd = fila.getString(4);
                profesorSerialEnvia.setPassword(passwordd);

                //se envia los datos de los profesores a la clase del perfil del profesor
                Intent i = new Intent(this, PerfilProfesor.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datos_profesores", profesorSerialEnvia);
                i.putExtras(bundle);
                startActivity(i);
            }

        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //metodo que da paso a la actividad ayuda
    public void Ayuda(){
        Toast.makeText(this,"Ayuda", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AyudaPasarLista.class);
        startActivity(i);
    }

    //método que muestra los botones de acción
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menuacciones, menu);
        getMenuInflater().inflate(R.menu.menuayuda, menu);
        return true;
    }
    //método para agregar las acciones de los botones de acción
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            //clase perfil
            case R.id.imgperfil:
                Perfil();
                return true;
            //clase ayuda
            case R.id.imgayuda:
                Ayuda();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //consulta los modulos para posteriormente añadirlo en el spinner
    private void consultarModulos() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        //recibe objetos de la clase profesor
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("profesor_iniciosesion");

        moduloSerialLista = new ArrayList<ModuloSerial>();
        ModuloSerial modulo = null;
        //consulta el id del modulo y el nombre
        Cursor cursor = db.rawQuery("select modulo.id_modulo, nombre from modulo left join imparten " +
                "on modulo.id_modulo = imparten.id_modulo where " +
                "dni_profesores = '" + profesorSerialRecibe.getDni_profesores() + "';", null);

        while(cursor.moveToNext()){
            modulo = new ModuloSerial();
            modulo.setId_modulo(cursor.getInt(0));
            modulo.setNombre(cursor.getString(1));

            moduloSerialLista.add(modulo);
        }
        obtenerListaModulo();
    }
    //inserta los valores en el spinner
    public void obtenerListaModulo() {
        listaModulos = new ArrayList<String>();

        for (int i = 0; i < moduloSerialLista.size(); i++) {
            listaModulos.add(moduloSerialLista.get(i).getNombre());
        }
    }

    /*public class spinnerSeleccionarModulos implements AdapterView.OnItemSelectedListener{

        RadioGroup rgDAM1 = findViewById(R.id.radioPrimeroDAMAlum);
        RadioGroup rgMYP1 = findViewById(R.id.radioPrimeroMYPAlum);
        RadioGroup rgCIN1 = findViewById(R.id.radioPrimeroCINAlum);

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- DAM")){
                rgDAM1.setVisibility(View.VISIBLE);
            }else{
                rgDAM1.setVisibility(View.GONE);
            }
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- Marketing y Publicidad")){
                rgMYP1.setVisibility(View.VISIBLE);
            }else{
                rgMYP1.setVisibility(View.GONE);
            }
            if(parent.getItemAtPosition(position).toString().equals("Primero ---- Comercio Internacional")){
                rgCIN1.setVisibility(View.VISIBLE);
            }else{
                rgCIN1.setVisibility(View.GONE);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }*/

}
