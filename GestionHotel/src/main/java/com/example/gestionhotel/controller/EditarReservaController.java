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

    private Stage stage;
    private String dniClienteSeleccionado;  // DNI del cliente previamente seleccionado
    private HotelModelo hotelModelo;
    private Reserva reserva;
    private boolean okClicked = false;

    // Construtor vacío por defecto
    public EditarReservaController() {

    }

    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para establecer el DNI del cliente seleccionado
    public void setDniClienteSeleccionado(String dni) {
        this.dniClienteSeleccionado = dni;
        System.out.println("buenass" + dni);
        // Aquí puedes usar el DNI para realizar alguna acción o actualizar la interfaz
        System.out.println("DNI Cliente: " + dniClienteSeleccionado);
    }

    public String getDniClienteSeleccionado() {
        return dniClienteSeleccionado;
    }

    @FXML
    private void initialize() {
        // Configuración de RadioButton para el régimen
        ToggleGroup grupoRegimen = new ToggleGroup();
        alojamientoDesayuno.setToggleGroup(grupoRegimen);
        mediaPension.setToggleGroup(grupoRegimen);
        pensionCompleta.setToggleGroup(grupoRegimen);

        // Configuración de tipos de habitación
        for (TipoHabitacion tipo : TipoHabitacion.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(event -> tipoHabitacion.setText(item.getText()));
            tipoHabitacion.getItems().add(item);
        }

        // Solo una habitación permitida
        MenuItem item = new MenuItem("1");
        item.setOnAction(event -> numHabitaciones.setText(item.getText()));
        numHabitaciones.getItems().add(item);
        numHabitaciones.setText("1");
        numHabitaciones.setDisable(true);  // Bloqueado en una habitación

        // Configuración de fechas válidas
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
            Reserva nuevaReserva = new Reserva(
                    0, dniClienteSeleccionado, llegada, salida, 1, tipoHab, fumador.isSelected(), regimen
            );

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

    @FXML
    private void botonCancelar() {
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private RegimenAlojamiento obtenerRegimenSeleccionado() {
        if (alojamientoDesayuno.isSelected()) return RegimenAlojamiento.desayuno;
        if (mediaPension.isSelected()) return RegimenAlojamiento.mediaPension;
        if (pensionCompleta.isSelected()) return RegimenAlojamiento.pensionCompleta;
        return null;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

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
                    // No hacer nada si no es ninguno de estos
                    break;
            }
        } else {
            // Aquí puedes manejar el caso de régimen no especificado si es necesario
            alojamientoDesayuno.setSelected(false);
            mediaPension.setSelected(false);
            pensionCompleta.setSelected(false);
        }
    }

}
