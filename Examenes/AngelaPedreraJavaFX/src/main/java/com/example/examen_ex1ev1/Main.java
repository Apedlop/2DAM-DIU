package com.example.examen_ex1ev1;

import Modelo.ExcepcionMoneda;
import Modelo.repository.MonedaRepository;
import Modelo.repository.impl.MonedaRepositoryImpl;
import com.example.examen_ex1ev1.controller.OpcionController;
import com.example.examen_ex1ev1.controller.RootLayoutController;
import com.example.examen_ex1ev1.controller.VPController;
import com.example.examen_ex1ev1.modelo.ConversorModelo;
import com.example.examen_ex1ev1.modelo.Moneda;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    private ConversorModelo conversorModelo;
    private ObservableList<Moneda> monedaData = FXCollections.observableArrayList();
    private Stage stage;
    private BorderPane rootLayout;
    private MonedaRepository monedaRepository;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("Gestión Hotel");

        initRootLayout();
        pantallaPrincipal();
    }

    public ObservableList<Moneda> getMonedaData() {
        return monedaData;
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/examen_ex1ev1/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.setTitle("Conversor Monedas");
            stage.show();
            RootLayoutController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/examen_ex1ev1/VP.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);

            VPController controller = loader.getController();
            conversorModelo = new ConversorModelo();
            monedaRepository = new MonedaRepositoryImpl();

            conversorModelo.setMonedaRepository(monedaRepository);

            controller.setConversorModelo(conversorModelo);
            controller.setMain(this);
            controller.getMult();

            monedaData.addAll(controller.tablaMonedas());
        } catch (ExcepcionMoneda e) {
            mostrarAlertaError("Error al cargar la pantalla principal", e.getMessage());
        } catch (IOException e) {
//            mostrarAlertaError("Ha ocurrido un erorr", e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void verTotalMonedas() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("/com/example/examen_ex1ev1/Opcion.fxml"));
//            AnchorPane pane = (AnchorPane) loader.load();
//            rootLayout.setCenter(pane);
//
//            OpcionController controller = loader.getController();
//            controller.se
//        }
    }

    // Método auxiliar para mostrar alertas de error
    private void mostrarAlertaError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setTitle("Error con la base de datos");
        alert.setContentText(content);
        alert.showAndWait();
    }

}