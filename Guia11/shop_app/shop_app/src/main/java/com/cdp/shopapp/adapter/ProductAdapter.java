package com.cdp.shopapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cdp.shopapp.R;
import com.cdp.shopapp.DetailActivity;
import com.cdp.shopapp.entity.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> productList;
    ArrayList<Product> originalList;

    public ProductAdapter(ArrayList<Product> productList) {
        this.productList = productList;
        originalList = new ArrayList<>();
        originalList.addAll(productList);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_item,
                null, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.viewNombre.setText(productList.get(position).getNombre());
        double valor = Double.parseDouble(productList.get(position).getPrecioUnitario());
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(Locale.US);
        String precio = formatoMoneda.format(valor);
        precio = precio.replace(".00", "");
        holder.viewPrecioUnitario.setText(precio);
        holder.viewUnidadesStock.setText("Stock: " + productList.get(position).getUnidadesStock());
        holder.viewCategoria.setText("Categoria: " + productList.get(position).getCategoria());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            productList.clear();
            productList.addAll(originalList);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Product> collecion = productList.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                productList.clear();
                productList.addAll(collecion);
            } else {
                for (Product c : originalList) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        productList.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewPrecioUnitario, viewUnidadesStock, viewCategoria;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewPrecioUnitario = itemView.findViewById(R.id.viewPrecioUnitario);
            viewUnidadesStock = itemView.findViewById(R.id.viewUnidadesStock);
            viewCategoria = itemView.findViewById(R.id.viewCategoria);

            itemView.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("ID", productList.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });
        }
    }
}
