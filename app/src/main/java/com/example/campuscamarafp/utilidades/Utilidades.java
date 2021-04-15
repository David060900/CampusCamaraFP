package com.example.campuscamarafp.utilidades;

public class Utilidades {

    public static final String TABLA_USUARIOS = "usuarios";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_APELLIDOS = "apellidos";
    public static final String CAMPO_CORREO = "correo";
    public static final String CAMPO_PASSWORD = "password";

    public static final String CREAR_TABLA_USUARIO = "create table " + TABLA_USUARIOS +
            "(" + CAMPO_NOMBRE + " text, "
            + CAMPO_APELLIDOS + " text, " +
            CAMPO_CORREO + " text primary key, " +
            CAMPO_PASSWORD + " text)";

}
