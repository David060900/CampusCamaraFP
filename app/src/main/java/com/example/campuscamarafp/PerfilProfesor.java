package com.example.campuscamarafp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
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

    public void CambiarPassword(View view){
        Intent i = new Intent(this, CambiarPassword.class);
        startActivity(i);
    }

    /*public void cargarImagen(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            iv.setImageURI(path);
        }
    }*/

}
