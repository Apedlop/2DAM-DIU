package com.example.examen_ex1ev1.controller;

import Modelo.ExcepcionMoneda;
import com.example.examen_ex1ev1.modelo.ConversorModelo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OpcionController {

    @FXML
    private Label totalMonedas;

    ConversorModelo conversorModelo;

    private void setTotalMonedas() throws ExcepcionMoneda {
        totalMonedas.setText(String.valueOf(conversorModelo.obtenerListaMonedas().size()));
    }

}
