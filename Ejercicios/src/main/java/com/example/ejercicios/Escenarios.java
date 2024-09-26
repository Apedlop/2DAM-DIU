package com.example.ejercicios;

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

class ContadorPulsaciones {

    private IntegerProperty numContador = new SimpleIntegerProperty(0);  // Inicializa el contador
    public Label lbContador;  // Define el Label de manera global para acceder desde los métodos

    // Método para obtener la propiedad del contador
    public IntegerProperty contPuls() {
        return numContador;
    }

    // CONTADOR FUNCIONAL
    private void contador(int num) {
        numContador.set((num == 0) ? 0 : numContador.get() + num);
        lbContador.setText(String.valueOf(numContador.get()));
    }

    public void setStage(String nomVentana) {
        try {
            Stage escenarioPrincipal = new Stage();

            // VERTICAL
            VBox raiz = new VBox();
            raiz.setPadding(new Insets(20, 20, 20, 20));
            raiz.setSpacing(10);
            raiz.setAlignment(Pos.CENTER);  // Centra los elementos
            raiz.getStyleClass().add("raiz");

            // HORIZONTAL
            HBox panelBotones = new HBox();
            panelBotones.setPadding(new Insets(20, 20, 20, 20));
            panelBotones.setSpacing(10);
            panelBotones.setAlignment(Pos.CENTER);  // Centra los elementos

            // CREAR BOTONES
            Button btMas, btMenos, btCero;
            btMas = new Button("+");
            btMas.setOnAction(e -> contador(1));
            btMenos = new Button("-");
            btMenos.setOnAction(e -> contador(-1));
            btCero = new Button("0");
            btCero.setOnAction(e -> contador(0));

            // MODIFICAR BOTONES
            btMas.setFont(Font.font("Ani", 20));
            btMas.setPrefWidth(60);
            btMas.setPrefHeight(60);

            btMenos.setFont(Font.font("Ani", 20));
            btMenos.setPrefWidth(60);
            btMenos.setPrefHeight(60);

            btCero.setFont(Font.font("Ani", 20));
            btCero.setPrefWidth(60);
            btCero.setPrefHeight(60);
            btCero.setId("btCero");

            // CREAR CONTADOR
            lbContador = new Label(String.valueOf(numContador.get()));  // Empieza en 0
            lbContador.setFont(Font.font(30));
            lbContador.setId("lbContador");

            // Añadir cambios en la propiedad numContador para actualizar el label
            numContador.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
                    lbContador.setText(String.valueOf(newVal));
                }
            });

            // AÑADIR BOTONES Y CONTADOR A LA INTERFAZ
            panelBotones.getChildren().addAll(btMas, btMenos, btCero);
            raiz.getChildren().addAll(panelBotones, lbContador);

            // CREAR ESCENA
            Scene escena = new Scene(raiz, 420, 150);
            escena.getStylesheets().add(getClass().getResource("/com/example/ejercicios/styles/styleContador.css").toExternalForm());
            escenarioPrincipal.setTitle(nomVentana);
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
