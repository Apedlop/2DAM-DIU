package com.example.agenda_v2.controller;

import com.example.agenda_v2.Main;
import javafx.fxml.FXML;

public class RootLayoutController {

    private Main main;

    // Inyectamos la clase Main
    public void setMain(Main main) {
        this.main = main;
    }

    // Se ejecuta cuando se pulsa el botón "show statictics"
    @FXML
    private void handleShowBirthdayStatistics() {
        main.showBirthdayStatistics();
    }

}
