package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.ClienteVO;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

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
    private ClienteUtil clienteUtil;
    private ArrayList<Cliente> clientes;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();

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
        hotelModelo = new HotelModelo();
        return hotelModelo.obtenerListaClientes();
    }

    // Método para cambiar Cliente a ClienteVO a través del Util y crearla en la BS
    public void clienteAClienteVO(Cliente cliente) throws ExeptionHotel {
        clienteUtil = new ClienteUtil();
        hotelModelo = new HotelModelo();
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        hotelModelo.añadirCliente(clienteVO);
    }

    // Método para editar el cliente de Cliente a ClienteVO
    public void editarClienteAClienteVO(Cliente cliente) throws ExeptionHotel {
        hotelModelo = new HotelModelo();
        clienteUtil = new ClienteUtil();
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        hotelModelo.editarCliente(clienteVO);
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

    private void mostrarInformacionCliente(ClienteVO cliente) {
        if (cliente == null) {
            mostrarAlerta("Cliente no encontrado", "No se encontró un cliente con el DNI proporcionado.");
            return;
        }

        // Supongamos que tienes estos elementos en tu interfaz
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
        Cliente cliente = new Cliente();
        boolean okClicked = main.pantallaEditar(cliente);
        if (okClicked) {
            try {
                clienteAClienteVO(cliente);
                main.getClienteData().add(cliente);
                cargarDatosClientes();
            } catch (ExeptionHotel | SQLException e) {
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
                    editarClienteAClienteVO(clienteSelecc);
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
            clienteUtil = new ClienteUtil();
            try {
                hotelModelo.eliminarCliente(clienteUtil.convertirClienteVO(tablaClientes.getItems().get(selectIndex)));
                cargarDatosClientes();
            } catch (ExeptionHotel | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al eliminar al cliente");
                alert.setTitle("Error con la base de datos");
                alert.setContentText("No se puede conectar con la base de datos para eliminar la persona");
                alert.showAndWait();
            }
            tablaClientes.getItems().remove(selectIndex);
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
            System.out.println("DNI buscado: " + dniBuscado);

            if (dniBuscado.isEmpty()) {
                mostrarAlerta("Búsqueda vacía", "Por favor, ingresa un DNI para buscar.");
                return;
            }
            hotelModelo = new HotelModelo(); // Verifica si esto es necesario cada vez
            ClienteVO cliente = hotelModelo.buscarDNI(dniBuscado);
            mostrarInformacionCliente(cliente);
        } catch (ExeptionHotel e) {
            System.out.println("Excepción capturada: " + e.getMessage());
            mostrarAlerta("Error", e.getMessage());
        }
    }





    @FXML
    private void botonVerReserva() {

    }
}
