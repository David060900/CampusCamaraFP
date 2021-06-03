package com.example.campuscamarafp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.ayudas.AyudaImpartir;
import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.entidades.ImpartirSerializable;
import com.example.campuscamarafp.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class Inicio  extends AppCompatActivity {

    private ListView lv;
    private Adaptador adaptador;
    private Button btn;
    private CheckBox chkAll;

    public static boolean isActionMode = false;
    public static List<String> userSelection = new ArrayList<>();
    public static ActionMode actionMode = null;

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

    /*AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menuayuda, menu);

            isActionMode = true;
            actionMode = mode;

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.btnQuedar:
                    adaptador.removeItems(userSelection);
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            actionMode = null;
            userSelection.clear();
        }
    };*/
    //consulta los valores de la tabla impartir de la base de datos
    public ArrayList consultarLista() {
        ArrayList<ImpartirSerializable> lista = new ArrayList();
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        ImpartirSerializable impartir = new ImpartirSerializable();
        //consulta de los valores que recoge de la base de datos
        Cursor registro = bd.rawQuery("select asignatura, correo_alumnos, dia, tiempo " +
                "from impartir", null);

        //condicion que recoge de la consulta para proyectar en la lista
        if (registro.moveToFirst()) {
            do {
                String asignatura = registro.getString(0);
                impartir.setAsignatura(asignatura);
                String correo_alumnos = registro.getString(1);
                impartir.setCorreo_alumnos(correo_alumnos);
                String dia = registro.getString(2);
                impartir.setDia(dia);
                String tiempo = registro.getString(3);
                impartir.setTiempo(tiempo);
                lista.add(new ImpartirSerializable(asignatura,
                        correo_alumnos, dia, tiempo));
            } while (registro.moveToNext());
        }
        return lista;
    }
    //metodo que comprueba la posicion de la lista y elimina de la base de datos y de la lista
    public void Quedar(){
        lv = (ListView)findViewById(R.id.listaBanco);
        btn = (Button)findViewById(R.id.btnQuedar);

        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(Inicio.this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //consulta del correo del alumno de la base de datos impartir
        Cursor fila = bd.rawQuery("select id_impartir, correo_alumnos from impartir;", null);

        //llamamos a la clase serializable
        ImpartirSerializable impartir = new ImpartirSerializable();

        //le asignamos a la lista que escuche cuando se seleccione un item
        lv.setOnItemClickListener((parent, view, position, id) -> {
            //condicion que recorre la posicion del elemento de la lista
            if (fila.moveToPosition(position)) {
                int id_impartir = fila.getInt(0);
                String correo = fila.getString(1);
                impartir.setId_impartir(id_impartir);
                Toast.makeText(Inicio.this, "Has elegido a " + impartir.getCorreo_alumnos(), Toast.LENGTH_SHORT).show();
            }
        });
        //le asignamos al boton una acción
        btn.setOnClickListener(v -> {
            Toast.makeText(Inicio.this, "Reservando disponibilidad" , Toast.LENGTH_SHORT).show();
            //instruccion que elimina de la base de datos
            bd.execSQL("delete from impartir where id_impartir = '" + impartir.getId_impartir() + "';");
            finish();
        });
    }
    //método que da paso a la actividad Perfil
    public void Perfil (){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //recibe objetos de la clase alumno
        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("alumno_iniciosesion");
        //consulta a la base de datos todos los valores
        Cursor fila = bd.rawQuery("select * from " + Utilidades.TABLA_ALUMNOS
                        + " where " + Utilidades.CAMPO_CORREO_ALUMNOS + " = '" + alumnoRecibe.getCorreo()
                        + "'"
                , null);
        //corrección de errores
        try{
            //condicion que recoge esos valores y los inserta en la clase serializable alumno
            if(fila.moveToFirst()){
                Alumno alumnoEnvia = new Alumno();
                String nom = fila.getString(0);
                alumnoEnvia.setNombre(nom);
                String ape = fila.getString(1);
                alumnoEnvia.setApellidos(ape);
                String cor= fila.getString(2);
                alumnoEnvia.setCorreo(cor);
                String pass = fila.getString(3);
                alumnoEnvia.setPassword(pass);
                String cur = fila.getString(4);
                alumnoEnvia.setCurso(cur);
                String ncur = fila.getString(5);
                alumnoEnvia.setNumcurso(ncur);
                int faltas = fila.getInt(6);
                alumnoEnvia.setFaltas(faltas);

                //se envia los datos de los alumno a la clase del perfil del alumno
                Intent i = new Intent(this, PerfilAlumno.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("datos_alumnos", alumnoEnvia);
                i.putExtras(bundle);
                startActivity(i);
            }

        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //metodo que llama a la clase impartir
    public void Impartir(View view){
        Intent i = new Intent(this, Impartir.class);
        //se reciben objetos de la clase iniciar sesion de alumno
        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("alumno_iniciosesion");
        String correo_alumno = alumnoRecibe.getCorreo();

        //se envian objetos del alumno a la clase impartir
        Bundle bundle = new Bundle();
        Alumno alumnoEnvia = new Alumno();
        alumnoEnvia.setCorreo(correo_alumno);
        bundle.putSerializable("correo_impartir", alumnoEnvia);
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
