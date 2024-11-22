package com.example.ejemplo_ex1ev1;

import com.example.ejemplo_ex1ev1.controller.CatalogoController;
import com.example.ejemplo_ex1ev1.controller.NuevoArticuloController;
import com.example.ejemplo_ex1ev1.modelo.Articulo;
import com.example.ejemplo_ex1ev1.modelo.ArticuloVO;
import com.example.ejemplo_ex1ev1.modelo.CatalogoModelo;
import com.example.ejemplo_ex1ev1.modelo.ExceptionCatalogo;
import com.example.ejemplo_ex1ev1.modelo.repository.CatalogoRepository;
import com.example.ejemplo_ex1ev1.modelo.repository.impl.CatalogoRepositoryImpl;
import com.example.ejemplo_ex1ev1.modelo.util.CatalogoUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private CatalogoModelo catalogoModelo;
    private CatalogoUtil catalogoUtil;
    private CatalogoRepository catalogoRepository;
    CatalogoController controller;
    private ObservableList<Articulo> articuloData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws SQLException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gestión Hotel");
        this.primaryStage.getIcons().add(new Image("file:resources/image/iconoHotel.png"));
        pantallaPrincipal();
    }

    // Método que devuelve una lista de articulos de tipo ObserbableList
    public ObservableList<Articulo> getArticuloData() {
        return articuloData;
    }

    // Método para mostrar la pantalla principal
   public void pantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/ejemplo_ex1ev1/Catalogo.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);

            CatalogoController catalogoController = loader.getController();
            catalogoModelo = new CatalogoModelo();
            catalogoRepository = new CatalogoRepositoryImpl();

            catalogoModelo.setCatalogoRepository(catalogoRepository);

            catalogoController.setCatalogoModelo(catalogoModelo);
            catalogoController.setMain(this);

            articuloData.addAll(catalogoController.tablaArticulos());

        } catch (ExceptionCatalogo e) {
            mostrarAlertaError("Error al cargar la pantalla principal", e.getMensaje());
        } catch (SQLException e) {
            mostrarAlertaError("Error al añadir a la lista", e.getMessage());
        } catch (IOException e) {
            mostrarAlertaError("Ha ocurrido un erorr", e.getMessage());
        }
   }

   // Método para mostrar pantalla de articulo nuevo
    public boolean pantallaNuevo(Articulo articulo) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/ejemplo_ex1ev1/NuevoArticulo.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo Artículo");
            stage.initModality(Modality.NONE);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            NuevoArticuloController articuloController = loader.getController();
            articuloController.setStage(stage);
            articuloController.setCatalogoModelo(catalogoModelo);
            articuloController.setArticulo(articulo);
            stage.showAndWait();
            return articuloController.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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
