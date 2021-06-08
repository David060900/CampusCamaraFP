package com.example.campuscamarafp.serializable;

import java.io.Serializable;
//clase serializable de profesor
public class ProfesorSerial implements Serializable {

    private String dni_profesores;
    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    //constructor
    public ProfesorSerial(String nombre, String apellidos, String correo, String password, String dni_profesores) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.dni_profesores = dni_profesores;
    }

    public ProfesorSerial(){

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

    public String getDni_profesores() {
        return dni_profesores;
    }

    public void setDni_profesores(String dni_profesores) {
        this.dni_profesores = dni_profesores;
    }

}
