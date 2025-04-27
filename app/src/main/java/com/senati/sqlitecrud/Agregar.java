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

public class Agregar extends AppCompatActivity {

    EditText edtNombreEquipo,edtMarcaEquipo, edtModeloEquipo, edtPrecioEquipo, edtGarantiaEquipo;
    Button btnRegistrarEquipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();

        btnRegistrarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (formIsReady())
                {
                    ask("¿Desea registrar este equipo?");
                }
                else
                {
                    showToast("Te falta completar el form");
                }
            }
        });
    }//onCreate


    /**
     * Muestra un cuadro de dialogo para confirmar el registro del equipo
     * @param question
     */
    private void ask(String question)
    {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("CrudSQLite");
        dialogo.setMessage(question);
        dialogo.setCancelable(false);

        //Botones
        dialogo.setNegativeButton("No", null);
        dialogo.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Enviamos los datos a SQLite
                dbEquipo equipo = new dbEquipo(getApplicationContext());

                String nombre = edtNombreEquipo.getText().toString();
                String marca = edtMarcaEquipo.getText().toString();
                String modelo = edtModeloEquipo.getText().toString();
                Double precio = Double.parseDouble(edtPrecioEquipo.getText().toString());
                int garantia = Integer.parseInt(edtGarantiaEquipo.getText().toString());

                long idgenerado = equipo.add(nombre, marca, modelo, precio, garantia);

                if (idgenerado > 0)
                {
                    resetUI();
                    edtNombreEquipo.requestFocus();
                    showToast("Guardado correctamente");
                }
                else
                {
                    showToast("No se pudo registrar el equipo");
                }
            }
        });

        //Mostrar cuadro de dialogo en pantalla
        dialogo.create().show();
    }//ask

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

    private boolean inputIsEmpty(EditText input)
    {
        if (TextUtils.isEmpty(input.getText().toString().trim()))
        {
            input.setError("Complete este campo");
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Este metodo valida que el formulario tenga todos los datos requeridos
     * @return
     */
    private boolean formIsReady()
    {
        boolean camposListos = true;

        if (inputIsEmpty(edtNombreEquipo)) { camposListos = false;  }
        if (inputIsEmpty(edtMarcaEquipo)) { camposListos = false;  }
        if (inputIsEmpty(edtModeloEquipo)) { camposListos = false;  }
        if (inputIsEmpty(edtPrecioEquipo)) { camposListos = false;}
        if (inputIsEmpty(edtGarantiaEquipo)) { camposListos = false; }

        return camposListos;
    }

    private void loadUI()
    {
        edtNombreEquipo = findViewById(R.id.edtNombreEquipo);
        edtMarcaEquipo = findViewById(R.id.edtMarcaEquipo);
        edtModeloEquipo = findViewById(R.id.edtModeloEquipo);
        edtPrecioEquipo = findViewById(R.id.edtPrecioEquipo);
        edtGarantiaEquipo = findViewById(R.id.edtGarantiaEquipo);

        btnRegistrarEquipo = findViewById(R.id.btnRegistrarEquipo);
    }
}