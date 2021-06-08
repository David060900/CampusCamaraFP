package com.example.campuscamarafp.serializable;

import java.io.Serializable;

public class CursoSerial implements Serializable {

    private int id_curso;
    private String nombre;
    private String num_curso;

    public CursoSerial(int id_curso, String nombre, String num_curso) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.num_curso = num_curso;
    }

    public CursoSerial(){

    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNum_curso() {
        return num_curso;
    }

    public void setNum_curso(String num_curso) {
        this.num_curso = num_curso;
    }
}
