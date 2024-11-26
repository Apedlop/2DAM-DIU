package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.ReservaVO;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import com.example.gestionhotel.modelo.util.ReservaUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class VRController {

    @FXML
    private TableView<Reserva> tablaReservas;
    @FXML
    private TableColumn<Reserva, String> columnaCodigo;
    @FXML
    private TableColumn<Reserva, String> columnaFechaEntrada;
    @FXML
    private DatePicker fechaLlegada;
    @FXML
    private DatePicker fechaSalida;
    @FXML
    private SplitMenuButton tipoHabitacion;
    @FXML
    private CheckBox fumador;
    @FXML
    private RadioButton alojamientoDesayuno;
    @FXML
    private RadioButton mediaPension;
    @FXML
    private RadioButton pensionCompleta;
    @FXML
    private SplitMenuButton numHabitaciones;

    private Main main;
    private HotelModelo hotelModelo;
    private ReservaUtil reservaUtil = new ReservaUtil();
    private ArrayList<Reserva> reservas;
    private ObservableList<Reserva> reservaData = FXCollections.observableArrayList();

    // Método para recibir el modelo y configurar los datos
    public void setHotelModelo(HotelModelo hotelModelo) throws ExeptionHotel {
        this.hotelModelo = hotelModelo;
    }

    public void setMain(Main main) {
        tablaReservas.setItems(main.getReservaData()); // Enlaza datos con la tabla
        this.main = main;
    }

    public ArrayList<Cliente> tablaReservas() throws ExeptionHotel, SQLException {
        return hotelModelo.obtenerListaClientes();
    }

    @FXML
    public void initialize() {
        columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().dniClienteProperty());
        columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().fechaLlegadaProperty().asString());
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mostrarDatosReserva(newValue);
        });
    }

    // Método para mostrar los datos de las reservas
    private void mostrarDatosReserva(Reserva reserva) {
        if (reserva != null) {
            // Establecer las fechas de llegada y salida
            fechaLlegada.setValue(reserva.getFechaLlegada());
            fechaSalida.setValue(reserva.getFechaSalida());

            // Establecer el tipo de habitación desde el SplitMenuButton
            tipoHabitacion.setText(reserva.getTipoHabitacion().toString()); // Asumiendo que TipoHabitacion tiene un método toString

            // Establecer si es fumador
            fumador.setSelected(reserva.isFumador());

            // Establecer el régimen de alojamiento
            if (reserva.getRegimenAlojamiento() != null) {
                switch (reserva.getRegimenAlojamiento()) {
                    case ALOJAMIENTO_DESAYUNO:
                        alojamientoDesayuno.setSelected(true);
                        break;
                    case MEDIA_PENSION:
                        mediaPension.setSelected(true);
                        break;
                    case PENSION_COMPLETA:
                        pensionCompleta.setSelected(true);
                        break;
                }
            }

            // Establecer el número de habitaciones (si está disponible en la reserva)
            numHabitaciones.setText(String.valueOf(reserva.getNumeroHabitaciones()));
        }
    }

    // Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void botonNuevaReserva() {
        Reserva reserva = new Reserva();
        boolean okClicked = main.
    }

    @FXML
    public void botonEditarReserva() {

    }

    @FXML
    public void botonEliminarReserva() {

    }

    @FXML
    private void seleccionarLlegada() {

    }

    @FXML
    public void seleccionarSalida() {

    }

    public boolean seleccionarFumador() {
        return false;
    }

}
