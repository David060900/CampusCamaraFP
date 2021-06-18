package com.example.campuscamarafp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

import com.example.campuscamarafp.utilidades.CrearTablas;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //abrimos la base de datos
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //permitimos el uso de foreign keys
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }
    @Override//creamos los scripts
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CrearTablas.CREAR_TABLA_ALUMNOS);
        db.execSQL(CrearTablas.CREAR_TABLA_PROFESORES);
        db.execSQL(CrearTablas.CREAR_TABLA_FALTAS);
        db.execSQL(CrearTablas.CREAR_TABLA_MODULO);
        db.execSQL(CrearTablas.CREAR_TABLA_CURSO);
        db.execSQL(CrearTablas.CREAR_TABLA_REPASO);
        db.execSQL(CrearTablas.CREAR_TABLA_ESTUDIAN);
        db.execSQL(CrearTablas.CREAR_TABLA_IMPARTEN);
        cargarDatos(db);
    }

    public void cargarDatos(SQLiteDatabase db){
        //inserts a modulo
        db.execSQL("insert into modulo values (1,'FOL');");
        db.execSQL("insert into modulo values (2,'GACI');");
        db.execSQL("insert into modulo values (3,'GEYFE');");
        db.execSQL("insert into modulo values (4,'INGLES T');");
        db.execSQL("insert into modulo values (5,'INGLES');");
        db.execSQL("insert into modulo values (6,'LOGAL');");
        db.execSQL("insert into modulo values (7,'TIM');");
        db.execSQL("insert into modulo values (8,'BASES');");
        db.execSQL("insert into modulo values (9,'ED');");
        db.execSQL("insert into modulo values (10,'LGMS');");
        db.execSQL("insert into modulo values (11,'PROGRAMACION');");
        db.execSQL("insert into modulo values (12,'SISTEMAS');");
        db.execSQL("insert into modulo values (13,'INVCOM');");
        db.execSQL("insert into modulo values (14,'MKDIG');");
        db.execSQL("insert into modulo values (15,'PLMK');");
        //inserts a curso
        db.execSQL("insert into curso values (1,'DAM','Primero');");
        db.execSQL("insert into curso values (2,'DAM', 'Segundo');");
        db.execSQL("insert into curso values (3,'Marketing y Publicidad', 'Primero');");
        db.execSQL("insert into curso values (4,'Marketing y Publicidad', 'Segundo');");
        db.execSQL("insert into curso values (5,'Comercio Internacional', 'Primero');");
        db.execSQL("insert into curso values (6,'Comercio Internacional', 'Segundo');");
        db.execSQL("insert into curso values (7,'Transporte y Logística', 'Primero');");
        db.execSQL("insert into curso values (8,'Transporte y Logística', 'Segundo');");

        System.out.println("HA CARGADO");
    }
    @Override//verifica si existe una version antigua de la base de datos
    //refrescamos los scripts
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alumnos");
        db.execSQL("DROP TABLE IF EXISTS profesores");
        db.execSQL("DROP TABLE IF EXISTS faltas");
        db.execSQL("DROP TABLE IF EXISTS modulo");
        db.execSQL("DROP TABLE IF EXISTS curso");
        db.execSQL("DROP TABLE IF EXISTS repaso");
        db.execSQL("DROP TABLE IF EXISTS estudian");
        db.execSQL("DROP TABLE IF EXISTS imparten");
        onCreate(db);
    }
}
