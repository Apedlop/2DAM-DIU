package com.example.gestionhotel;

import com.example.gestionhotel.controller.RootLayoutController;
import com.example.gestionhotel.modelo.HotelModelo;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private HotelModelo hotelModelo;
    private ClienteUtil clienteUtil;
    private ClienteRepositoryImpl clienteRepository;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();

    // Constructor donde ejecutamos el metodo addlist para que agrege la lista de clientes de la BD
    public Main() {
        clienteData.addAll(addList());
    }

    // Método que devuelve una lista de Cliente
    public ObservableList<Cliente> getClienteData() {
        return clienteData;
    }

    public ArrayList<Cliente> addList() {
        clienteUtil = new ClienteUtil();
        clienteRepository = new ClienteRepositoryImpl();
        hotelModelo = new HotelModelo();
        hotelModelo.setClienteRepository(clienteRepository);

        ArrayList<Cliente> listaCliente = new ArrayList<>();
        ArrayList<ClienteVO> listaClienteVO = new ArrayList<>();

        try {
            listaClienteVO = hotelModelo.listarClientes();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al listar las personas.");
            alert.setTitle("Error con la base de datos");
            alert.setContentText("No se puede conectar con la base de datos");
            alert.showAndWait();
            return listaCliente;
        }
        return clienteUtil.listaClientes(listaClienteVO);
    }

    // Método para mostrar el RootLayout
    public void initRootLayout() {
      try {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(Main.class.getResource("/com/example/gestionhotel/RootLayout.fxml"));
          rootLayout = loader.load();
          Scene scene = new Scene(rootLayout);
          primaryStage.setScene(scene);
          primaryStage.setTitle("Gestion Hotel");
          primaryStage.show();
          RootLayoutController controller = loader.getController();
          controller.setMain(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void verEstadisticas() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/EstadisticasReservas.fxml"));
            AnchorPane estadisticasCliente = loader.load();
            Stage stage = new Stage();
            stage.setT

        }
    }

    @Override
    public void start(Stage stage) throws Exception {

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
