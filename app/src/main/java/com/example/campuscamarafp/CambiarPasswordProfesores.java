package com.example.campuscamarafp;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.campuscamarafp.serializable.ProfesorSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;


public class CambiarPasswordProfesores extends AppCompatActivity {

    private EditText et1, et2, et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarpassword_profesores);

        et1 = findViewById(R.id.etPasswordAntiguaProfe);
        et2 = findViewById(R.id.etPasswordNuevaProfe);
        et3 = findViewById(R.id.etPasswordNuevaProfe2);
    }
    //metodo que comprueba la contraseña antigua y actualiza a la nueva
    public void comprobarPassword(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String password = et1.getText().toString();
        String passwordnueva = et2.getText().toString();
        String passwordnueva2 = et3.getText().toString();

        //recibimos el dni del alumno de la clase objeto
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("dni_profesor");
        String dni = profesorSerialRecibe.getDni_profesores();

        //consulta a la base de datos de la contraseña
        Cursor fila = bd.rawQuery("select password from profesores where password = '"
                        + password + "';"
                , null);

        //corrección de errores
        try{
            if(!password.isEmpty() && !passwordnueva.isEmpty() && !passwordnueva2.isEmpty()){
                if(fila.moveToFirst()){
                    String pass = fila.getString(0);
                    //condicion si coincide, actualizamos a la nueva contraseña
                    if(password.equals(pass) && passwordnueva.equals(passwordnueva2)){
                        //instruccion sql que actualiza los valores en la base de datos
                        bd.execSQL("update profesores set password = '" + passwordnueva + "'"
                                + " where dni_profesores = '" + dni + "';");
                        Intent i = new Intent(this, InicioSesion.class);
                        startActivity(i);
                        Toast.makeText(this, "Contraseña actualizada", Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                Toast.makeText(this, "Escribe en los dos campos", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {//capturamos los errores si hubieran
            Toast.makeText(this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
