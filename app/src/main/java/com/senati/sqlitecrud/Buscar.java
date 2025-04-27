package com.senati.sqlitecrud;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senati.sqlitecrud.db.dbEquipo;
import com.senati.sqlitecrud.entidades.Equipo;

public class Buscar extends AppCompatActivity {

    EditText edtIDEquipoBuscado;
    Button btnBuscarEquipo;
    EditText edtNombreEquipo,edtMarcaEquipo, edtModeloEquipo, edtPrecioEquipo, edtGarantiaEquipo;
    Button btnActualizarEquipo, btnEliminarEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();

        btnBuscarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEquipo();
            }
        });

        btnActualizarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEquipo();
            }
        });

        btnEliminarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEquipo();
            }
        });
    }

    private void resetUI()
    {
        edtNombreEquipo.setText(null);
        edtMarcaEquipo.setText(null);
        edtModeloEquipo.setText(null);
        edtPrecioEquipo.setText(null);
        edtGarantiaEquipo.setText(null);
    }

    private void showToast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void eliminarEquipo()
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("CrudSQLite");
        dialogo.setMessage("¿Confirma eliminación de equipo?");
        dialogo.setCancelable(false);

        //Botones
        dialogo.setNegativeButton("No", null);
        dialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Enviamos los datos a SQLite
                dbEquipo db = new dbEquipo(getApplicationContext());
                db.delete(Integer.parseInt(edtIDEquipoBuscado.getText().toString().trim()));

                resetUI();
            }
        });

        //Mostrar cuadro de dialogo en pantalla
        dialogo.create().show();
    }

    private void actualizarEquipo()
    {
        int total = 0;
        dbEquipo db = new dbEquipo(getApplicationContext());
        Equipo equipo = new Equipo();

        equipo.setId(Integer.parseInt(edtIDEquipoBuscado.getText().toString()));
        equipo.setNombre(edtNombreEquipo.getText().toString());
        equipo.setMarca(edtMarcaEquipo.getText().toString());
        equipo.setModelo(edtModeloEquipo.getText().toString());
        equipo.setPrecio(Double.parseDouble(edtPrecioEquipo.getText().toString()));
        equipo.setGarantia(Integer.parseInt(edtGarantiaEquipo.getText().toString()));

        total = db.edit(equipo);

        if (total > 0){
            resetUI();
            showToast("Actualizado correctamente");
        }
    }

    private void buscarEquipo()
    {
        //Validación en caso no escriba valor buscado
        if (TextUtils.isEmpty(edtIDEquipoBuscado.getText().toString().trim())){
            showToast("Ingrese un ID");
            edtIDEquipoBuscado.requestFocus();
            return; //Fin proceso
        }

        //Instancia de la clase "dbEquipo" (CRUD)
        dbEquipo db = new dbEquipo(getApplicationContext());

        //Objeto del tipo Equipo para recibir los datos
        Equipo equipo = new Equipo();

        //Buscamos en la TABLA
        equipo = db.find(Integer.parseInt(edtIDEquipoBuscado.getText().toString()));

        //¿Qué pasa si el ID no existe?
        if (equipo.getNombre() == null){
            resetUI();
            showToast("No encontrado");
        }else{
            //Enviamos cada parte de la info a una caja de texto
            edtNombreEquipo.setText(equipo.getNombre());
            edtMarcaEquipo.setText(equipo.getMarca());
            edtModeloEquipo.setText(equipo.getModelo());
            edtPrecioEquipo.setText(String.valueOf(equipo.getPrecio()));
            edtGarantiaEquipo.setText(String.valueOf(equipo.getGarantia()));
        }
    }

    private void loadUI()
    {

        edtIDEquipoBuscado = findViewById(R.id.edtIdEquipoBuscado); //Buscador
        btnBuscarEquipo = findViewById(R.id.btnBuscarEquipo); //Botón buscar

        edtNombreEquipo = findViewById(R.id.edtNombreEquipoEdit);
        edtMarcaEquipo = findViewById(R.id.edtMarcaEquipoEdit);
        edtModeloEquipo = findViewById(R.id.edtModeloEquipoEdit);
        edtPrecioEquipo = findViewById(R.id.edtPrecioEquipoEdit);
        edtGarantiaEquipo = findViewById(R.id.edtGarantiaEquipoEdit);

        btnActualizarEquipo = findViewById(R.id.btnActualizarEquipo);
        btnEliminarEquipo = findViewById(R.id.btnEliminarEquipo);
    }
}