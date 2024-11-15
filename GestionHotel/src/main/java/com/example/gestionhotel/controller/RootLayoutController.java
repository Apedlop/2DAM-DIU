package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import javafx.fxml.FXML;

public class RootLayoutController {

    private Main main;

    // Inyectamos la clase Main
    public void setMain(Main main) {
        this.main = main;
    }

    // Se ejecuta cuando se pulsa el botón "Ver estadísticas"
    @FXML
    private void botonVerEstadisticas() {
        main.verEstadisticas();
    }

}