package com.example.ejercicios.ContadorPuls;

import com.example.ejercicios.ContadorPuls.controler.controlerPuls;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ContadorPuls extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(controlerPuls.class.getResource("/com/example/ejercicios/ContadorPuls2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Contador 1");
        stage.setScene(scene);
        stage.show();
        controlerPuls controlador1 = fxmlLoader.getController();

        Stage stage2 = new Stage();
        FXMLLoader fxmlLoader2 = new FXMLLoader(controlerPuls.class.getResource("/com/example/ejercicios/ContadorPuls2.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load());
        stage2.setTitle("Contador 2");
        stage2.setScene(scene2);
        stage2.show();
        controlerPuls controlador2 = fxmlLoader2.getController();

        controlador1.getContador().bindBidirectional(controlador2.getContador());

    }

    public static void main(String[] args) {
        launch();
    }
}
