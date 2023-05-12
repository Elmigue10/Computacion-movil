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
import com.cdp.shopapp.ListActivity;
import com.cdp.shopapp.entity.Product;

import java.util.ArrayList;
import java.util.List;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_item, null, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.viewNombre.setText(productList.get(position).getNombre());
        holder.viewTelefono.setText(productList.get(position).getTelefono());
        holder.viewCorreo.setText(productList.get(position).getCorreo_electornico());
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

        TextView viewNombre, viewTelefono, viewCorreo;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);

            itemView.setOnClickListener(view -> {
                Context context = view.getContext();
                Intent intent = new Intent(context, ListActivity.class);
                intent.putExtra("ID", productList.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });
        }
    }
}
