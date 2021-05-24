package com.example.campuscamarafp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.entidades.Profesor;
import com.example.campuscamarafp.utilidades.Utilidades;

public class CambiarPasswordAlumnos extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarpassword_alumnos);

        et1 = (EditText) findViewById(R.id.etPasswordAntigua);
        et2 = (EditText) findViewById(R.id.etPasswordNueva);
    }
    //metodo que comprueba la contraseña antigua y actualiza a la nueva
    public void comprobarPassword(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String password = et1.getText().toString();
        String passwordnueva = et2.getText().toString();

        //recibimos los datos del alumno con el que se ha iniciado sesion
        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("correo_alumno");
        String correo = alumnoRecibe.getCorreo();

        //consulta a la base de datos de la contraseña
        Cursor fila = bd.rawQuery("select password from alumnos where password = '"
                        + password + "';"
                , null);

        //corrección de errores
        try{
            if(fila.moveToFirst()){
                String pass = fila.getString(0);
                //condicion si coincide, actualizamos a la nueva contraseña
                if(password.equals(pass)){
                    //instruccion sql que actualiza los valores en la base de datos
                    bd.execSQL("update alumnos set password = '" + passwordnueva + "' "
                            + " where correo = '" + correo + "';");
                }
            }
            Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
