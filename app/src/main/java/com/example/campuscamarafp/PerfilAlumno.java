package com.example.campuscamarafp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.Alumno;

public class PerfilAlumno extends AppCompatActivity{

    private TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        tv1 = (TextView)findViewById(R.id.tvPerfilNombreU);
        tv2 = (TextView)findViewById(R.id.tvPerfilApellidosU);
        tv3 = (TextView)findViewById(R.id.tvPerfilCorreoU);
        tv4 = (TextView)findViewById(R.id.tvTotalFaltasU);

        //recoge los datos que se han enviado del alumno y los escribe en Text Views
        Bundle objEnviado = getIntent().getExtras();
        Alumno alumnoRecibe;
        alumnoRecibe = (Alumno) objEnviado.getSerializable("datos_alumnos");
        String nombre_alumno = alumnoRecibe.getNombre();
        String apellido_alumno = alumnoRecibe.getApellidos();
        String correo_alumno = alumnoRecibe.getCorreo();
        int faltas = alumnoRecibe.getFaltas();
        String faltasS = String.valueOf(faltas);
        tv1.setText(nombre_alumno);
        tv2.setText(apellido_alumno);
        tv3.setText(correo_alumno);
        tv4.setText(faltasS);
    }
    //metodo que llama a la clase que cambia la contraseña
    public void CambiarPassword(View view){
        Intent i = new Intent(this, CambiarPasswordAlumnos.class);
        Alumno alumnoEnvia = new Alumno();
        alumnoEnvia.setCorreo(tv3.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("correo_alumno", alumnoEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }
}
