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
    public EditarReservaController() {}

    // Métodos de configuración

    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Establecer el DNI del cliente seleccionado
    public void setDniClienteSeleccionado(String dni) {
        this.dniClienteSeleccionado = dni;
        System.out.println("DNI Cliente: " + dniClienteSeleccionado);
    }

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

    // Método para guardar la reserva
    @FXML
    private void BotonAceptar() {
        // Validación de fechas
        LocalDate llegada = fechaLlegada.getValue();
        LocalDate salida = fechaSalida.getValue();
        if (llegada == null || salida == null) {
            mostrarAlerta("Error", "Las fechas de llegada y salida son obligatorias.");
            return;
        }
        if (llegada.isAfter(salida)) {
            mostrarAlerta("Error", "La fecha de salida no puede ser anterior a la de llegada.");
            return;
        }

        // Validación del régimen de alojamiento
        RegimenAlojamiento regimen = obtenerRegimenSeleccionado();
        if (regimen == null) {
            mostrarAlerta("Error", "Debe seleccionar un régimen de alojamiento.");
            return;
        }

        try {
            // Tipo de habitación
            TipoHabitacion tipoHab = TipoHabitacion.valueOf(tipoHabitacion.getText());

            // Crear la reserva
            Reserva nuevaReserva = new Reserva(0, dniClienteSeleccionado, llegada, salida, 1, tipoHab, fumador.isSelected(), regimen);

            // Guardar en la base de datos
            hotelModelo.anadirReserva(nuevaReserva);

            okClicked = true;
            stage.close();
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "Tipo de habitación no válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo crear la reserva: " + e.getMessage());
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
