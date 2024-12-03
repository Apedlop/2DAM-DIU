package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import javafx.fxml.FXML;

public class RootLayoutController {

    private Main main;

    // Inyectamos la clase Main
    public void setMain(Main main) {
        this.main = main;
    }

    // Método que se ejecuta cuando pulsamos en "Estadísticas"
    @FXML
    private void botonVerEstadisticas() {
        main.verEstadisticas();
    }

    // Método que se ejecuta cuando pulsamos en "Habitaciones"
    @FXML
    private void botonTiposHbitacion() {
        main.verTiposHabitaciones();
    }

    // Método que se ejecuta cuando pulsamos en "Documentación"
    @FXML
    private void botonDocumentacion() {main.verDocumentacion();}

}