package com.example.campuscamarafp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.text.DecimalFormat;

public class PerfilAlumno extends AppCompatActivity{

    private TextView tv1, tv2, tv3, tv4, tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        tv1 = findViewById(R.id.tvPerfilNombreAlumU);
        tv2 = findViewById(R.id.tvPerfilApellidosAlumU);
        tv3 = findViewById(R.id.tvPerfilCorreoAlumU);
        tv4 = findViewById(R.id.tvTotalFaltasAlumU);
        tv5 = findViewById(R.id.tvPerfilDNIAlumU);

        //recoge los datos que se han enviado del alumno y los escribe en Text Views
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("datos_alumnos");

        String nombre_alumno = alumnoSerialRecibe.getNombre();
        String apellido_alumno = alumnoSerialRecibe.getApellidos();
        String correo_alumno = alumnoSerialRecibe.getCorreo();
        String dni_alumno = alumnoSerialRecibe.getDni_alumno();

        tv1.setText(nombre_alumno);
        tv2.setText(apellido_alumno);
        tv3.setText(correo_alumno);
        tv5.setText(dni_alumno);

        verFaltas();
    }
    //metodo que llama a la clase que cambia la contraseña
    public void CambiarPassword(View view){
        Intent i = new Intent(this, CambiarPasswordAlumnos.class);
        AlumnoSerial alumnoSerialEnvia = new AlumnoSerial();
        alumnoSerialEnvia.setDni_alumno(tv5.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("dni_alumno", alumnoSerialEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }
    //metodo que abre una clase RecyclerView que plasma las faltas del alumno
    public void visualizarFaltas(View view){
        Intent i = new Intent(this, VerFaltas.class);
        AlumnoSerial alumnoSerialEnvia = new AlumnoSerial();
        alumnoSerialEnvia.setDni_alumno(tv5.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("dni_alumno", alumnoSerialEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void verFaltas(){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        //recibimos los datos de los alumnos
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("datos_alumnos");
        //consulta que hace un count de las faltas de cada alumno
        Cursor fila = bd.rawQuery("select count(faltas.num_falta), modulo.id_modulo, modulo.horas_modulo from modulo left join faltas " +
                        "on modulo.id_modulo = faltas.id_modulo where dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "';"
                , null);
        while(fila.moveToNext()) {
            double numfaltas = fila.getInt(0);
            int id_modulo = fila.getInt(1);
            double horas = fila.getInt(2);
            DecimalFormat format = new DecimalFormat("#.##");
            double operacion = (numfaltas/horas)*100;
            tv4.setText(format.format(operacion) + "%");
        }
    }
}
