package com.example.gestionhotel.controller;

import com.example.gestionhotel.Main;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
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
    private RadioButton alojamientoDesayuno, mediaPension, pensionCompleta;
    @FXML
    private SplitMenuButton numHabitaciones;

    private Main main;
    private HotelModelo hotelModelo;
    private ObservableList<Reserva> reservaData = FXCollections.observableArrayList();
    private String dniClienteSeleccionado;

    // Método para establecer el modelo del hotel
    public void setHotelModelo(HotelModelo hotelModelo) throws ExeptionHotel {
        this.hotelModelo = hotelModelo;
        cargarDatosReservas();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getDniClienteSeleccionado() {
        return dniClienteSeleccionado;
    }

    @FXML
    private void initialize() {
        columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().idReservaProperty().asString());
        columnaFechaEntrada.setCellValueFactory(cellData -> cellData.getValue().fechaLlegadaProperty().asString());
        tablaReservas.setItems(reservaData);

        // Listener para cambios en la selección de la tabla
        tablaReservas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostrarDatosReserva(newValue));
    }

    // Cargar reservas de un cliente específico por su DNI
    public void setDniClienteSeleccionado(String dni) {
        this.dniClienteSeleccionado = dni;
        cargarReservasPorCliente(dni);
    }

    // Método para obtener las reservas de un cliente
    private void cargarReservasPorCliente(String dniCliente) {
        try {
            ArrayList<Reserva> reservasCliente = hotelModelo.buscarReserva(dniCliente);
            reservaData.setAll(reservasCliente);
            tablaReservas.setItems(reservaData);
        } catch (ExeptionHotel e) {
            mostrarAlerta("Error", "No se pudieron cargar las reservas: " + e.getMessage());
        }
    }

    // Método auxiliar para mostrar detalles de una reserva seleccionada
    private void mostrarDatosReserva(Reserva reserva) {
        if (reserva != null) {
            fechaLlegada.setValue(reserva.getFechaLlegada());
            fechaSalida.setValue(reserva.getFechaSalida());
            tipoHabitacion.setText(reserva.getTipoHabitacion().toString());
            fumador.setSelected(reserva.isFumador());
            switch (reserva.getRegimenAlojamiento()) {
                case desayuno -> alojamientoDesayuno.setSelected(true);
                case mediaPension -> mediaPension.setSelected(true);
                case pensionCompleta -> pensionCompleta.setSelected(true);
            }
            numHabitaciones.setText(String.valueOf(reserva.getNumeroHabitaciones()));
        } else {
            limpiarDetallesReserva();
        }
    }

    // Limpiar la vista de detalles de reserva
    private void limpiarDetallesReserva() {
        fechaLlegada.setValue(null);
        fechaSalida.setValue(null);
        tipoHabitacion.setText("Selecciona");
        fumador.setSelected(false);
        alojamientoDesayuno.setSelected(false);
        mediaPension.setSelected(false);
        pensionCompleta.setSelected(false);
        numHabitaciones.setText("Selecciona");
    }

    // Cargar la lista completa de reservas
    public ObservableList<Reserva> cargarDatosReservas() {
        try {
            ArrayList<Reserva> listaReservas = hotelModelo.obtenerListaReservas();
            reservaData.setAll(listaReservas);
            return reservaData;
        } catch (ExeptionHotel | SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar las reservas: " + e.getMessage());
            return FXCollections.observableArrayList();
        }
    }

    @FXML
    private void botonNuevaReserva() {
        Reserva nuevaReserva = new Reserva();  // Crea una instancia vacía
        nuevaReserva.setDniCliente(dniClienteSeleccionado);  // Asocia el cliente seleccionado
        boolean okClicked = main.pantallaEditarCrearReserva(nuevaReserva);
        System.out.println("botonNuevaReserva: " + nuevaReserva);
        if (okClicked) {
            try {
                hotelModelo.anadirReserva(nuevaReserva);
                reservaData.add(nuevaReserva);
            } catch (ExeptionHotel e) {
                mostrarAlerta("Error", "No se pudo añadir la reserva: " + e.getMessage());
            }
        }
    }

    @FXML
    private void botonEditarReserva() {
        Reserva seleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            boolean okClicked = main.pantallaEditarCrearReserva(seleccionada);
            if (okClicked) {
                try {
                    hotelModelo.editarReserva(seleccionada);
                    mostrarDatosReserva(seleccionada);
                } catch (ExeptionHotel e) {
                    mostrarAlerta("Error", "No se pudo actualizar la reserva: " + e.getMessage());
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione una reserva para editar.");
        }
    }

    @FXML
    private void botonEliminarReserva() {
        Reserva seleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                hotelModelo.eliminarReserva(seleccionada.getIdReserva());
                reservaData.remove(seleccionada);
            } catch (ExeptionHotel e) {
                mostrarAlerta("Error", "No se pudo eliminar la reserva: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Error", "Seleccione una reserva para eliminar.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
