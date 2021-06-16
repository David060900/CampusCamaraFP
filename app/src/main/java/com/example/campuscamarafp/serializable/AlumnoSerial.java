package com.example.campuscamarafp.serializable;

import java.io.Serializable;
//clase serializable de alumno
public class AlumnoSerial implements Serializable {

    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private String dni_alumno;
    //constructor
    public AlumnoSerial(String nombre, String apellidos, String correo, String password, String dni_alumno) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.dni_alumno = dni_alumno;
    }

    public AlumnoSerial(){

    }

    public AlumnoSerial(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
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

    public String getDni_alumno() {
        return dni_alumno;
    }

    public void setDni_alumno(String dni_alumno) {
        this.dni_alumno = dni_alumno;
    }

    public boolean isSelected() {
        return false;
    }
}
