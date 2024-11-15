package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.ClienteVO;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private Main main;
    private HotelModelo hotelModelo;
    private ClienteUtil clienteUtil;
    private ObservableList<Cliente> clienteData = FXCollections.observableArrayList();

    // Método para recibir el modelo y configurar los datos
    public void setHotelModelo(HotelModelo hotelModelo) throws ExeptionHotel {
        this.hotelModelo = hotelModelo;
    }

    public void setMain(Main main) {
        this.main = main;
        tablaClientes.setItems(main.getClienteData());
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

    // Método para cambiar Cliente a ClienteVO a través del Util y crearla en la BS
    public void clienteAClienteVO(Cliente cliente) throws ExeptionHotel {
        clienteUtil = new ClienteUtil();
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        hotelModelo.añadirCliente(clienteVO);
    }

    // Método para editar el cliente de Cliente a ClienteVO
    public void editarClienteAClienteVO(Cliente cliente) throws ExeptionHotel {
        clienteUtil = new ClienteUtil();
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        hotelModelo.editarCliente(clienteVO);
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
            } catch (ExeptionHotel e) {
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
                } catch (ExeptionHotel e) {
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
            } catch (ExeptionHotel e) {
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
    private void busquedaID() {

    }

    @FXML
    private void botonVerReserva() {

    }
}
