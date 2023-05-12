package com.cdp.shopapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cdp.shopapp.helper.DbProduct;
import com.cdp.shopapp.entity.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListActivity extends AppCompatActivity {

    EditText txtNombre, txtPrecioUnitario, txtUnidadesStock;
    Button btnGuarda;
    FloatingActionButton fabEditar, fabEliminar;

    Product product;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtNombre = findViewById(R.id.txtNombre);
        txtPrecioUnitario = findViewById(R.id.txtPrecioUnitario);
        txtUnidadesStock = findViewById(R.id.txtUnidadesStock);
        fabEditar = findViewById(R.id.fabEditar);
        fabEliminar = findViewById(R.id.fabEliminar);
        btnGuarda = findViewById(R.id.btnGuarda);
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

        final DbProduct dbProduct = new DbProduct(com.cdp.shopapp.ListActivity.this);
        product = dbProduct.listProduct(id);

        if(product != null){
            txtNombre.setText(product.getNombre());
            txtPrecioUnitario.setText(product.getPrecioUnitario());
            txtUnidadesStock.setText(product.getUnidadesStock());
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtPrecioUnitario.setInputType(InputType.TYPE_NULL);
            txtUnidadesStock.setInputType(InputType.TYPE_NULL);
        }

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =
                        new Intent(ListActivity.this, UpdateActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        fabEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(com.cdp.shopapp.ListActivity.this);
                builder.setMessage("¿Desea eliminar este producto?")
                        .setPositiveButton("SI", (dialogInterface, i) -> {
                            if(dbProduct.deleteProduct(id)){
                                lista();
                            }
                        })
                        .setNegativeButton("NO", (dialogInterface, i) -> {
                        }).show();
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}