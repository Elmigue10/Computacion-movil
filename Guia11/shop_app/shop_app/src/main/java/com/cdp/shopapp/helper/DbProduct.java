package com.cdp.shopapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.cdp.shopapp.entity.Product;

import java.util.ArrayList;

public class DbProduct extends DbHelper {

    Context context;

    public DbProduct(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long createProduct(String nombre, String precioUnitario, String unidadesStock,
                              String categoria) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("precioUnitario", precioUnitario);
            values.put("unidadesStock", unidadesStock);
            values.put("categoria", categoria);

            id = db.insert(TABLE_PRODUCT, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Product> showProducts() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Product> productList = new ArrayList<>();
        Product product;
        Cursor productCursor;

        productCursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " ORDER BY nombre ASC",
                null);

        if (productCursor.moveToFirst()) {
            do {
                product = new Product();
                product.setId(productCursor.getInt(0));
                product.setNombre(productCursor.getString(1));
                product.setPrecioUnitario(productCursor.getString(2));
                product.setUnidadesStock(productCursor.getString(3));
                product.setCategoria(productCursor.getString(4));
                productList.add(product);
            } while (productCursor.moveToNext());
        }

        productCursor.close();

        return productList;
    }

    public Product listProduct(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Product product = null;
        Cursor productCursor;

        productCursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE id = " + id + " LIMIT 1", null);

        if (productCursor.moveToFirst()) {
            product = new Product();
            product.setId(productCursor.getInt(0));
            product.setNombre(productCursor.getString(1));
            product.setPrecioUnitario(productCursor.getString(2));
            product.setUnidadesStock(productCursor.getString(3));
            product.setCategoria(productCursor.getString(4));
        }

        productCursor.close();

        return product;
    }

    public boolean updateProduct(int id, String nombre, String precioUnitario,
                                 String unidadesStock, String categoria) {

        boolean correcto;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_PRODUCT + " SET nombre = '" + nombre + "', precioUnitario = '" +
                    precioUnitario + "', unidadesStock = '" + unidadesStock + "', categoria = '" +
                    categoria +  "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean deleteProduct(int id) {

        boolean correcto;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PRODUCT + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
