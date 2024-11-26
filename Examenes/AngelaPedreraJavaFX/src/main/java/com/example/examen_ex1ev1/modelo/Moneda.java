package com.example.examen_ex1ev1.modelo;

import javafx.beans.property.*;

public class Moneda {

    Integer codigo;

    @Override
    public String toString() {
        return "Moneda{" +
                "codigo=" + codigo +
                ", nombre=" + nombre +
                ", multiplicador=" + multiplicador +
                '}';
    }

    private final StringProperty nombre;
    private final FloatProperty multiplicador;

    public Moneda() {
        this(null, 0);
    }

    public Moneda(String nombre, float multiplicador) {
        this.nombre = new SimpleStringProperty(nombre);
        this.multiplicador = new SimpleFloatProperty(multiplicador);
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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

    public void setMultiplicador(float multiplicador) {
        this.multiplicador.set(multiplicador);
    }

    public float getMultiplicador() {
        return multiplicador.get();
    }

    public FloatProperty multiplicadorProperty() {
        return multiplicador;
    }
}
