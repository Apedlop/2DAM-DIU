package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VPController {

    // Elementos de la tabla de clientes
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> columnaNombre;
    @FXML
    private TableColumn<Cliente, String> columnaApellido;
    @FXML
    private Label dni;
    @FXML
    private Label nombre;
    @FXML
    private Label apellido;
    @FXML
    private Label direccion;
    @FXML
    private Label localidad;
    @FXML
    private Label provincia;
    @FXML
    private TextField buscarDni;

    private Main main;
    private HotelModelo hotelModelo;
    private ClienteUtil clienteUtil = new ClienteUtil();
    private ArrayList<Cliente> clientes;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();
    private ObservableList<Reserva> reservaData = FXCollections.observableArrayList();

    // Método para recibir el modelo y configurar los datos
    public void setHotelModelo(HotelModelo hotelModelo) throws ExeptionHotel {
        this.hotelModelo = hotelModelo;
    }

    public void setMain(Main main) {
        tablaClientes.setItems(main.getClienteData()); // Enlaza datos con la tabla
        this.main = main;
    }

    // Método para mostrar los datos de un Cliente
    private void mostrarDatosCliente(Cliente cliente) {
        if (cliente != null) {
            dni.setText(cliente.getDni());
            nombre.setText(cliente.getNombre());
            apellido.setText(cliente.getApellido());
            direccion.setText(cliente.getDireccion());
            localidad.setText(cliente.getLocalidad());
            provincia.setText(cliente.getProvincia());
        } else {
            dni.setText("");
            nombre.setText("");
            apellido.setText("");
            direccion.setText("");
            localidad.setText("");
            provincia.setText("");
        }
    }

    // Método que listará todas las resevas
    public ObservableList<Reserva> getReservaData() throws SQLException {
        ArrayList<Reserva> reservas = hotelModelo.obtenerListaReservas();
        reservaData.setAll(reservas);
        return reservaData;
    }

    // Método para configurar la tabla de datos
    @FXML
    private void initialize() {
        // Asigna las propiedades de las columnas del TableView para mostrar los datos de la persona.
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaApellido.setCellValueFactory(cellData -> cellData.getValue().apellidoProperty());
        // Cambios de selección en la tabla de Clientes
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mostrarDatosCliente(newValue);
        });
    }

    public ArrayList<Cliente> tablaClientes() throws ExeptionHotel, SQLException {
        return hotelModelo.obtenerListaClientes();
    }

    // Método para cargar y ordenar los clientes en la interfaz
    private void cargarDatosClientes() throws ExeptionHotel, SQLException {
        // Obtener la lista de clientes desde el modelo
        ArrayList<Cliente> listaClientes = hotelModelo.obtenerListaClientes();

        // Convertir la lista a un ObservableList (para usar con TableView de JavaFX)
        ObservableList<Cliente> clienteData = FXCollections.observableArrayList(listaClientes);

        // Ordenar la lista de clientes por apellido
        clienteData.sort(new Comparator<Cliente>() {
            @Override
            public int compare(Cliente c1, Cliente c2) {
                return c1.getApellido().compareTo(c2.getApellido());
            }
        });

        // Asignar la lista ordenada a la tabla
        tablaClientes.setItems(clienteData);
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInformacionCliente(Cliente cliente) {
        if (cliente == null) {
            mostrarAlerta("Cliente no encontrado", "No se encontró un cliente con el DNI proporcionado.");
            return;
        }

        dni.setText(cliente.getDni());
        nombre.setText(cliente.getNombre());
        apellido.setText(cliente.getApellido());
        direccion.setText(cliente.getDireccion());
        localidad.setText(cliente.getLocalidad());
        provincia.setText(cliente.getProvincia());
    }

    /*
    * MÉTODOS PARA LOS BOTONES
    */

    @FXML
    private void botonNuevoCliente() {
        // Crear nuevo cliente y reserva
        Cliente cliente = new Cliente();
        Reserva reserva = new Reserva();

        // Asegurarse de que los datos necesarios se pasan correctamente
        boolean okClicked = main.pantallaCrear(cliente, reserva);

        if (okClicked) {
            try {
                // Después de crear, se asocia el cliente y la reserva
                hotelModelo.anadirCliente(cliente);
                hotelModelo.anadirReserva(reserva);

                // Mostrar en consola para depuración
                System.out.println("BotonNuevoCliente " + cliente);
                System.out.println("BotonNuevoCliente " + reserva);

                // Añadir el cliente a la lista de datos
                main.getClienteData().add(cliente);
                System.out.println("nuevoCLienre: " + clienteData);
                main.getReservaData().add(reserva);
                cargarDatosClientes();
            } catch (ExeptionHotel | SQLException e) {
                // Manejo de errores de base de datos
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al añadir la persona");
                alert.setTitle("Error con la base de datos");
                alert.setContentText("No se puede conectar con la base de datos para añadir la persona");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void botonEditarCliente() {
        Cliente clienteSelecc = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecc != null) {
            boolean okClicked = main.pantallaEditar(clienteSelecc);
            if (okClicked) {
                try {
                    hotelModelo.editarCliente(clienteSelecc);
                    mostrarDatosCliente(clienteSelecc);
                    cargarDatosClientes();
                } catch (ExeptionHotel | SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error al editar al cliente");
                    alert.setTitle("Error con la base de datos");
                    alert.setContentText("No se puede conectar con la base de datos para editar la persona");
                    alert.showAndWait();
                }
            }
        } else {
            // Nada seleccionado
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Nada seleccionado");
            alerta.setHeaderText("No se ha seleccionado ningún cliente");
            alerta.setContentText("Porfavor, seleccione a un cliente.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void botonEliminarCliente() {
        int selectIndex = tablaClientes.getSelectionModel().getSelectedIndex();
        if (selectIndex >= 0) {
            try {
                // Eliminar del modelo (base de datos)
                Cliente clienteSeleccionado = tablaClientes.getItems().get(selectIndex);
                hotelModelo.eliminarCliente(clienteSeleccionado);

                // Actualizar la lista de datos y la interfaz
                cargarDatosClientes();  // Esto actualizará la tabla automáticamente
            } catch (ExeptionHotel | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al eliminar al cliente");
                alert.setTitle("Error con la base de datos");
                alert.setContentText("No se puede conectar con la base de datos para eliminar la persona");
                alert.showAndWait();
            }
        } else {
            // Nada seleccionado
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("No Selection");
            alerta.setHeaderText("No Person Selected");
            alerta.setContentText("Please select a person in the table.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void busquedaDni() {
        try {
            String dniBuscado = buscarDni.getText().trim();

            // Si el campo de búsqueda está vacío, cargar todos los clientes
            if (dniBuscado.isEmpty()) {
                mostrarAlerta("Búsqueda vacía", "Ingrese un DNI.");

                // Cargar la lista completa de clientes desde el modelo
                List<Cliente> todosClientes = hotelModelo.obtenerListaClientes();  // Asegúrate de tener este método en tu modelo

                // Actualizar la lista observable
                clienteData.setAll(todosClientes);
                tablaClientes.setItems(clienteData);
                return;
            }

            // Validar formato del DNI (por ejemplo, 8 dígitos y una letra)
            if (!dniBuscado.matches("\\d{8}[A-Za-z]")) {
                mostrarAlerta("DNI inválido", "El formato del DNI es incorrecto. Debe contener 8 números seguidos de una letra.");
                return;  // Detiene la ejecución si el formato es incorrecto
            }

            // Buscar el cliente por DNI
            System.out.println("holaa");
            Cliente cliente = hotelModelo.buscarDNI(dniBuscado);
            System.out.println("vo" + cliente);
            // Si el cliente no existe, mostrar alerta
            if (cliente == null) {
                mostrarAlerta("Cliente no encontrado", "No se ha encontrado un cliente con el DNI proporcionado.");
                return;  // Detiene la ejecución si no se encuentra el cliente
            }

            // Si el cliente es encontrado, mostrar la información
            mostrarInformacionCliente(cliente);

            // Crear una lista temporal con solo el cliente encontrado y mostrarla en la tabla
            clienteData.setAll(cliente);
            tablaClientes.setItems(clienteData);

        } catch (ExeptionHotel | SQLException e) {
            // Manejo de cualquier excepción
            System.out.println("Excepción capturada: " + e.getMessage());
            mostrarAlerta("Error", e.getMessage());
        }
    }

    @FXML
    private void botonVerReserva() {
        int selectCliente = tablaClientes.getSelectionModel().getSelectedIndex();
        if (selectCliente >= 0) {
            Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem(); // Obtener el cliente seleccionado
            String dniCliente = clienteSeleccionado.getDni(); // Obtener el DNI del cliente
            main.setClienteSeleccionado(dniCliente);
            main.abrirVentanaReservas(dniCliente); // Pasar el DNI al método de Main
        } else {
            // Nada seleccionado
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Nada seleccionado");
            alerta.setHeaderText("Cliente no seleccionado");
            alerta.setContentText("Por favor, seleccione un cliente de la tabla");
            alerta.showAndWait();
        }
    }


}
