package com.example.campuscamarafp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campuscamarafp.serializable.FaltasSerial;

import java.util.ArrayList;

public class VerFaltas extends AppCompatActivity {

    ArrayList<FaltasSerial> listaFaltas;
    RecyclerView recyclerFaltas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfaltas);

        listaFaltas = new ArrayList<>();
        recyclerFaltas = (RecyclerView) findViewById(R.id.recyclerFaltas);
        recyclerFaltas.setLayoutManager(new LinearLayoutManager(this));

        llenarLista();

        AdaptadorFaltas adapter = new AdaptadorFaltas(listaFaltas);
        recyclerFaltas.setAdapter(adapter);
    }

    private void llenarLista() {
        listaFaltas.add(new FaltasSerial("", "hola", "si"));
        listaFaltas.add(new FaltasSerial("", "hola", "si"));
        listaFaltas.add(new FaltasSerial("", "hola", "si"));
        listaFaltas.add(new FaltasSerial("", "hola", "si"));
        listaFaltas.add(new FaltasSerial("", "hola", "si"));
    }
}
