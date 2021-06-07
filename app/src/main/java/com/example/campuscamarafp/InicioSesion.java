package com.example.campuscamarafp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.campuscamarafp.ayudas.AyudaInicioSesion;
import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.entidades.Profesor;
import com.example.campuscamarafp.utilidades.Utilidades;

public class InicioSesion extends AppCompatActivity {

    private EditText et1, et2;
    private RadioButton rb1, rb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et1 = (EditText)findViewById(R.id.etCorreoIS);
        et2 = (EditText)findViewById(R.id.etPasswordIS);
        rb1 = (RadioButton) findViewById(R.id.rbAlumno);
        rb2 = (RadioButton) findViewById(R.id.rbProfesor);

        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        /*SQLiteDatabase db = conexion.getWritableDatabase();
        //inserts a modulo
        db.execSQL("insert into modulo values (1,'Programación');");
        db.execSQL("insert into modulo values (2,'FOL');");
        db.execSQL("insert into modulo values (3,'Políticas');");
        db.execSQL("insert into modulo values (4,'Comercio');");
        //inserts a curso
        db.execSQL("insert into curso values (1,'DAM','Primero');");
        db.execSQL("insert into curso values (2,'DAM', 'Segundo');");
        db.execSQL("insert into curso values (3,'Marketing', 'Primero');");
        db.execSQL("insert into curso values (4,'Marketing', 'Segundo');");
        db.execSQL("insert into curso values (5,'Comercio', 'Primero');");
        db.execSQL("insert into curso values (6,'Comercio', 'Segundo');");*/
    }
    //método que abre la ventana para el registro de usuarios
    public void Registrar (View view){
        //condicion si el primer radio button esta seleccionado
        if(rb1.isChecked()) {
            Intent i = new Intent(this, RegistrarseAlumnos.class);
            i.putExtra("dato", et1.getText().toString());
            startActivity(i);
            Toast.makeText(this,"Registro Alumnos",Toast.LENGTH_SHORT).show();
        }
        //condicion si el segundo radio button esta seleccionado
        if(rb2.isChecked()){
            Intent i = new Intent(this, RegistrarseProfesores.class);
            i.putExtra("dato2", et1.getText().toString());
            startActivity(i);
            Toast.makeText(this,"Registro Profesores",Toast.LENGTH_SHORT).show();
        }
    }
    //método para iniciar sesión y abrir otra ventana
    public void Inicio (View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        
        String dni = et1.getText().toString();
        String password = et2.getText().toString();
        //sentencias que comprueban en la base de datos el dni y la contraseña
        Cursor fila = bd.rawQuery("select dni_alumnos, password from alumnos where " +
                        "dni_alumnos = '" + dni + "' and password = '" + password + "';", null);
        Cursor fila2 = bd.rawQuery("select dni_profesores, password from profesores where " +
                "dni_profesores = '" + dni + "' and password = '" + password + "';", null);
        //corrección de errores
        try{
            if(fila.moveToFirst()){
                String cor = fila.getString(0);
                String pass = fila.getString(1);
                //condicion si coinciden los datos abrimos la siguiente ventana
                if(dni.equals(cor) && password.equals(pass)){
                    Intent i = new Intent(this, Inicio.class);
                    Alumno alumnoEnvia = new Alumno();
                    alumnoEnvia.setCorreo(cor);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("alumno_iniciosesion", alumnoEnvia);
                    i.putExtras(bundle);
                    startActivity(i);
                    Toast.makeText(this,"Inicio",Toast.LENGTH_SHORT).show();
                }
            }else if(fila2.moveToFirst()){
                String cor2 = fila2.getString(0);
                String pass2 = fila2.getString(1);
                if(dni.equals(cor2) && password.equals(pass2)){
                    Intent i = new Intent(this, PasarLista.class);
                    Profesor profesorEnvia = new Profesor();
                    profesorEnvia.setCorreo(cor2);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("profesor_iniciosesion", profesorEnvia);
                    i.putExtras(bundle);
                    startActivity(i);
                    Toast.makeText(this,"Lista",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,"Datos incorrectos",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    //método que muestra los botones de acción
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menuayuda, menu);
        return true;
    }
    //método para agregar las acciones de los botones de acción
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        //clase ayuda que hace referencia a la ayuda del inicio de sesion
        if (id == R.id.imgayuda) {
            Toast.makeText(this,"Ayuda", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, AyudaInicioSesion.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}