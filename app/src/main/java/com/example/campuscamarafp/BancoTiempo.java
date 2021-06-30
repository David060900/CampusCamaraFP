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

import com.example.campuscamarafp.adaptadores.Adaptador;
import com.example.campuscamarafp.ayudas.AyudaImpartir;
import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.serializable.RepasoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BancoTiempo extends AppCompatActivity {

    private ListView lv;
    private Adaptador adaptador;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bancotiempo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lv = findViewById(R.id.listaBanco);
        //adaptador
        adaptador = new Adaptador(consultarLista(), this);
        lv.setAdapter(adaptador);

        Quedar();
    }
    //consulta los valores de la tabla impartir de la base de datos
    public ArrayList consultarLista() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(getApplicationContext(), "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        ArrayList<RepasoSerial> lista = new ArrayList();
        RepasoSerial repasoSerial = new RepasoSerial();

        //consulta de los valores que recoge de la base de datos
        Cursor registro = bd.rawQuery("select nombre_modulo, dia_hora, horas_repasar, nombre, apellidos " +
                "from repaso;", null);

        //condicion que recoge de la consulta para proyectar en la lista
        if (registro.moveToFirst()) {
            do {
                String modulo = registro.getString(0);
                repasoSerial.setModulo(modulo);
                String dia = registro.getString(1);
                repasoSerial.setDia(dia);
                String tiempo = registro.getString(2);
                repasoSerial.setTiempo(tiempo);
                String nombre = registro.getString(3);
                repasoSerial.setNombre(nombre);
                String apellidos = registro.getString(4);
                repasoSerial.setApellidos(apellidos);
                lista.add(new RepasoSerial(modulo,
                        nombre, apellidos, dia, tiempo));
            } while (registro.moveToNext());
        }
        return lista;
    }
    //metodo que comprueba la posicion de la lista y elimina de la base de datos y de la lista
    public void Quedar(){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(getApplicationContext(), "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        lv = findViewById(R.id.listaBanco);
        fab = findViewById(R.id.floatingActionButton);

        //consulta el id_repaso para posteriormente eliminar
        Cursor fila = bd.rawQuery("select id_repaso from repaso;", null);


        //llamamos a la clase serializable
        RepasoSerial repasoSerial = new RepasoSerial();

        //le asignamos a la lista que escuche cuando se seleccione un item
        lv.setOnItemClickListener((parent, view, position, id) -> {
            //condicion que recorre la posicion del elemento de la lista
            if (fila.moveToPosition(position)) {
                int id_repaso = fila.getInt(0);
                repasoSerial.setId_repaso(id_repaso);
                Cursor fila2 = bd.rawQuery("select correo_alumnos from alumnos left join" +
                        " repaso on alumnos.dni_alumnos = repaso.dni_alumnos where" +
                        " repaso.id_repaso = '" + repasoSerial.getId_repaso() + "';",null);
                while(fila2.moveToNext()){
                    String correo = fila2.getString(0);
                    Toast.makeText(BancoTiempo.this, "Enviar correo a: " + correo, Toast.LENGTH_LONG).show();
                }
            }
        });
        //le asignamos al boton una acción
        fab.setOnClickListener(v -> {
            //instruccion que elimina de la base de datos
            bd.execSQL("delete from repaso where id_repaso = '" + repasoSerial.getId_repaso() + "';");
            Toast.makeText(BancoTiempo.this, "Reservando disponibilidad" , Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    //método que da paso a la actividad Perfil
    public void Perfil (){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(getApplicationContext(), "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        //recibe objetos de alumno de la clase inicio sesion
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
        //recibe objetos de alumno de la clase inicio sesion
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("alumno_iniciosesion");
        String dni_alumno = alumnoSerialRecibe.getDni_alumno();

        //se envian objetos del alumno a la clase repaso
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
        getMenuInflater().inflate(R.menu.menuperfil, menu);
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
