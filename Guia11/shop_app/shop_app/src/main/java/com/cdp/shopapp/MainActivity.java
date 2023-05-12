package com.cdp.shopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.cdp.shopapp.adapter.ProductAdapter;
import com.cdp.shopapp.helper.DbProduct;
import com.cdp.shopapp.entity.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    Spinner spinnerCategorias;
    RecyclerView recyclerViewProduct;
    ArrayList<Product> productList;
    FloatingActionButton fabNuevo;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBuscar = findViewById(R.id.txtBuscar);

        spinnerCategorias = findViewById(R.id.spinner_categoria);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.filtro_categorias,
                        android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCategorias.setAdapter(spinnerAdapter);

        recyclerViewProduct = findViewById(R.id.productList);
        fabNuevo = findViewById(R.id.favNuevo);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        DbProduct dbProduct = new DbProduct(MainActivity.this);

        productList = new ArrayList<>();

        adapter = new ProductAdapter(dbProduct
                .showProducts(spinnerCategorias.getSelectedItem().toString()));
        recyclerViewProduct.setAdapter(adapter);

        fabNuevo.setOnClickListener(view -> nuevoRegistro());

        txtBuscar.setOnQueryTextListener(this);
        spinnerCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Este método se ejecuta cuando cambia la selección del Spinner
                String itemSeleccionado = parent.getItemAtPosition(position).toString();
                adapter = new ProductAdapter(dbProduct
                        .showProducts(itemSeleccionado));
                recyclerViewProduct.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }
}