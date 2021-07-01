package com.example.campuscamarafp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PerfilAlumno extends AppCompatActivity{

    private TextView tv1, tv2, tv3, tv4, tv5;
    Spinner spinner1;
    Button btn;
    ArrayList<String> modulos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        tv1 = findViewById(R.id.tvPerfilNombreAlumU);
        tv2 = findViewById(R.id.tvPerfilApellidosAlumU);
        tv3 = findViewById(R.id.tvPerfilCorreoAlumU);
        tv4 = findViewById(R.id.tvTotalFaltasAlumU);
        tv5 = findViewById(R.id.tvPerfilDNIAlumU);
        spinner1 = findViewById(R.id.spinner);
        btn = findViewById(R.id.button2);

        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

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

        //adaptador para el spinner de cursos
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(this,
                R.layout.spinner_cursos, modulos);

        Cursor c = bd.rawQuery("select modulo.nombre from modulo left join estudian " +
                        "on modulo.id_modulo = estudian.id_modulo where estudian.dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "';"
                , null);

        String modulo = null;
        while(c.moveToNext()){
            modulo = c.getString(0);
            modulos.add(modulo);
        }
        spinner1.setAdapter(adaptador);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner1.getSelectedItem().equals("BBDD")){
                    tv4.setText("hola");
                }else{
                    //consulta que hace un count de las faltas de cada alumno
                    Cursor fila = bd.rawQuery("select count(faltas.num_falta), modulo.id_modulo, modulo.horas_modulo from modulo left join faltas " +
                                    "on modulo.id_modulo = faltas.id_modulo where dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "'" +
                                    " and modulo.id_modulo = 1;"
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                        "on modulo.id_modulo = faltas.id_modulo where dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "'" +
                        " and modulo.id_modulo = 1;"
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

    //método que muestra los botones de acción
    public boolean onCreateOptionsMenu (Menu menu){
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        //recibimos los datos de los alumnos
        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("datos_alumnos");

        Cursor countModulo = bd.rawQuery("select count(modulo.id_modulo), modulo.nombre from modulo left join estudian " +
                        "on modulo.id_modulo = estudian.id_modulo where estudian.dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "';"
                , null);

        if(countModulo.moveToFirst()){
            int id_modulo = countModulo.getInt(0);
            String modulo = countModulo.getString(1);
            String [] array = new String[id_modulo];
            for(int i = 0; i<array.length; i++){
                int id = i;
                menu.add(0,id,0,array[i] = modulo);
            }
        }
        return true;
    }
}
