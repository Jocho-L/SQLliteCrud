package com.senati.sqlitecrud.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    //Declaración de constantes
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tienda.db";
    public static final String TABLE_EQUIPOS = "equipos";

    //Constructor
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Metodo - CREACION
    //Equipos = id, nombre, marca, modelo, precio, garantia
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EQUIPOS + "(" +
                "id         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre     TEXT NOT NULL," +
                "marca      TEXT NOT NULL," +
                "modelo     TEXT NOT NULL," +
                "precio     REAL NOT NULL," +
                "garantia   INTEGER NOT NULL)");
    }

    //Metodo - ACTUALIZACION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Este metodo se ejecuta cuando se detecta un cambio de version en la BD
        db.execSQL("DROP TABLE " + TABLE_EQUIPOS);
        onCreate(db);
    }
}
