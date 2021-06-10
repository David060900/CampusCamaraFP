package com.example.campuscamarafp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.ayudas.AyudaPasarLista;
import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.serializable.ProfesorSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class PasarLista extends AppCompatActivity {

    ArrayAdapter<String> adaptador;
    private FloatingActionButton fab;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasarlista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final ListView lv = findViewById(R.id.lista);
        final ArrayList<AlumnoSerial> lista;

        //la lista que declaramos la igualamos a la lista que recoge los datos de la base de datos
        lista = llenar_lv();
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, lista);
        lv.setAdapter(adaptador);

        selectAlumnos();
    }
    //metodo que consulta los alumnos de la base de datos
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void selectAlumnos(){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(PasarLista.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        fab = findViewById(R.id.floatingActionButton2);
        ListView lv = findViewById(R.id.lista);

        //recibimos objetos de la clase inicio sesion
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("profesor_iniciosesion");

        //consulta el id del curso al que pertenece el profesor que ha iniciado sesion
        Cursor curso = bd.rawQuery("select id_curso from imparten " +
                "where dni_profesores = '" + profesorSerialRecibe.getDni_profesores() + "';",null);
        int idcurso = 0;
        while(curso.moveToNext()){
            idcurso = curso.getInt(0);
        }

        //consulta del correo de los alumnos
        Cursor fila = bd.rawQuery("select correo_alumnos, alumnos.dni_alumnos from alumnos left join estudian on " +
                "alumnos.dni_alumnos = estudian.dni_alumnos where estudian.id_curso = '" + idcurso + "';" , null);
        AlumnoSerial alumnoSerial = new AlumnoSerial();
        lv.setOnItemClickListener((parent, view, position, id) -> {
            //recoge la posicion de la fila
            if (fila.moveToPosition(position)) {
                String correo = fila.getString(0);
                String dni = fila.getString(1);
                alumnoSerial.setCorreo(correo);
                alumnoSerial.setDni_alumno(dni);
                lv.getItemIdAtPosition(position);
                long[] posicion = lv.getCheckedItemIds();
                for(int i = 0; i< Arrays.stream(posicion).count(); i++){
                    Toast.makeText(PasarLista.this, "Has seleccionado: " + i, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(PasarLista.this, "Correo: " + correo , Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(v -> {
            //instruccion que incrementa en 1 la columna de las faltas de los alumnos
            bd.execSQL("insert into faltas (num_falta, dni_alumnos, dni_profesores, dia_hora) " +
                    "values (1,'" + alumnoSerial.getDni_alumno() +"' " +
                    ", '" + profesorSerialRecibe.getDni_profesores() + "', datetime('now', 'localtime'));");
            Toast.makeText(PasarLista.this, "Guardar Faltas ", Toast.LENGTH_SHORT).show();
        });

    }
    //metodo que recoge los valores de la base de datos y los proyecta en una lista
    public ArrayList llenar_lv(){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        ArrayList<String> lista = new ArrayList<>();

        //recibimos objetos de la clase inicio sesion
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("profesor_iniciosesion");

        //consulta el id del curso al que pertenece el profesor que ha iniciado sesion
        Cursor curso = bd.rawQuery("select id_curso from imparten " +
                "where dni_profesores = '" + profesorSerialRecibe.getDni_profesores() + "';",null);
        int idcurso = 0;
        while(curso.moveToNext()){
            idcurso = curso.getInt(0);
        }
        //consulta el nombre y los apellidos de los alumnos que estudian el curso que imparte el profesor
        Cursor alumnos = bd.rawQuery("select nombre, apellidos from alumnos left join estudian on" +
                " alumnos.dni_alumnos = estudian.dni_alumnos where estudian.id_curso = '" + idcurso + "';", null);
        //condicion que añade lo consultado en la lista
        if(alumnos.moveToFirst()){
            do{
                lista.add(alumnos.getString(0)
                        + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"
                        + alumnos.getString(1));
            }while(alumnos.moveToNext());
        }
        return lista;
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

}
