package com.example.controles;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class Botones extends Application {
    @Override
    public void start(Stage escenarioPrincipal) {
        try {
            HBox raiz = new HBox();
            raiz.setPadding(new Insets(20, 20, 20, 20));
            raiz.setSpacing(10);
            raiz.setAlignment(Pos.CENTER); // Sirve para poner en el centro los elementos

            Button  btTexto, btTextoImagen, btImagen;
            btTexto = new Button();
            btTextoImagen = new Button();
            btImagen = new Button();

            btTexto.setText("Pulsar");

            Image imagen = new Image("file:resources/imagenes/beber.png", 100, 100, true, true);
            btTextoImagen.setGraphic(new ImageView(imagen));
            btTextoImagen.setText("Beber");
            btTextoImagen.setGraphicTextGap(20);
            btTextoImagen.setFont(Font.font("Ani", 30));

            imagen = new Image("file:resources/imagenes/apagar.png", 100, 100, true, true);
            btImagen.setGraphic(new ImageView(imagen));

            raiz.getChildren().addAll(btTexto, btTextoImagen, btImagen);

            Scene escena = new Scene(raiz, 500, 220);
            escenarioPrincipal.setTitle("Botones");
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