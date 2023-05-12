package com.cdp.shopapp.entity;

public class Product {

    private int id;
    private String nombre;
    private String precioUnitario;
    private String unidadesStock;
    private String categoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(String precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getUnidadesStock() {
        return unidadesStock;
    }

    public void setUnidadesStock(String unidadesStock) {
        this.unidadesStock = unidadesStock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
