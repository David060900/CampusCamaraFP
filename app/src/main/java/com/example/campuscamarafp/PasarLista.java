package com.example.campuscamarafp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;

public class PasarLista extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private TextView tv1, tv2;
    private FloatingActionButton fab;
    private String ValorSeleccionado;
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
        spinner2 = findViewById(R.id.spinnerHoras);
        tv1 = findViewById(R.id.tvElegirDia);
        tv2 = findViewById(R.id.tvprueba);

        consultarModulos();

        listaAlumnos = new ArrayList<>();
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

        llenarListaAlumnos();

        String lugar [] = {"8:15 - 9:10", "9:10 - 10:05", "10:05 - 11:00", "11:30 - 12:25", "12:25 - 13:20", "13:20 - 14:15"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_cursos, lugar);
        spinner2.setAdapter(adapter2);
    }

    private void insertarFaltas(String dni_al, String dni_prof) {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        AlumnoSerial alumnoSerial = new AlumnoSerial();
        String dia_hora = tv1.getText().toString() + " " + spinner2.getSelectedItem().toString();
        //instruccion que incrementa en 1 la columna de las faltas de los alumnos
        bd.execSQL("insert into faltas (num_falta, dni_alumnos, dni_profesores, dia_hora) " +
                "values (1,'" + dni_al +"' " +
                ", '" + dni_prof + "', '" + dia_hora +"');");
    }

    //metodo que recoge los valores de la base de datos y los proyecta en una lista
    public void llenarListaAlumnos(){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                ValorSeleccionado=spinner1.getSelectedItem().toString(); //Obtiene el valor del Spinner
                tv2.setText(ValorSeleccionado);
                //consulta el nombre y los apellidos de los alumnos que estudian el modulo que imparte el profesor
                Cursor alumnos = bd.rawQuery("select alumnos.nombre, alumnos.apellidos, alumnos.dni_alumnos from alumnos left join estudian " +
                        " on alumnos.dni_alumnos = estudian.dni_alumnos where estudian.id_modulo in (select id_modulo from modulo" +
                        " where modulo.nombre = '" + ValorSeleccionado + "');", null);
                if(alumnos.moveToFirst()){
                    do{
                        String nombre = alumnos.getString(0);
                        String apellidos = alumnos.getString(1);
                        String dni_alumnos = alumnos.getString(2);
                        listaAlumnos.add(new AlumnoSerial(nombre, apellidos, dni_alumnos));
                        Toast.makeText(PasarLista.this, ValorSeleccionado, Toast.LENGTH_SHORT).show();

                    }while(alumnos.moveToNext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
            //condicion que recoge esos valores y los inserta en la clase serializable profesor
            if(fila.moveToFirst()){
                ProfesorSerial profesorSerialEnvia = new ProfesorSerial();
                String dni = fila.getString(0);
                profesorSerialEnvia.setDni_profesores(dni);
                String correo = fila.getString(1);
                profesorSerialEnvia.setCorreo(correo);
                String nombre= fila.getString(2);
                profesorSerialEnvia.setNombre(nombre);
                String apellidos = fila.getString(3);
                profesorSerialEnvia.setApellidos(apellidos);
                String password = fila.getString(4);
                profesorSerialEnvia.setPassword(password);

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

    //metodo que abre el calendario de la caja de texto
    public void etCalendario(View view) {
        tv1 = findViewById(R.id.tvElegirDia);
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(PasarLista.this, (view1, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + month + "/" + year;
            tv1.setText(fecha);
        }, anio, mes, dia);
        dpd.show();
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
