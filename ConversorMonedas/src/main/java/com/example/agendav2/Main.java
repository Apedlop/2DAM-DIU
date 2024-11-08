package com.example.agendav2;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;
import Modelo.repository.impl.MonedaRepositoryImpl; // Asegúrate de que este paquete existe
import com.example.agendav2.controller.ConversorController;
import com.example.agendav2.modelo.ConversorModelo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML que contiene el diseño y el controlador
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConversorView.fxml"));
        Parent root = loader.load();
        ConversorController controlador = loader.getController();


        ConversorModelo conversorModelo;
        MonedaRepositoryImpl monedaRepository = new MonedaRepositoryImpl(); // Crear instancia del repositorio
        conversorModelo = new ConversorModelo(); // Pasar repositorio al modelo
        conversorModelo.setMonedaRepository(monedaRepository);
        controlador.setConversorModelo(conversorModelo);
        controlador.getMultiplicador();


//        controlador.getMultiplicador(mult);

        // Establece la escena
        primaryStage.setTitle("Conversor de Monedas");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
