package com.example.campuscamarafp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campuscamarafp.entidades.Usuario;

public class Perfil  extends AppCompatActivity{

    private TextView tv1, tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tv1 = (TextView)findViewById(R.id.textView2);
        tv2 = (TextView)findViewById(R.id.textView3);

        Bundle objEnviado = getIntent().getExtras();
        Usuario user;
        user = (Usuario) objEnviado.getSerializable("datos_usuarios");
        tv1.setText(user.getNombre());
        tv2.setText(user.getApellidos());

    }
}
