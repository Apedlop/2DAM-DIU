package com.example.ejemplo_ex1ev1.modelo;

import javafx.beans.property.*;

public class Articulo {

    private int id;
    private final StringProperty nombre;
    private final StringProperty descripcion;
    private final FloatProperty precio;
    private final IntegerProperty stock;

    public Articulo() {
        this(null, null, 0, 0);
    }

    public Articulo(String nombre, String descripcion, float precio, int stock) {
        this.id = 0;
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleFloatProperty(precio);
        this.stock = new SimpleIntegerProperty(stock);
    }

//    public void setId(int id) {
//        this.id.set(id);
//    }
//
//    public int getId() {
//        return id.get();
//    }
//
//    public IntegerProperty idProperty() {
//        return id;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public float getPrecio() {
        return precio.get();
    }

    public void setPrecio(float precio) {
        this.precio.set(precio);
    }

    public FloatProperty precioProperty() {
        return precio;
    }

    public int getStock() {
        return stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public IntegerProperty stockProperty() {
        return stock;
    }
}
