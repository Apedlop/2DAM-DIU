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
import java.util.Comparator;

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
    private ObservableList<Reserva> reservaData = FXCollections.observableArrayList();
    private String dniClienteSelecc;
    private Stage stage;

    public void setHotelModelo(HotelModelo hotelModelo) throws ExeptionHotel {
        this.hotelModelo = hotelModelo;
        cargarDatosReservas();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    public void initialize() {
        columnaCodigo.setCellValueFactory(cellData -> cellData.getValue().dniClienteProperty());
        columnaFechaEntrada.setCellValueFactory(cellData -> cellData.getValue().fechaLlegadaProperty().asString());
        tablaReservas.setItems(reservaData);

        tablaReservas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mostrarDatosReserva(newValue);
        });
    }

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
        }
    }

    public void setDniClienteSeleccionado(String dniCliente) {
        this.dniClienteSelecc = dniCliente;
        cargarReservasPorCliente(dniCliente);
    }

    // Método para obtener el DNI
    public String getDniClienteSeleccionado() {
        return dniClienteSelecc;
    }

    private void cargarReservasPorCliente(String dniCliente) {
        try {
            ArrayList<Reserva> listaReservasCliente = hotelModelo.buscarReserva(dniCliente);
            reservaData.setAll(listaReservasCliente);
        } catch (ExeptionHotel e) {
            mostrarAlerta("Error", "No se pudieron cargar las reservas: " + e.getMessage());
        }
    }

    private void cargarDatosReservas() {
        try {
            ArrayList<Reserva> listaReservas = hotelModelo.obtenerListaReservas();
            reservaData.setAll(listaReservas);
            reservaData.sort(Comparator.comparing(Reserva::getFechaLlegada));
        } catch (ExeptionHotel | SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar las reservas: " + e.getMessage());
        }
    }

    @FXML
    public void botonNuevaReserva() {
        Reserva nuevaReserva = new Reserva();
        boolean okClicked = main.pantallaCrearReserva(nuevaReserva);
        if (okClicked) {
            try {
                hotelModelo.anadirReserva(nuevaReserva);
                main.getReservaData().add(nuevaReserva);
                cargarDatosReservas();
            } catch (ExeptionHotel e) {
                mostrarAlerta("Error", "No se pudo añadir la reserva: " + e.getMessage());
            }
        }
    }

    @FXML
    public void botonEditarReserva() {
        Reserva seleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            boolean okClicked = main.pantallaEditarReserva(seleccionada);
            if (okClicked) {
                try {
                    hotelModelo.editarReserva(seleccionada);
                    mostrarDatosReserva(seleccionada);
                    cargarDatosReservas();
                } catch (ExeptionHotel e) {
                    mostrarAlerta("Error", "No se pudo actualizar la reserva: " + e.getMessage());
                }
            }
        } else {
            mostrarAlerta("Error", "Seleccione una reserva para editar.");
        }
    }

    @FXML
    public void botonEliminarReserva() {
        Reserva seleccionada = tablaReservas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            try {
                hotelModelo.eliminarReserva(seleccionada.getIdReserva()); // Llamada segura
                reservaData.remove(seleccionada); // Actualiza la tabla
            } catch (ExeptionHotel e) {
                mostrarAlerta("Error", "No se pudo eliminar la reserva: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Error", "Seleccione una reserva para eliminar.");
        }
    }

    private RegimenAlojamiento obtenerRegimenSeleccionado() {
        if (alojamientoDesayuno.isSelected()) return RegimenAlojamiento.desayuno;
        if (mediaPension.isSelected()) return RegimenAlojamiento.mediaPension;
        if (pensionCompleta.isSelected()) return RegimenAlojamiento.pensionCompleta;
        return null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
