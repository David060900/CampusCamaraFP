package com.example.campuscamarafp.entidades;

import java.io.Serializable;

public class Curso implements Serializable {

    private int id_curso;
    private String nombre;
    private String num_curso;

    public Curso(int id_curso, String nombre, String num_curso) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.num_curso = num_curso;
    }

    public Curso(){

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
