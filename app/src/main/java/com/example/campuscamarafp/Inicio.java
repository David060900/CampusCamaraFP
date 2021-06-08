package com.example.campuscamarafp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.ayudas.AyudaImpartir;
import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.serializable.RepasoSerial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Inicio  extends AppCompatActivity {

    private ListView lv;
    private Adaptador adaptador;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lv = (ListView)findViewById(R.id.listaBanco);
        //lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        //lv.setMultiChoiceModeListener(modeListener);

        adaptador = new Adaptador(consultarLista(), this);
        lv.setAdapter(adaptador);

        Quedar();
    }
    //consulta los valores de la tabla impartir de la base de datos
    public ArrayList consultarLista() {
        ArrayList<RepasoSerial> lista = new ArrayList();
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        RepasoSerial impartir = new RepasoSerial();
        //consulta de los valores que recoge de la base de datos
        Cursor registro = bd.rawQuery("select nombre_modulo, dni_alumnos, dia_hora, horas_repasar " +
                "from repaso;", null);

        //condicion que recoge de la consulta para proyectar en la lista
        if (registro.moveToFirst()) {
            do {
                String modulo = registro.getString(0);
                impartir.setModulo(modulo);
                String dni_alumnos = registro.getString(1);
                impartir.setDni_alumnos(dni_alumnos);
                String dia = registro.getString(2);
                impartir.setDia(dia);
                String tiempo = registro.getString(3);
                impartir.setTiempo(tiempo);
                lista.add(new RepasoSerial(modulo,
                        dni_alumnos, dia, tiempo));
            } while (registro.moveToNext());
        }
        return lista;
    }
    //metodo que comprueba la posicion de la lista y elimina de la base de datos y de la lista
    public void Quedar(){
        lv = (ListView)findViewById(R.id.listaBanco);
        //btn = (Button)findViewById(R.id.btnQuedar);
        fab = findViewById(R.id.floatingActionButton);

        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(Inicio.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //consulta del correo del alumno de la base de datos repaso
        Cursor fila = bd.rawQuery("select id_repaso from repaso;", null);

        //llamamos a la clase serializable
        RepasoSerial repasoSerial = new RepasoSerial();

        //le asignamos a la lista que escuche cuando se seleccione un item
        lv.setOnItemClickListener((parent, view, position, id) -> {
            //condicion que recorre la posicion del elemento de la lista
            if (fila.moveToPosition(position)) {
                int id_repaso = fila.getInt(0);
                repasoSerial.setId_repaso(id_repaso);
                Toast.makeText(Inicio.this, "Has elegido a " + repasoSerial.getId_repaso(), Toast.LENGTH_SHORT).show();
            }
        });
        //le asignamos al boton una acción
        fab.setOnClickListener(v -> {
            Toast.makeText(Inicio.this, "Reservando disponibilidad" , Toast.LENGTH_SHORT).show();
            //instruccion que elimina de la base de datos
            bd.execSQL("delete from repaso where id_repaso = '" + repasoSerial.getId_repaso() + "';");
            finish();
        });
    }
    //método que da paso a la actividad Perfil
    public void Perfil (){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //recibe objetos de la clase alumno
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("alumno_iniciosesion");
        //consulta a la base de datos todos los valores
        Cursor fila = bd.rawQuery("select * from alumnos where dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "';"
                , null);
        //corrección de errores
        try{
            //condicion que recoge esos valores y los inserta en la clase serializable alumno
            if(fila.moveToFirst()){
                AlumnoSerial alumnoSerialEnvia = new AlumnoSerial();
                String dni = fila.getString(0);
                alumnoSerialEnvia.setDni_alumno(dni);
                String correo = fila.getString(1);
                alumnoSerialEnvia.setCorreo(correo);
                String nombre= fila.getString(2);
                alumnoSerialEnvia.setNombre(nombre);
                String apellidos = fila.getString(3);
                alumnoSerialEnvia.setApellidos(apellidos);
                String password = fila.getString(4);
                alumnoSerialEnvia.setPassword(password);

                //se envia los datos de los alumno a la clase del perfil del alumno
                Intent i = new Intent(this, PerfilAlumno.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datos_alumnos", alumnoSerialEnvia);
                i.putExtras(bundle);
                startActivity(i);
            }

        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //metodo que llama a la clase impartir
    public void Impartir(View view){
        Intent i = new Intent(this, Repaso.class);
        //se reciben objetos de la clase iniciar sesion de alumno
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("alumno_iniciosesion");
        String dni_alumno = alumnoSerialRecibe.getDni_alumno();

        //se envian objetos del alumno a la clase impartir
        Bundle bundle = new Bundle();
        AlumnoSerial alumnoSerialEnvia = new AlumnoSerial();
        alumnoSerialEnvia.setDni_alumno(dni_alumno);
        bundle.putSerializable("dni_impartir", alumnoSerialEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }
    //metodo que llama a la clase ayuda que se relaciona con impartir
    public void Ayuda(){
        Toast.makeText(this,"Ayuda", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, AyudaImpartir.class);
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
