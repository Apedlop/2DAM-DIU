package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;
import com.example.gestionhotel.modelo.tablas.RegimenAlojamiento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditarReservaController {

    // FXML Components
    @FXML
    private DatePicker fechaLlegada;
    @FXML
    private DatePicker fechaSalida;
    @FXML
    private SplitMenuButton tipoHabitacion;
    @FXML
    private SplitMenuButton numHabitaciones;
    @FXML
    private CheckBox fumador;
    @FXML
    private RadioButton alojamientoDesayuno;
    @FXML
    private RadioButton mediaPension;
    @FXML
    private RadioButton pensionCompleta;

    // Atributos de la clase
    private Stage stage;
    private String dniClienteSeleccionado;  // DNI del cliente previamente seleccionado
    private HotelModelo hotelModelo;
    private Reserva reserva;
    private boolean okClicked = false;

    // Constructor vacío por defecto
    public EditarReservaController() {
    }

    // Métodos de configuración

    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para establecer el DNI del cliente seleccionado
    public void setDniClienteSeleccionado(String dni) {
        this.dniClienteSeleccionado = dni;
        System.out.println("ggggggg" + dniClienteSeleccionado);
    }

    // Método para obtener el DNI del cliente seleccionado
    public String getDniClienteSeleccionado() {
        return dniClienteSeleccionado;
    }

    // Método de inicialización
    @FXML
    private void initialize() {
        configurarRegimen();
        configurarTipoHabitacion();
        configurarNumeroHabitaciones();
        configurarFechas();
    }

    // Configura los RadioButtons para el régimen de alojamiento
    private void configurarRegimen() {
        ToggleGroup grupoRegimen = new ToggleGroup();
        alojamientoDesayuno.setToggleGroup(grupoRegimen);
        mediaPension.setToggleGroup(grupoRegimen);
        pensionCompleta.setToggleGroup(grupoRegimen);
    }

    // Configura el tipo de habitación (ComboBox)
    private void configurarTipoHabitacion() {
        for (TipoHabitacion tipo : TipoHabitacion.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(event -> tipoHabitacion.setText(item.getText()));
            tipoHabitacion.getItems().add(item);
        }
    }

    // Configura el número de habitaciones (Solo una)
    private void configurarNumeroHabitaciones() {
        MenuItem item = new MenuItem("1");
        item.setOnAction(event -> numHabitaciones.setText(item.getText()));
        numHabitaciones.getItems().add(item);
        numHabitaciones.setText("1");
        numHabitaciones.setDisable(true);  // Bloqueado en una habitación
    }

    // Configura las fechas válidas para la llegada y salida
    private void configurarFechas() {
        fechaLlegada.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        });
        fechaSalida.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        });
    }

    // Método para validar todos los campos
    private boolean isValido() {
        // Validar fechas (llegada y salida)
        LocalDate llegada = fechaLlegada.getValue();
        LocalDate salida = fechaSalida.getValue();

        if (llegada == null || salida == null) {
            mostrarAlerta("Error", "Las fechas de llegada y salida son obligatorias.");
            return false;
        }

        if (llegada.isAfter(salida)) {
            mostrarAlerta("Error", "La fecha de salida no puede ser anterior a la de llegada.");
            return false;
        }

        // Validar tipo de habitación
        if (tipoHabitacion.getText() == null || tipoHabitacion.getText().isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un tipo de habitación.");
            return false;
        }

        // Validar régimen de alojamiento
        if (!alojamientoDesayuno.isSelected() && !mediaPension.isSelected() && !pensionCompleta.isSelected()) {
            mostrarAlerta("Error", "Debe seleccionar un régimen de alojamiento.");
            return false;
        }

        // Validar DNI del cliente (si se requiere)
        if (dniClienteSeleccionado == null || dniClienteSeleccionado.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un cliente.");
            return false;
        }

        // Si todo es válido
        return true;
    }

    // Método para guardar la reserva
    @FXML
    private void BotonAceptar() {
        // Verificar si la entrada es válida
        if (!isValido()) {
            return;  // No continuar si los datos no son válidos
        }

        try {
            System.out.println("Estoy en boton aceptar");
            // Crear una nueva reserva si no existe una reserva para editar

            System.out.println("buena" + reserva);
            System.out.println(dniClienteSeleccionado + " boton aceptar");// Crear una nueva reserva
            reserva.setDniCliente(dniClienteSeleccionado);  // Establecer el DNI del cliente

            // Actualizar los atributos de la reserva (tanto para creación como edición)
            reserva.setFechaLlegada(fechaLlegada.getValue());
            reserva.setFechaSalida(fechaSalida.getValue());
            reserva.setTipoHabitacion(TipoHabitacion.valueOf(tipoHabitacion.getText()));
            reserva.setFumador(fumador.isSelected());
            reserva.setRegimenAlojamiento(obtenerRegimenSeleccionado());
            reserva.setNumeroHabitaciones(1);  // Siempre será 1 habitación
            System.out.println("EStoy en boton acetpae");

            okClicked = true;
            stage.close();
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "Tipo de habitación no válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo procesar la reserva: " + e.getMessage());
        }
    }

    // Método para cancelar la operación
    @FXML
    private void botonCancelar() {
        stage.close();
    }

    // Mostrar alertas de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Obtener el régimen de alojamiento seleccionado
    private RegimenAlojamiento obtenerRegimenSeleccionado() {
        if (alojamientoDesayuno.isSelected()) return RegimenAlojamiento.desayuno;
        if (mediaPension.isSelected()) return RegimenAlojamiento.mediaPension;
        if (pensionCompleta.isSelected()) return RegimenAlojamiento.pensionCompleta;
        return null;
    }

    // Verificar si la reserva fue confirmada
    public boolean isOkClicked() {
        return okClicked;
    }

    // Establecer los valores de la reserva a editar
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;

        // Establecer fechas
        fechaLlegada.setValue(reserva.getFechaLlegada());
        fechaSalida.setValue(reserva.getFechaSalida());

        // Verificar y establecer tipo de habitación
        TipoHabitacion tipoHab = reserva.getTipoHabitacion();
        if (tipoHab != null) {
            tipoHabitacion.setText(tipoHab.toString());
        } else {
            tipoHabitacion.setText("No especificado");
        }

        // Número de habitaciones
        numHabitaciones.setText(String.valueOf(reserva.getNumeroHabitaciones()));

        // Fumador
        fumador.setSelected(reserva.isFumador());

        // Verificar y establecer régimen de alojamiento
        RegimenAlojamiento regimen = reserva.getRegimenAlojamiento();
        if (regimen != null) {
            switch (regimen) {
                case desayuno:
                    alojamientoDesayuno.setSelected(true);
                    break;
                case mediaPension:
                    mediaPension.setSelected(true);
                    break;
                case pensionCompleta:
                    pensionCompleta.setSelected(true);
                    break;
                default:
                    break;
            }
        } else {
            alojamientoDesayuno.setSelected(false);
            mediaPension.setSelected(false);
            pensionCompleta.setSelected(false);
        }
    }
}
