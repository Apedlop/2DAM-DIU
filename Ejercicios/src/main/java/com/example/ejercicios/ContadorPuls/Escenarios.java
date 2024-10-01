package com.example.ejercicios.ContadorPuls;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Escenarios extends Application {

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            // Crea dos instancias de la clase ContadorPulsaciones
            ContadorPulsaciones escena1 = new ContadorPulsaciones();
            ContadorPulsaciones escena2 = new ContadorPulsaciones();

            // Obtener el número del contador
            IntegerProperty numPuls1 = escena1.contPuls();
            IntegerProperty numPuls2 = escena2.contPuls();

            // Enlazamos bidireccionalmente los contadores
            numPuls1.bindBidirectional(numPuls2);

            // Mostramos los escenarios
            escena1.setStage("Contador 1");
            escena2.setStage("Contador 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}