package com.example.campuscamarafp.entidades;

import java.io.Serializable;

public class Alumno implements Serializable {

    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private String curso;
    private String numcurso;

    public Alumno(String nombre, String apellidos, String correo, String password, String curso, String numcurso) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.curso = curso;
        this.numcurso = numcurso;
    }

    public Alumno(){

    }
    //getters y setters
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNumcurso() {
        return numcurso;
    }

    public void setNumcurso(String numcurso) {
        this.numcurso = numcurso;
    }

}
