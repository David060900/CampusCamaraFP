package com.example.campuscamarafp.serializable;

import java.io.Serializable;

public class FaltasSerial implements Serializable {

    private int id_faltas;
    private int num_falta;
    private String dni_alumnos;
    private String dni_profesores;
    private String dia_hora;

    public FaltasSerial(int id_faltas, int num_falta, String dni_alumnos, String dni_profesores, String dia_hora) {
        this.id_faltas = id_faltas;
        this.num_falta = num_falta;
        this.dni_alumnos = dni_alumnos;
        this.dni_profesores = dni_profesores;
        this.dia_hora = dia_hora;
    }

    public FaltasSerial(int num_falta, String dni_profesores, String dia_hora) {
        this.id_faltas = id_faltas;
        this.num_falta = num_falta;
        this.dni_alumnos = dni_alumnos;
        this.dni_profesores = dni_profesores;
        this.dia_hora = dia_hora;
    }

    public FaltasSerial(){

    }

    public int getId_faltas() {
        return id_faltas;
    }

    public void setId_faltas(int id_faltas) {
        this.id_faltas = id_faltas;
    }

    public int getNum_falta() {
        return num_falta;
    }

    public void setNum_falta(int num_falta) {
        this.num_falta = num_falta;
    }

    public String getDni_alumnos() {
        return dni_alumnos;
    }

    public void setDni_alumnos(String dni_alumnos) {
        this.dni_alumnos = dni_alumnos;
    }

    public String getDni_profesores() {
        return dni_profesores;
    }

    public void setDni_profesores(String dni_profesores) {
        this.dni_profesores = dni_profesores;
    }

    public String getDia_hora() {
        return dia_hora;
    }

    public void setDia_hora(String dia_hora) {
        this.dia_hora = dia_hora;
    }
}
