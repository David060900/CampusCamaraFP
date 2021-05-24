package com.example.campuscamarafp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.campuscamarafp.entidades.Profesor;


public class CambiarPasswordProfesores extends AppCompatActivity {

    private EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiarpassword_profesores);

        et1 = (EditText) findViewById(R.id.etPasswordAntigua);
        et2 = (EditText) findViewById(R.id.etPasswordNueva);
    }

    public void comprobarPassword(View view){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        String password = et1.getText().toString();
        String passwordnueva = et2.getText().toString();

        Bundle objEnviado = getIntent().getExtras();
        Profesor profesorRecibe;
        profesorRecibe = (Profesor) objEnviado.getSerializable("correo_profesor");
        String correo = profesorRecibe.getCorreo();

        Cursor fila = bd.rawQuery("select password from profesores where password = '"
                        + password + "';"
                , null);
        //corrección de errores
        try{
            if(fila.moveToFirst()){
                String pass = fila.getString(0);
                if(password.equals(pass)){
                    bd.execSQL("update profesores set password = '" + passwordnueva + "' "
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
