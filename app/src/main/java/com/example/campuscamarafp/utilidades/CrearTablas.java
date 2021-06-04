package com.example.campuscamarafp.utilidades;

public class CrearTablas {

    public static final String CREAR_TABLA_ALUMNOS = "create table if not exists alumnos(" +
            "dni_alumnos text primary key, correo_alumnos text, " +
            "nombre text, apellidos text, password text, " +
            "curso text, num_curso text, )";

}
