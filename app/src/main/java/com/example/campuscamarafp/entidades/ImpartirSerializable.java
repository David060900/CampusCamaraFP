package com.example.campuscamarafp.entidades;

import java.io.Serializable;
//clase serializable de impartir
public class ImpartirSerializable implements Serializable {

    private String asignatura;
    private String tiempo;
    private String lugar;
    private String dia;
    private String correo_alumnos;
    private int id_impartir;

    //constructor
    public ImpartirSerializable(String asignatura, String tiempo, String lugar, String dia, String correo_alumnos, int id_impartir) {
        this.asignatura = asignatura;
        this.tiempo = tiempo;
        this.lugar = lugar;
        this.dia = dia;
        this.correo_alumnos = correo_alumnos;
        this.id_impartir = id_impartir;
    }

    public ImpartirSerializable(){

    }

    public ImpartirSerializable(String asignatura, String correo_alumnos, String dia, String tiempo) {
        this.asignatura = asignatura;
        this.tiempo = tiempo;
        this.dia = dia;
        this.correo_alumnos = correo_alumnos;
    }

    //getters y setters
    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getCorreo_alumnos() {
        return correo_alumnos;
    }

    public void setCorreo_alumnos(String correo_alumnos) {
        this.correo_alumnos = correo_alumnos;
    }

    public int getId_impartir() {
        return id_impartir;
    }

    public void setId_impartir(int id_impartir) {
        this.id_impartir = id_impartir;
    }
}
