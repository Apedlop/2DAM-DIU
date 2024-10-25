package com.example.agenda_v2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AgendaController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}