package com.example.agendav2.controller;

import Modelo.ExcepcionMoneda;
import com.example.agendav2.modelo.ConversorModelo;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ConversorController {

    @FXML
    private TextField cantidad;
    @FXML
    private TextField cambio;

    private ConversorModelo conversorModelo;

    private float mult;

    public void setConversorModelo(ConversorModelo conversorModelo){
        this.conversorModelo = conversorModelo;
    }

    public float getMultiplicador() {
        try {
            mult = conversorModelo.recuperarMoneda();
        } catch (ExcepcionMoneda e) {
            throw new RuntimeException(e);
        }
        return mult;
    }

    public void cambioM() {
        float cantidadIngresada = Float.parseFloat(cantidad.getText());
        float cambioDolar;
        try {
            cambioDolar = conversorModelo.convertir(mult, cantidadIngresada);
            cambio.setText(String.format("%.2f", cambioDolar));
        } catch (NumberFormatException e) {
            // Manejar el caso de que el usuario no haya ingresado un número válido
            cambio.setText("Entrada inválida");
        }

    }

}
