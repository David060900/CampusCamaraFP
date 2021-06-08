package com.example.campuscamarafp.serializable;

import java.io.Serializable;

public class Modulo implements Serializable {

    private int id_modulo;
    private String nombre;

    public int getId_modulo() {
        return id_modulo;
    }

    public void setId_modulo(int id_modulo) {
        this.id_modulo = id_modulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Modulo(int id_modulo, String nombre) {
        this.id_modulo = id_modulo;
        this.nombre = nombre;
    }

    public Modulo(){

    }

}
