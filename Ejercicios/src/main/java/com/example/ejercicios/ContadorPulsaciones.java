package com.example.ejercicios;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

import javax.swing.event.ChangeListener;

public class ContadorPulsaciones extends Application {

    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            ContadorPulsaciones2 escena1 = new ContadorPulsaciones2();
            ContadorPulsaciones2 escena2 = new ContadorPulsaciones2();

            IntegerProperty numPuls1 = new SimpleIntegerProperty(escena1.getNumContador());
            IntegerProperty numPuls2 = new SimpleIntegerProperty(escena2.getNumContador());

            numPuls1.bindBidirectional(numPuls2);
            numPuls2.bindBidirectional(numPuls1);

            escena1.setStage();
            escena2.setStage();

            escena1.contPuls().addListener(new ChangeListener(){
                @Override
                public void changed(ObservableValue o, Object oldVal, Object newVal){
                    System.out.println("Electric bill has changed!");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ContadorPulsaciones2 {

    public int numContador = 0;  // Inicializa el contador
    public Label lbContador;  // Define el Label de manera global para acceder desde los métodos
    IntegerProperty numPuls = new SimpleIntegerProperty();

    public int getNumContador() {
        return numContador;
    }

    public IntegerProperty contPuls() {
        return numPuls;
    }

    // CONTADOR FUNCIONAL
    private void contador(int num) {
        numContador = (num == 0) ? 0 : numContador + num;
        lbContador.setText(String.valueOf(numContador));
    }

    public void setStage() {
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
            lbContador = new Label("0");  // Empieza en 0
            lbContador.setFont(Font.font(30));
            lbContador.setId("lbContador");

            // AÑADIR BOTONES Y CONTADOR A LA INTERFAZ
            panelBotones.getChildren().addAll(btMas, btMenos, btCero);
            raiz.getChildren().addAll(panelBotones, lbContador);

            // CREAR ESCENA
            Scene escena = new Scene(raiz, 420, 150);
            escena.getStylesheets().add(getClass().getResource("/com/example/ejercicios/styles/styleContador.css").toExternalForm());
            escenarioPrincipal.setTitle("Contador");
            escenarioPrincipal.setScene(escena);
            escenarioPrincipal.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
