package com.senati.sqlitecrud.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.senati.sqlitecrud.entidades.Equipo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class dbEquipo extends DBHelper{

    //Objeto para gestionar contexto
    Context context;

    //Constructor
    public dbEquipo(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Metodo CRUD
    public long add(String nombre, String marca, String modelo, double precio, int garantia)
    {
        long resultado = 0;
        try{
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase(); //Escritura = agregar/insertar

            //Enviar la información dentro de un objeto
            ContentValues values = new ContentValues();

            values.put("nombre", nombre);
            values.put("marca", marca);
            values.put("modelo", modelo);
            values.put("precio", precio);
            values.put("garantia", garantia);

            resultado = db.insert(TABLE_EQUIPOS, null, values);
        }
        catch(Exception e){
            e.toString(); //Provisional...
        }

        return resultado;
    }

    public ArrayList<Equipo> getAll()
    {
        //Paso 1: Crear un objeto que sirva como plantilla
        Equipo equipo = null;

        //Paso 2: Crear un ArrayList<Equipo>
        ArrayList<Equipo> listaEquipos = new ArrayList<>();

        //Paso 3: Conexión BD
        DBHelper dbHelper = new DBHelper(context);

        //Paso 4: Permisos
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Paso 5: Consulta (SELECT * FROM ...)
        Cursor cursor = db.rawQuery("SELECT * FROM equipos", null);

        //Paso 6: Mover los registros del Tabla >>> Cursor >>> ArrayList<>
        while (cursor.moveToNext()){
            //Paso 7: Creamos un nuevo objeto de tipo "Equipo"
            equipo = new Equipo();

            //Paso 8: Agregamos todos los datos al nuevo objeto
            equipo.setId(cursor.getInt(0)); //0 = primera columna
            equipo.setNombre(cursor.getString(1)); //1 = segunda columna
            equipo.setMarca(cursor.getString(2));
            equipo.setModelo(cursor.getString(3));
            equipo.setPrecio(cursor.getDouble(4));
            equipo.setGarantia(cursor.getInt(5));

            //Paso 9: Enviamos el objeto equipo (alimentado), al arrayList
            listaEquipos.add(equipo);
        }//fin While

        //Paso 10: Retornamos el ArrayList conteniendo los objetos alimentados
        return listaEquipos;

    }//fin getAll

    public int edit(Equipo equipo) {
        int registrosAfectados = 0;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            String[] parametros = { String.valueOf(equipo.getId()) };

            ContentValues values = new ContentValues();

            values.put("nombre", equipo.getNombre());
            values.put("marca", equipo.getMarca());
            values.put("modelo", equipo.getModelo());
            values.put("precio", equipo.getPrecio());
            values.put("garantia", equipo.getGarantia());

            registrosAfectados = db.update(TABLE_EQUIPOS, values, "id=?", parametros);
        }
        catch (Exception e){
            e.toString();
        }

        return registrosAfectados;
    }

    public void delete(int idEquipo) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] parametros = { String.valueOf(idEquipo) };
        db.delete(TABLE_EQUIPOS, "id=?", parametros);
        db.close();
    }

    public Equipo find(int idEquipo)
    {
        //Contendrá los datos de salida (return)
        Equipo equipo = new Equipo();
        //Acceso BD
        DBHelper dbHelper = new DBHelper(context);
        //Permisos
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Valor buscado -> array
        String[] parametros = { String.valueOf(idEquipo) };

        //¿Qué campos espera obtener?
        String[] campos = { "nombre", "marca", "modelo",  "precio", "garantia"};

        try{
            //Ejecutamos la consulta...
            Cursor cursor = db.query(TABLE_EQUIPOS, campos, "id=?", parametros, null, null, null);
            cursor.moveToFirst();

            //Enviamos todos los datos al objeto Equipo (paso 1)
            equipo.setNombre(cursor.getString(0));
            equipo.setMarca(cursor.getString(1));
            equipo.setModelo(cursor.getString(2));
            equipo.setPrecio(cursor.getDouble(3));
            equipo.setGarantia(cursor.getInt(4));

            cursor.close();
        }
        catch (Exception e){
            e.toString();
        }
        return equipo;
    }

}
