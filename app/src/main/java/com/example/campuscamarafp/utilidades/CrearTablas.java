package com.example.campuscamarafp.utilidades;

public class CrearTablas {
    //tabla alumnos
    public static final String CREAR_TABLA_ALUMNOS = "create table if not exists alumnos(" +
            "dni_alumnos text primary key, correo_alumnos text, " +
            "nombre text, apellidos text, password text);";
    //tabla profesores
    public static final String CREAR_TABLA_PROFESORES = "create table if not exists profesores(" +
            "dni_profesores text primary key, correo_profesores text, " +
            "nombre text, apellidos text, password text);";
    //tabla faltas
    public static final String CREAR_TABLA_FALTAS = "create table if not exists faltas(" +
            "id_falta integer primary key autoincrement, num_falta integer, " +
            "dni_alumnos text, dni_profesores text, dia_hora text, " +
            "foreign key (dni_alumnos) references alumnos(dni_alumnos), " +
            "foreign key (dni_profesores) references profesores(dni_profesores));";
    //tabla modulo
    public static final String CREAR_TABLA_MODULO = "create table if not exists modulo(" +
            "id_modulo integer primary key, nombre text);";
    //tabla curso
    public static final String CREAR_TABLA_CURSO = "create table if not exists curso(" +
            "id_curso integer primary key, nombre text, num_curso text);";
    //tabla repaso
    public static final String CREAR_TABLA_REPASO = "create table if not exists repaso(" +
            "id_repaso integer primary key autoincrement, nombre_modulo text, horas_repasar text, " +
            "dia_hora text, lugar text, dni_alumnos text, id_modulo integer, nombre text, apellidos text, " +
            "foreign key (dni_alumnos) references alumnos(dni_alumnos), " +
            "foreign key (id_modulo) references modulo(id_modulo));";
    //tabla estudian
    public static final String CREAR_TABLA_ESTUDIAN = "create table if not exists estudian(" +
            "dni_alumnos text not null, id_modulo integer not null, " +
            "id_curso integer not null, " +
            "foreign key (dni_alumnos) references alumnos(dni_alumnos), " +
            "foreign key (id_modulo) references modulo(id_modulo), " +
            "foreign key (id_curso) references curso(id_curso));";
    //tabla imparten
    public static final String CREAR_TABLA_IMPARTEN = "create table if not exists imparten(" +
            "dni_profesores text not null, id_modulo integer not null, " +
            "id_curso integer not null, " +
            "foreign key (id_curso) references curso(id_curso), " +
            "foreign key (id_modulo) references modulo(id_modulo), " +
            "foreign key (dni_profesores) references profesores(dni_profesores));";
}
