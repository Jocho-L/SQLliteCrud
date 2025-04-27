package com.senati.sqlitecrud;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.senati.sqlitecrud.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    Button btnTestBD, btnActAgregar, btnActListar, btnActBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();

        btnTestBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDatabase();
            }
        });

        btnActAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Agregar.class);
            }
        });

        btnActListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Listar.class);
            }
        });

        btnActBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(Buscar.class);
            }
        });
    }

    private void buildDatabase()
    {
        //Instanciamos la clase que habíamos creado
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        //Solicitamos permisos
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //Verificando
        if (db != null)
        {
            //Éxito
            showToast("BD creada con éxito");
        }
        else
        {
            //Algo falló :(
            showToast("No se pudo crear la BD");
        }
    }

    private void showToast(String message)
    {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Este metodo apertura un Activity
     */
    private void openActivity(Class activityName)
    {
        Intent intent = new Intent(getApplicationContext(), activityName);
        startActivity(intent);
    }

    private void loadUI()
    {
        btnTestBD = findViewById(R.id.btnTestBD);
        btnActAgregar = findViewById(R.id.btnActAgregar);
        btnActListar = findViewById(R.id.btnActListar);
        btnActBuscar = findViewById(R.id.btnActBuscar);
    }
} //Fin clase MainActivity