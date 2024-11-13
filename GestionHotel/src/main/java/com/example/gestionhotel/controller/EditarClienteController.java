package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;

public class EditarClienteController {

    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField direccion;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField localidad;
    @FXML
    private TextField provincia;

    private Stage stage;
    private Cliente cliente;
    private HotelModelo hotelModelo;
    private boolean okClicked = false;

    // Constructor por defecto vacío
    public EditarClienteController() {

    }

    // Inyectamos HotelModelo
    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    // Inyectamos Stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para añadir el cliente y añadirlo a los campos
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        nombre.setText(cliente.getNombre());
        apellido.setText(cliente.getApellido());
        direccion.setText(cliente.getDireccion());
        localidad.setText(cliente.getLocalidad());
        provincia.setText(cliente.getProvincia());
    }

    // Retorna "true" si el usuario hace click en ACEPTAR
    public boolean isOkClicked() {
        return okClicked;
    }

    // Valida la entrada del usuario en los campos de texto, para que no deje ninguno vacío
    private void isValido() {
        String mensajeError = "";
    }

    @FXML
    private void botonOk() {
        if (isInp)
    }

}
