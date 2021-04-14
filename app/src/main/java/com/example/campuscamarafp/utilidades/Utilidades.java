package com.example.campuscamarafp.utilidades;

public class Utilidades {

    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO="usuarios";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_APELLIDOS="apellidos";
    public static final String CAMPO_CORREO="correo";
    public static final String CAMPO_PASSWORD="password";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE" + TABLA_USUARIO +
            "(" + CAMPO_NOMBRE + "TEXT,"
            + CAMPO_APELLIDOS + "TEXT," + CAMPO_CORREO + "TEXT, " + CAMPO_PASSWORD + " TEXT)";

}
