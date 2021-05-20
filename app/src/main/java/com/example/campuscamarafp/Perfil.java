package com.example.campuscamarafp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.entidades.Profesor;

public class Perfil  extends AppCompatActivity{

    private TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

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
