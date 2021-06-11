package com.example.campuscamarafp.serializable;

import java.io.Serializable;
//clase serializable de impartir
public class RepasoSerial implements Serializable {

    private String modulo;
    private String tiempo;
    private String lugar;
    private String dia;
    private String dni_alumnos;
    private String nombre;
    private String apellidos;
    private int id_repaso;

    //constructor
    public RepasoSerial(String modulo, String tiempo, String lugar, String dia, String dni_alumnos, int id_repaso) {
        this.modulo = modulo;
        this.tiempo = tiempo;
        this.lugar = lugar;
        this.dia = dia;
        this.dni_alumnos = dni_alumnos;
        this.id_repaso = id_repaso;
    }

    public RepasoSerial(){

    }

    public RepasoSerial(String modulo, String nombre, String apellidos, String dia, String tiempo) {
        this.modulo = modulo;
        this.tiempo = tiempo;
        this.dia = dia;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    //getters y setters
    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
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

    public String getDni_alumnos() {
        return dni_alumnos;
    }

    public void setDni_alumnos(String dni_alumnos) {
        this.dni_alumnos = dni_alumnos;
    }

    public int getId_repaso() {
        return id_repaso;
    }

    public void setId_repaso(int id_repaso) {
        this.id_repaso = id_repaso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
