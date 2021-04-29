package com.example.campuscamarafp.utilidades;

public class Utilidades {

    //creamos campos estáticos para todos los alumnos
    public static final String TABLA_ALUMNOS = "alumnos";
    public static final String CAMPO_NOMBRE_ALUMNOS = "nombre";
    public static final String CAMPO_APELLIDOS_ALUMNOS = "apellidos";
    public static final String CAMPO_CORREO_ALUMNOS = "correo";
    public static final String CAMPO_PASSWORD_ALUMNOS = "password";
    public static final String CAMPO_CURSO_ALUMNOS = "curso";
    public static final String CAMPO_NUMCURSO_ALUMNOS = "numcurso";

    //creamos campos estáticos para todos los profesores
    public static final String TABLA_PROFESORES = "profesores";
    public static final String CAMPO_NOMBRE_PROFESORES = "nombre";
    public static final String CAMPO_APELLIDOS_PROFESORES = "apellidos";
    public static final String CAMPO_CORREO_PROFESORES = "correo";
    public static final String CAMPO_PASSWORD_PROFESORES = "password";
    //public static final String CAMPO_PASSWORD_PROFESORES = "password";

    //sentencia que crea la tabla alumnos
    public static final String CREAR_TABLA_ALUMNO = "create table " + TABLA_ALUMNOS +
            "(" + CAMPO_NOMBRE_ALUMNOS + " text, "
            + CAMPO_APELLIDOS_ALUMNOS + " text, " +
            CAMPO_CORREO_ALUMNOS + " text primary key, " +
            CAMPO_PASSWORD_ALUMNOS + " text, " +
            CAMPO_CURSO_ALUMNOS + " text, " +
            CAMPO_NUMCURSO_ALUMNOS + " text);";

    //sentencia que crea la tabla profesores
    public static final String CREAR_TABLA_PROFESOR = "create table " + TABLA_PROFESORES +
            "(" + CAMPO_NOMBRE_PROFESORES + " text, " +
            CAMPO_APELLIDOS_PROFESORES + " text, " +
            CAMPO_CORREO_PROFESORES + " text primary key, " +
            CAMPO_PASSWORD_PROFESORES + " text);";

}
