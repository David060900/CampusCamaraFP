package com.example.campuscamarafp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.serializable.ProfesorSerial;

public class PerfilProfesor extends AppCompatActivity{

    private TextView tv1, tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_profesor);

        tv1 = (TextView)findViewById(R.id.tvPerfilNombreProfeU);
        tv2 = (TextView)findViewById(R.id.tvPerfilApellidosProfeU);
        tv3 = (TextView)findViewById(R.id.tvPerfilCorreoProfeU);
        tv4 = findViewById(R.id.tvPerfilDNIProfeU);

        //recoge los datos que se han enviado del profesor y los escribe en Text Views
        Bundle objEnviado = getIntent().getExtras();
        ProfesorSerial profesorSerialRecibe;
        profesorSerialRecibe = (ProfesorSerial) objEnviado.getSerializable("datos_profesores");
        String nombreProfesor = profesorSerialRecibe.getNombre();
        String apellidoProfesor = profesorSerialRecibe.getApellidos();
        String correoProfesor = profesorSerialRecibe.getCorreo();
        String dniProfesor = profesorSerialRecibe.getDni_profesores();
        tv1.setText(nombreProfesor);
        tv2.setText(apellidoProfesor);
        tv3.setText(correoProfesor);
        tv4.setText(dniProfesor);
    }
    //metodo que llama a la clase que cambia la contraseña
    public void CambiarPassword(View view){
        Intent i = new Intent(this, CambiarPasswordProfesores.class);
        ProfesorSerial profesorSerialEnvia = new ProfesorSerial();
        profesorSerialEnvia.setDni_profesores(tv4.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("dni_profesor", profesorSerialEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }
}
