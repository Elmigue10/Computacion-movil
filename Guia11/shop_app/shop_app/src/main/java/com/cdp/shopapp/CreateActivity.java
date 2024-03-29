package com.cdp.shopapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cdp.shopapp.helper.DbProduct;

public class CreateActivity extends AppCompatActivity {

    EditText txtNombre, txtPrecioUnitario, txtUnidadesStock;
    Spinner spinnerCategorias;
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        txtNombre = findViewById(R.id.txtNombre);
        txtPrecioUnitario = findViewById(R.id.txtPrecioUnitario);
        txtUnidadesStock = findViewById(R.id.txtUnidadesStock);

        spinnerCategorias = findViewById(R.id.spinner_categoria);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.categorias,
                        android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCategorias.setAdapter(spinnerAdapter);

        btnGuarda = findViewById(R.id.btnGuarda);

        btnGuarda.setOnClickListener(view -> {

            if(!txtNombre.getText().toString().equals("") && !txtPrecioUnitario.getText().toString().equals("")) {

                DbProduct dbProduct = new DbProduct(CreateActivity.this);
                long id = dbProduct.createProduct(txtNombre.getText().toString(),
                        txtPrecioUnitario.getText().toString(),
                        txtUnidadesStock.getText().toString(),
                        spinnerCategorias.getSelectedItem().toString());

                if (id > 0) {
                    Toast.makeText(CreateActivity.this, "REGISTRO GUARDADO",
                            Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(CreateActivity.this, "ERROR AL GUARDAR REGISTRO",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(CreateActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtPrecioUnitario.setText("");
        txtUnidadesStock.setText("");
    }
}