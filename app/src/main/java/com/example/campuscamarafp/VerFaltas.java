package com.example.campuscamarafp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.adaptadores.AdaptadorFaltas;
import com.example.campuscamarafp.serializable.AlumnoSerial;
import com.example.campuscamarafp.serializable.FaltasSerial;
import com.example.campuscamarafp.sqlite.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class VerFaltas extends AppCompatActivity {

    ArrayList<FaltasSerial> listaFaltas;
    RecyclerView recyclerFaltas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfaltas);

        listaFaltas = new ArrayList<>();
        recyclerFaltas = findViewById(R.id.recyclerFaltas);
        recyclerFaltas.setLayoutManager(new LinearLayoutManager(this));

        llenarLista();

        AdaptadorFaltas adapter = new AdaptadorFaltas(listaFaltas);
        recyclerFaltas.setAdapter(adapter);
    }
    //metodo que llena la lista de faltas con un RecyclerView
    private void llenarLista() {
        AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this, "campus", null, 1);
        SQLiteDatabase bd = conexion.getWritableDatabase();

        Bundle objEnviado = getIntent().getExtras();
        AlumnoSerial alumnoSerialRecibe;
        alumnoSerialRecibe = (AlumnoSerial) objEnviado.getSerializable("dni_alumno");

        FaltasSerial faltasSerial = new FaltasSerial();

        Cursor faltas = bd.rawQuery("select num_falta, profesores.nombre, profesores.apellidos, dia_hora " +
                "from faltas left join profesores on faltas.dni_profesores = profesores.dni_profesores " +
                "where dni_alumnos = '" + alumnoSerialRecibe.getDni_alumno() + "';",null);
        //condicion que recoge de la consulta para proyectar en la lista
        if (faltas.moveToFirst()) {
            do {
                int num_falta = faltas.getInt(0);
                faltasSerial.setNum_falta(num_falta);
                String nombre = faltas.getString(1);
                faltasSerial.setNombre(nombre);
                String apellidos = faltas.getString(2);
                faltasSerial.setApellidos(apellidos);
                String dia_hora = faltas.getString(3);
                faltasSerial.setDia_hora(dia_hora);
                listaFaltas.add(new FaltasSerial(num_falta,
                        nombre, apellidos, dia_hora));
            } while (faltas.moveToNext());
        }
    }
}
