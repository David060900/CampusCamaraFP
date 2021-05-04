package com.example.campuscamarafp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.campuscamarafp.utilidades.Utilidades;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override//creamos los scripts
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_ALUMNO);
        db.execSQL(Utilidades.CREAR_TABLA_PROFESOR);
        //db.execSQL(Utilidades.CREAR_TABLA_ASIGNATURAS);
    }

    @Override//verifica si existe una version antigua de la base de datos
    //refrescamos los scripts
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alumnos");
        db.execSQL("DROP TABLE IF EXISTS profesores");
        //db.execSQL("DROP TABLE IF EXISTS asignaturas");
        onCreate(db);
    }
}
