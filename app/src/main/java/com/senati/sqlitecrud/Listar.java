package com.senati.sqlitecrud;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senati.sqlitecrud.db.dbEquipo;
import com.senati.sqlitecrud.entidades.Equipo;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {

    ListView lstEquipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();
        renderData(); //Mostrar la información en el listView

    }//onCreate

    private void renderData()
    {
        //Paso 1: Instancia clase que contienen métodos CRUD
        dbEquipo db = new dbEquipo(getApplicationContext());

        //Paso 2: Crear un ArrayList<String> valores que enviaremos al listView
        ArrayList<String> listaInformacion = new ArrayList<>();

        //Paso 3: Almacenamos los datos obtenidos por el metodo
        ArrayList<Equipo> data = db.getAll();

        //Paso 4: Traslador ArrayList<Equipo> >>> ArrayList<String>
        for (int i = 0; i < data.size(); i++){
            listaInformacion.add(data.get(i).getNombre() + " - " + data.get(i).getMarca() + " (" + data.get(i).getPrecio() + ")");
        }

        //Paso 5: ArrayList<String> ADAPTADOR(intérprete de datos)
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);

        //Paso 6: Asignar los datos al ListView utilizando el adaptador
        lstEquipos.setAdapter(adaptador);
    }

    private void loadUI()
    {
        lstEquipos = findViewById(R.id.lstEquipos);
    }

}