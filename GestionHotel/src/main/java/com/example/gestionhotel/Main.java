package com.example.gestionhotel;

import com.example.gestionhotel.controller.*;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.repository.ClienteRepository;
import com.example.gestionhotel.modelo.repository.ReservaRepository;
import com.example.gestionhotel.modelo.repository.impl.ClienteRepositoryImpl;
import com.example.gestionhotel.modelo.repository.impl.ReservaRepositoryImpl;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.Reserva;
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

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private HotelModelo hotelModelo;
    private ClienteUtil clienteUtil;
    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;
    private  String dniSeleccionado;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
    private ObservableList<Reserva> reservaData = FXCollections.observableArrayList();

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

    // Método que devuelve una lista de Reservas
    public ObservableList<Reserva> getReservaData() {
        return reservaData;
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
            reservaRepository = new ReservaRepositoryImpl();

            hotelModelo.setClienteRepository(clienteRepository);
            hotelModelo.setReservaRepository(reservaRepository);

            controller.setHotelModelo(hotelModelo);
            controller.setMain(this);

            clienteData.addAll(controller.tablaClientes()); // Añadimos el ArrayList a un ObservableList para convertirlo
        } catch (ExeptionHotel e) {
            mostrarAlertaError("Error al cargar la pantalla principal", e.getMensaje());
        } catch (SQLException e) {
            mostrarAlertaError("Error al añadir a la lista", e.getMessage());
        } catch (IOException e) {
            mostrarAlertaError("Ha ocurrido un erorr", e.getMessage());
        }
    }

    // Método para mostrar editarCliente o añadirCliente
    public boolean pantallaCrear(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/CrearCliente.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Crear el nuevo Stage
            Stage stage = new Stage();
            stage.setTitle("Crear Cliente");

            // Asegúrate de usar Modality.APPLICATION_MODAL para que sea modal
            stage.initModality(Modality.APPLICATION_MODAL); // Cambiar a modalidad modal
            stage.initOwner(primaryStage);  // Asegúrate de que primaryStage esté inicializado

            // Crear la escena y configurarla en el Stage
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Obtener el controlador y pasarle los datos
            CrearClienteController controller = loader.getController();
            controller.setStage(stage);
            hotelModelo = new HotelModelo();
            clienteRepository = new ClienteRepositoryImpl();
            reservaRepository = new ReservaRepositoryImpl();
            controller.setHotelModelo(hotelModelo);
            hotelModelo.setReservaRepository(reservaRepository);
            hotelModelo.setClienteRepository(clienteRepository);
            controller.setCliente(cliente);

            // Mostrar la ventana modal y esperar su cierre
            stage.showAndWait();

            // Retornar si se hizo clic en OK dentro del controlador
            return controller.isOkClicked();
        } catch (IOException e) {
            // Imprimir cualquier excepción de IO para ver el problema
            e.printStackTrace();
            return false;
        }
    }

    // Método para mostrar editarCliente o añadirCliente
    public boolean pantallaEditar(Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/EditarCliente.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Crear el nuevo Stage
            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");

            // Asegúrate de usar Modality.APPLICATION_MODAL para que sea modal
            stage.initModality(Modality.APPLICATION_MODAL); // Cambiar a modalidad modal
            stage.initOwner(primaryStage);  // Asegúrate de que primaryStage esté inicializado

            // Crear la escena y configurarla en el Stage
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Obtener el controlador y pasarle los datos
            EditarClienteController controller = loader.getController();
            controller.setStage(stage);
            hotelModelo = new HotelModelo();
            clienteRepository = new ClienteRepositoryImpl();
            reservaRepository = new ReservaRepositoryImpl();

            controller.setHotelModelo(hotelModelo);

            hotelModelo.setReservaRepository(reservaRepository);
            hotelModelo.setClienteRepository(clienteRepository);

            controller.setCliente(cliente);

            // Mostrar la ventana modal y esperar su cierre
            stage.showAndWait();

            // Retornar si se hizo clic en OK dentro del controlador
            return controller.isOkClicked();
        } catch (IOException e) {
            // Imprimir cualquier excepción de IO para ver el problema
            e.printStackTrace();
            return false;
        }
    }

    public boolean pantallaCrearReserva(Reserva reserva) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/EditarReserva.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Crear el nuevo Stage
            Stage stage = new Stage();
            stage.setTitle("Crear Reserva");

            // Asegúrate de usar Modality.APPLICATION_MODAL para que sea modal
            stage.initModality(Modality.APPLICATION_MODAL); // Cambiar a modalidad modal
            stage.initOwner(primaryStage);  // Asegúrate de que primaryStage esté inicializado

            // Crear la escena y configurarla en el Stage
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Obtener el controlador y pasarle los datos
            EditarReservaController controller = loader.getController();
            controller.setStage(stage);

            controller.setDniClienteSeleccionado(dniSeleccionado);
            System.out.println("hola " + dniSeleccionado);

            hotelModelo = new HotelModelo();
            reservaRepository = new ReservaRepositoryImpl();

            controller.setHotelModelo(hotelModelo);

            hotelModelo.setReservaRepository(reservaRepository);

            controller.setReserva(reserva);
            controller.setDniClienteSeleccionado(reserva.getDniCliente());
            stage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            // Imprimir cualquier excepción de IO para ver el problema
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
            stage.setTitle("Estadísticas de reservas");
            // stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            Scene scene = new Scene(estadisticasCliente);
            stage.setScene(scene);

            EstadisticasReservasController controller = loader.getController();
            controller.setReservasMeses(reservaData);
            System.out.println("ReservaData: " + reservaData);
            System.out.println();
            stage.show();  // Asegúrate de llamar a stage.show() para mostrar la ventana
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verTiposHabitaciones() {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/TipoHabitaciones.fxml"));
            AnchorPane pane = loader.load();

            // Crear un nuevo escenario (ventana) para mostrar la información
            Stage stage = new Stage();
            stage.setTitle("Tipos de Habitaciones");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage); // Asegúrate de que primaryStage esté definido en tu clase Main.
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Obtener el controlador del FXML cargado
            TipoHabitacionesController controller = loader.getController();

            HotelModelo hotelModelo = new HotelModelo();
            controller.setHotelModelo(hotelModelo);

            // Mostrar la ventana
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para abrir la ventana de reservas
    public void abrirVentanaReservas(String dni) {
        if (dni == null || dni.isEmpty()) {
            mostrarAlertaError("Error", "No se ha seleccionado un cliente.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/gestionhotel/VR.fxml"));
            AnchorPane reservaView = (AnchorPane) loader.load();

            Scene reservaScene = new Scene(reservaView);
            Stage reservaStage = new Stage();
            reservaStage.setTitle("Gestión de Reservas");
            reservaStage.setScene(reservaScene);
            reservaStage.show();

            // Obtener el controlador de la ventana de reservas
            VRController controller = loader.getController();
            hotelModelo = new HotelModelo();
            reservaRepository = new ReservaRepositoryImpl();

            hotelModelo.setReservaRepository(reservaRepository);
            controller.setHotelModelo(hotelModelo);

            controller.setMain(this); // Pasar la instancia del Main al controlador de la vista de reservas
            controller.setDniClienteSeleccionado(dni); // Pasar el DNI al controlador de VR
            System.out.println(dni);
            dniSeleccionado = controller.getDniClienteSeleccionado();
            System.out.println("sele" + dniSeleccionado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean pantallaEditarReserva(Reserva reserva) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/gestionhotel/EditarReserva.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            // Crear el nuevo Stage
            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");

            // Asegúrate de usar Modality.APPLICATION_MODAL para que sea modal
            stage.initModality(Modality.APPLICATION_MODAL); // Cambiar a modalidad modal
            stage.initOwner(primaryStage);  // Asegúrate de que primaryStage esté inicializado

            // Crear la escena y configurarla en el Stage
            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Obtener el controlador y pasarle los datos
            EditarReservaController controller = loader.getController();
            controller.setStage(stage);
            hotelModelo = new HotelModelo();
            clienteRepository = new ClienteRepositoryImpl();
            reservaRepository = new ReservaRepositoryImpl();

            controller.setHotelModelo(hotelModelo);

            hotelModelo.setReservaRepository(reservaRepository);
            hotelModelo.setClienteRepository(clienteRepository);

            controller.setReserva(reserva);

            // Mostrar la ventana modal y esperar su cierre
            stage.showAndWait();

            // Retornar si se hizo clic en OK dentro del controlador
            return controller.isOkClicked();
        } catch (IOException e) {
            // Imprimir cualquier excepción de IO para ver el problema
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
