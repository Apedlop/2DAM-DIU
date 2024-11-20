package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarClienteController {

    @FXML
    private TextField ponerDni;
    @FXML
    private TextField ponerNombre;
    @FXML
    private TextField ponerApellido;
    @FXML
    private TextField ponerDireccion;
    @FXML
    private TextField ponerLocalidad;
    @FXML
    private TextField ponerProvincia;

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
        ponerDni.setText(cliente.getDni());
        ponerNombre.setText(cliente.getNombre());
        ponerApellido.setText(cliente.getApellido());
        ponerDireccion.setText(cliente.getDireccion());
        ponerLocalidad.setText(cliente.getLocalidad());
        ponerProvincia.setText(cliente.getProvincia());
    }

    // Retorna "true" si el usuario hace click en ACEPTAR
    public boolean isOkClicked() {
        return okClicked;
    }

    // Valida la entrada del usuario en los campos de texto, para que no deje ninguno vacío
    private boolean isValido() {
        String mensajeError = "";

        String dniTexto = ponerDni.getText().trim();
        if (dniTexto == null || dniTexto.length() == 0 || !dniTexto.matches("\\d{8}[A-Za-z]")) {
            mensajeError += "DNI no válido. Debe tener 8 dígitos seguidos de una letra.\n";
        }
        if (ponerDni.getText() == null || ponerDni.getText().length() == 0) {
            mensajeError += "DNI no válido\n";
        }
        if (ponerNombre.getText() == null || ponerNombre.getText().trim().equals("") || ponerNombre.getText().length() == 0) {
            mensajeError += "Nombre no válido\n";
        }
        if (ponerApellido.getText() == null || ponerApellido.getText().trim().equals("")) {
            mensajeError += "Apellido no valido\n";
        }
        if (ponerDireccion.getText() == null || ponerDireccion.getText().trim().equals("")) {
            mensajeError += "Dirección no valido\n";
        }
        if (ponerLocalidad.getText() == null || ponerLocalidad.getText().trim().equals("")) {
            mensajeError += "Localidad no valida\n";
        }
        if (ponerProvincia.getText() == null || ponerProvincia.getText().trim().equals("")) {
            mensajeError += "Provincia no valida\n";
        }

        if (mensajeError.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Invalid Fields");
            alerta.setHeaderText("Please correct invalid fields");
            alerta.setContentText(mensajeError);
            alerta.showAndWait();
            return false;
        }
    }

    @FXML
    private void botonOk() {
        if (isValido()) {
            cliente.setDni(ponerDni.getText());
            cliente.setNombre(ponerNombre.getText());
            cliente.setApellido(ponerApellido.getText());
            cliente.setDireccion(ponerDireccion.getText());
            cliente.setLocalidad(ponerLocalidad.getText());
            cliente.setProvincia(ponerProvincia.getText());
            okClicked = true;
            stage.close();
        }
    }

    @FXML
    private void botonCancelar() {
        stage.close();
    }

}
