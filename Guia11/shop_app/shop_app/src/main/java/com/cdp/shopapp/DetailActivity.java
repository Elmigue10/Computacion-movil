package com.cdp.shopapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cdp.shopapp.helper.DbProduct;
import com.cdp.shopapp.entity.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {

    EditText txtNombre, txtPrecioUnitario, txtUnidadesStock;
    Spinner spinnerCategorias;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

    Product product;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final DbProduct dbProduct = new DbProduct(DetailActivity.this);
        product = dbProduct.listProduct(id);

        if(product != null){
            txtNombre.setText(product.getNombre());
            txtPrecioUnitario.setText(product.getPrecioUnitario());
            txtUnidadesStock.setText(product.getUnidadesStock());
            spinnerCategorias.setSelection(getCategoria(product));
            txtNombre.setEnabled(false);
            txtPrecioUnitario.setEnabled(false);
            txtUnidadesStock.setEnabled(false);
            spinnerCategorias.setEnabled(false);
        }

        fabEditar.setOnClickListener(view -> {
            Intent intent =
                    new Intent(DetailActivity.this, UpdateActivity.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        });

        fabEliminar.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
            builder.setMessage("¿Desea eliminar este producto?")
                    .setPositiveButton("SI", (dialogInterface, i) -> {
                        if(dbProduct.deleteProduct(id)){
                            lista();
                        }
                    })
                    .setNegativeButton("NO", (dialogInterface, i) -> {
                    }).show();
        });
    }

    private int getCategoria(Product product) {
        String[] categorias = getResources().getStringArray(R.array.categorias);

        // Buscar el índice de la categoría en la matriz de cadenas
        int indiceCategoria = -1;
        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].equals(product.getCategoria())) {
                indiceCategoria = i;
                break;
            }
        }
        if (indiceCategoria != -1) {
            return indiceCategoria;
        }
        return 0;
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}