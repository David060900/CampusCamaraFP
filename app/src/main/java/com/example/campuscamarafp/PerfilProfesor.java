package com.example.campuscamarafp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Profesor;

public class PerfilProfesor extends AppCompatActivity{

    private TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_profesor);

        tv1 = (TextView)findViewById(R.id.tvPerfilNombreU);
        tv2 = (TextView)findViewById(R.id.tvPerfilApellidosU);
        tv3 = (TextView)findViewById(R.id.tvPerfilCorreoU);

        //recoge los datos que se han enviado del profesor y los escribe en Text Views
        Bundle objEnviado = getIntent().getExtras();
        Profesor profesorRecibe;
        profesorRecibe = (Profesor) objEnviado.getSerializable("datos_profesores");
        String nombreProfesor = profesorRecibe.getNombre();
        String apellidoProfesor = profesorRecibe.getApellidos();
        String correoProfesor = profesorRecibe.getCorreo();
        tv1.setText(nombreProfesor);
        tv2.setText(apellidoProfesor);
        tv3.setText(correoProfesor);
    }
    //metodo que llama a la clase que cambia la contraseña
    public void CambiarPassword(View view){
        Intent i = new Intent(this, CambiarPasswordProfesores.class);
        Profesor profesorEnvia = new Profesor();
        profesorEnvia.setCorreo(tv3.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putSerializable("correo_profesor", profesorEnvia);
        i.putExtras(bundle);
        startActivity(i);
    }
}
