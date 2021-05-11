package com.example.campuscamarafp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Alumno;
import com.example.campuscamarafp.entidades.Profesor;

public class Perfil  extends AppCompatActivity{

    private TextView tv1, tv2;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tv1 = (TextView)findViewById(R.id.textView2);
        tv2 = (TextView)findViewById(R.id.textView3);
        iv = (ImageView)findViewById(R.id.imagen_perfil);

        Bundle objEnviado = getIntent().getExtras();
        Profesor profe;
        Alumno alum;
        //profe = (Profesor) objEnviado.getSerializable("datos_usuarios");
        alum = (Alumno) objEnviado.getSerializable("datos_usuarios");
        //tv1.setText(profe.getCorreo());
        tv2.setText(alum.getCorreo());
    }

    public void cargarImagen(View view){
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
    }

}
