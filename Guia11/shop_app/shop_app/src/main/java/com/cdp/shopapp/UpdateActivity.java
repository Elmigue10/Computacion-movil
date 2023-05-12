package com.cdp.shopapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cdp.shopapp.helper.DbProduct;
import com.cdp.shopapp.entity.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    EditText txtNombre, txtPrecioUnitario, txtUnidadesStock;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;
    boolean correcto = false;
    Product product;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtNombre = findViewById(R.id.txtNombre);
        txtPrecioUnitario = findViewById(R.id.txtPrecioUnitario);
        txtUnidadesStock = findViewById(R.id.txtUnidadesStock);
        btnGuarda = findViewById(R.id.btnGuarda);
        fabEditar = findViewById(R.id.fabEditar);
        fabEditar.setVisibility(View.INVISIBLE);
        fabEliminar = findViewById(R.id.fabEliminar);
        fabEliminar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbProduct dbProduct = new DbProduct(UpdateActivity.this);
        product = dbProduct.listProduct(id);

        if (product != null) {
            txtNombre.setText(product.getNombre());
            txtPrecioUnitario.setText(product.getPrecioUnitario());
            txtUnidadesStock.setText(product.getUnidadesStock());
        }

        btnGuarda.setOnClickListener(view -> {
            if (!txtNombre.getText().toString().equals("") && !txtPrecioUnitario.getText().toString().equals("")) {
                correcto = dbProduct.updateProduct(id, txtNombre.getText().toString(), txtPrecioUnitario.getText().toString(), txtUnidadesStock.getText().toString());

                if(correcto){
                    Toast.makeText(UpdateActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                    verRegistro();
                } else {
                    Toast.makeText(UpdateActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(UpdateActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}