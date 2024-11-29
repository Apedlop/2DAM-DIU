package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.RegimenAlojamiento;
import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class EditarReservaController {

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

    private Stage stage;
    private HotelModelo hotelModelo;
    private Reserva reserva;
    private boolean okClicked = false;

    // Constructor vacío por defecto
    public EditarReservaController() {

    }

    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para añadir la reserva y añadir a los campos
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;

        // Establecer las fechas de llegada y salida
        fechaLlegada.setValue(reserva.getFechaLlegada());
        fechaSalida.setValue(reserva.getFechaSalida());

        // Establecer el tipo de habitación
        // Asumiendo que el método getTipoHabitacion() devuelve un string con el tipo de habitación
        String tipoHabitacionSeleccionado = String.valueOf(reserva.getTipoHabitacion());
        for (MenuItem item : tipoHabitacion.getItems()) {
            if (item.getText().equals(tipoHabitacionSeleccionado)) {
                tipoHabitacion.setText(tipoHabitacionSeleccionado);
                break;
            }
        }

        // Establecer si es fumador o no
        fumador.setSelected(reserva.isFumador());

        // Establecer el régimen de alojamiento
        // Asumiendo que getRegimenAlojamiento() devuelve un string con el régimen
        String regimen = String.valueOf(reserva.getRegimenAlojamiento());
        if ("Alojamiento y desayuno".equals(regimen)) {
            alojamientoDesayuno.setSelected(true);
        } else if ("Media pensión".equals(regimen)) {
            mediaPension.setSelected(true);
        } else if ("Pensión completa".equals(regimen)) {
            pensionCompleta.setSelected(true);
        }

        // Asumiendo que el número de habitaciones es un valor dentro de los items del SplitMenuButton
        String numHabitacionesSeleccionadas = "1"; // Valor predeterminado o de la reserva
        for (MenuItem item : numHabitaciones.getItems()) {
            if (item.getText().equals(numHabitacionesSeleccionadas)) {
                numHabitaciones.setText(numHabitacionesSeleccionadas);
                break;
            }
        }
    }

    // Retorna "true" si el usuario hace click en ACEPTAR
    public boolean isOkClicked() {
        return okClicked;
    }

    // Método que se llama cuando se selecciona la fecha de llegada
    @FXML
    private void seleccionarLlegada() {
        // Lógica para manejar la fecha de llegada
        System.out.println("Fecha de llegada seleccionada: " + fechaLlegada.getValue());
    }

    // Método que se llama cuando se selecciona la fecha de salida
    @FXML
    private void seleccionarSalida() {
        // Lógica para manejar la fecha de salida
        System.out.println("Fecha de salida seleccionada: " + fechaSalida.getValue());
    }

    // Método que se llama cuando se selecciona el tipo de habitación
    @FXML
    private void seleccionarTipoHabitacion() {
        // Lógica para manejar la selección de tipo de habitación
        String selectedType = tipoHabitacion.getText();
        System.out.println("Tipo de habitación seleccionado: " + selectedType);
    }

    // Método que se llama cuando se selecciona la opción "Fumador"
    @FXML
    private void seleccionarFumador() {
        // Lógica para manejar la selección de fumador
        boolean isFumador = fumador.isSelected();
        System.out.println("¿Fumador?: " + isFumador);
    }

    // Método que se llama cuando se selecciona el régimen de alojamiento
    @FXML
    private void seleccionarRegimenAlojamiento() {
        // Lógica para manejar la selección del régimen de alojamiento
        if (alojamientoDesayuno.isSelected()) {
            System.out.println("Régimen seleccionado: Alojamiento y desayuno");
        } else if (mediaPension.isSelected()) {
            System.out.println("Régimen seleccionado: Media pensión");
        } else if (pensionCompleta.isSelected()) {
            System.out.println("Régimen seleccionado: Pensión completa");
        }
    }

    // Método que se llama cuando se presiona el botón "Aceptar"
    @FXML
    private void BotonAceptar() {
        // Actualiza la fecha de llegada y salida en la reserva
        reserva.setFechaLlegada(fechaLlegada.getValue());
        reserva.setFechaSalida(fechaSalida.getValue());

        // Obtener el tipo de habitación seleccionado desde el SplitMenuButton
        String tipoHabitacionSeleccionado = tipoHabitacion.getText();

        // Verificar si el tipo de habitación seleccionado coincide con alguna de las opciones
        for (MenuItem item : tipoHabitacion.getItems()) {
            if (item.getText().equals(tipoHabitacionSeleccionado)) {
                // Asignar el tipo de habitación seleccionado a la reserva
                reserva.setTipoHabitacion(TipoHabitacion.valueOf(tipoHabitacionSeleccionado));
                break;
            }
        }

        // Asignar el régimen de alojamiento seleccionado
        if (alojamientoDesayuno.isSelected()) {
            reserva.setRegimenAlojamiento(RegimenAlojamiento.desayuno);
        } else if (mediaPension.isSelected()) {
            reserva.setRegimenAlojamiento(RegimenAlojamiento.mediaPension);
        } else if (pensionCompleta.isSelected()) {
            reserva.setRegimenAlojamiento(RegimenAlojamiento.pensionCompleta);
        }

        // Asignar la opción de fumador
        reserva.setFumador(fumador.isSelected());

        // Asignar el número de habitaciones seleccionado
        int numHabitacionesSeleccionadas = Integer.parseInt(numHabitaciones.getText());
        reserva.setNumeroHabitaciones(numHabitacionesSeleccionadas);

        // Aquí puedes agregar más lógica según sea necesario, como cerrar la ventana o actualizar la UI
        System.out.println("Reserva actualizada con éxito.");
    }

    @FXML
    private void botonCancelar() {
        stage.close();
    }
}

