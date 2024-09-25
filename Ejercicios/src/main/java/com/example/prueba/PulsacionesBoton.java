package com.example.prueba;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PulsacionesBoton extends Application {

    private Button boton;
    private Label etiqueta;
    private int numPulsaciones = 0;

    private void botonPulsado() {
        if (++numPulsaciones == 1)
            etiqueta.setText("El botón se ha pulsado 1 vez");
        else
            etiqueta.setText("El botón se ha pulsado " + numPulsaciones + " veces");
    }

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            VBox raiz = new VBox(30);
            raiz.setPadding(new Insets(10));
            raiz.setAlignment(Pos.CENTER);

            boton = new Button("Púlsame!!!");
            boton.setFont(Font.font("Arial", 16));
            boton.setOnAction(e -> botonPulsado());

            etiqueta = new Label("El botón aún no se ha pulsado");
            etiqueta.setFont(Font.font("Arial", 24));

            raiz.getChildren().addAll(boton, etiqueta);

            Scene escena = new Scene(raiz, 450, 150);
            escenarioPrincipal.setTitle("Pulsaciones del botón");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}