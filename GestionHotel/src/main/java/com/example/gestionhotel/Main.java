package com.example.gestionhotel;

import com.example.gestionhotel.controller.EditarClienteController;
import com.example.gestionhotel.controller.EstadisticasReservasController;
import com.example.gestionhotel.controller.RootLayoutController;
import com.example.gestionhotel.controller.VPController;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.repository.ClienteRepository;
import com.example.gestionhotel.modelo.repository.impl.ClienteRepositoryImpl;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.ClienteVO;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ArrayList;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private HotelModelo hotelModelo;
    private ClienteUtil clienteUtil;
    private ClienteRepository clienteRepository;
    VPController controllerVP;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws SQLException {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Gestión Hotel");
        this.primaryStage.getIcons().add(new Image("file:resources/image/iconoHotel.png"));
        initRootLayout();
        pantallaPrincipal();
    }

    // Método que devuelve una lista de Cliente
    public ObservableList<Cliente> getClienteData() {
        return clienteData;
    }

    // Método para mostrar el RootLayout
    public void initRootLayout() {
      try {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(Main.class.getResource("/com/example/gestionhotel/RootLayout.fxml"));
          rootLayout = (BorderPane) loader.load();
          Scene scene = new Scene(rootLayout);
          primaryStage.setScene(scene);
          primaryStage.setTitle("Gestion Hotel");
          primaryStage.show();
          RootLayoutController controller = loader.getController();
          controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar la pantalla principal
    public void pantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/VP.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);

            VPController controller = loader.getController();
            hotelModelo = new HotelModelo();
            clienteRepository = new ClienteRepositoryImpl();

            hotelModelo.setClienteRepository(clienteRepository);
            
            controller.setHotelModelo(hotelModelo);
            controller.setMain(this);

            clienteData.addAll(controller.tablaClientes()); // Añadimos el ArrayList a un ObservableList para convertirlo
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExeptionHotel e) {
            mostrarAlertaError("Error al cargar la pantalla principal", e.getMensaje());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para mostrar editarCliente o añadirCliente
    public boolean pantallaEditar(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/EditarCliente.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.initModality(Modality.NONE);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            EditarClienteController controller = loader.getController();
            controller.setStage(stage);
            controller.setHotelModelo(hotelModelo);
            controller.setCliente(cliente);
            stage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para mostrar las estadísticas de las reservas de los Clientes
    public void verEstadisticas() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/EstadisticasReservas.fxml"));
            AnchorPane estadisticasCliente = loader.load();
            Stage stage = new Stage();
            stage.setTitle("EStadísticas de reservas");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(estadisticasCliente);
            stage.setScene(scene);
            EstadisticasReservasController controller = loader.getController();
//            controller.set
        } catch (IOException e) {
            e.printStackTrace();
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
