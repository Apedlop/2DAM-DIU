package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CrearClienteController {

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
    @FXML
    private Label alertaFumador;

    private Stage stage;
    private Cliente cliente;
    private Reserva reserva;
    private HotelModelo hotelModelo;
    private boolean okClicked = false;

    // Constructor por defecto vacío
    public CrearClienteController() {
    }

    // Inyectamos HotelModelo
    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    // Inyectamos Stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        // Configuración de RadioButton
        ToggleGroup grupoRegimen = new ToggleGroup();
        alojamientoDesayuno.setToggleGroup(grupoRegimen);
        mediaPension.setToggleGroup(grupoRegimen);
        pensionCompleta.setToggleGroup(grupoRegimen);

        // Configuración de SplitMenuButton para tipoHabitacion
        for (TipoHabitacion tipo : TipoHabitacion.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(event -> {
                tipoHabitacion.setText(item.getText());
            });
            tipoHabitacion.getItems().add(item);
        }

        tipoHabitacion.setText("-- Seleccionar --");  // Texto predeterminado
        tipoHabitacion.requestLayout(); // Fuerza el refresco visual

        // Configuración de SplitMenuButton para numHabitaciones
        MenuItem item = new MenuItem("1");
        item.setOnAction(event -> {
            numHabitaciones.setText(item.getText());
        });
        numHabitaciones.getItems().add(item);
        numHabitaciones.setText("1"); // Valor predeterminado
        numHabitaciones.setDisable(true); // Desactiva el menú, ya que solo hay una opción

        // Limitar la selección de fechas en fechaLlegada y fechaSalida
        fechaLlegada.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // No permitir fechas pasadas
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        });
        fechaSalida.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                // No permitir fechas pasadas
                setDisable(empty || item.isBefore(LocalDate.now()));
            }
        });

        // Listener para CheckBox fumador
        fumador.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // Si fumador es seleccionado (true)
                alertaFumador.setText("En virtud de la ley de sanidad, \nse informa a los clientes de que solo \npodrán fumar en las habitaciones \nreservadas para tal fin.");
                alertaFumador.setTextFill(javafx.scene.paint.Color.RED); // Cambiar el color del texto a rojo
            } else { // Si fumador no está seleccionado (false)
                alertaFumador.setText(""); // Limpiar el texto del label
            }
        });
    }


    // Método para añadir tanto el cliente como la reserva a los campos
    public void setClienteYReserva(Cliente cliente, Reserva reserva) {
        if (cliente != null) {
            this.cliente = cliente;
            ponerDni.setText(cliente.getDni());
            ponerNombre.setText(cliente.getNombre());
            ponerApellido.setText(cliente.getApellido());
            ponerDireccion.setText(cliente.getDireccion());
            ponerLocalidad.setText(cliente.getLocalidad());
            ponerProvincia.setText(cliente.getProvincia());
        } else {
            // Manejar el caso cuando el cliente es nulo, por ejemplo, mostrar un mensaje
        }

        if (reserva != null) {
            this.reserva = reserva;
            fechaLlegada.setValue(reserva.getFechaLlegada());
            fechaSalida.setValue(reserva.getFechaSalida());

            // Establecer el tipo de habitación
            TipoHabitacion tipoHab = reserva.getTipoHabitacion();
            if (tipoHab != null) {
                tipoHabitacion.setText(tipoHab.toString());
            } else {
                tipoHabitacion.setText("-- Seleccionar --");
            }

            fumador.setSelected(reserva.isFumador());
            numHabitaciones.setText("1");

            // Establecer el régimen de alojamiento
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
                }
            }
        }
    }



    // Retorna "true" si el usuario hace click en ACEPTAR
    public boolean isOkClicked() {
        return okClicked;
    }

    // Valida la entrada del usuario en los campos de texto, para que no deje ninguno vacío
    private boolean isValido() {
        String mensajeError = "";

        // Validar DNI
        String dniTexto = ponerDni.getText().trim();

        // Validación de formato y letra
        if (dniTexto.isEmpty() || !dniTexto.matches("\\d{8}[A-Za-z]") ||
                dniTexto.charAt(8) != "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(dniTexto.substring(0, 8)) % 23)) {
            mensajeError += "DNI no válido.\n";
        }

        // Validar nombre, apellido, dirección, localidad, y provincia
        if (ponerNombre.getText().trim().isEmpty()) mensajeError += "Nombre no válido\n";
        if (ponerApellido.getText().trim().isEmpty()) mensajeError += "Apellido no válido\n";
        if (ponerDireccion.getText().trim().isEmpty()) mensajeError += "Dirección no válida\n";
        if (ponerLocalidad.getText().trim().isEmpty()) mensajeError += "Localidad no válida\n";
        if (ponerProvincia.getText().trim().isEmpty()) mensajeError += "Provincia no válida\n";

        // Si hay errores, mostramos la alerta
        if (!mensajeError.isEmpty()) {
            mostrarAlerta("Campos Inválidos", mensajeError);
            return false;
        }

        return true;
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
        return null; // Si no se selecciona ninguno
    }

    @FXML
    private void botonOk() {
        if (!isValido()) {
            return;
        }

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

        // Validar número de habitaciones
        int numHab;
        try {
            numHab = Integer.parseInt(numHabitaciones.getText());
            if (numHab != 1) {
                mostrarAlerta("Error", "Solo se puede reservar 1 habitación.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Número de habitaciones no válido.");
            return;
        }

        TipoHabitacion tipoHab = null;
        try {
            tipoHab = TipoHabitacion.valueOf(tipoHabitacion.getText());
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "Tipo de habitación no válido.");
            return;
        }

        RegimenAlojamiento regimen = obtenerRegimenSeleccionado();
        if (regimen == null) {
            mostrarAlerta("Error", "Debe seleccionar un régimen de alojamiento.");
            return;
        }

        // Actualizar cliente y reserva
        cliente.setDni(ponerDni.getText().trim());
        cliente.setNombre(ponerNombre.getText().trim());
        cliente.setApellido(ponerApellido.getText().trim());
        cliente.setDireccion(ponerDireccion.getText().trim());
        cliente.setLocalidad(ponerLocalidad.getText().trim());
        cliente.setProvincia(ponerProvincia.getText().trim());

        reserva.setFechaLlegada(llegada);
        reserva.setFechaSalida(salida);
        reserva.setTipoHabitacion(tipoHab);
        reserva.setNumeroHabitaciones(numHab);
        reserva.setFumador(fumador.isSelected());
        reserva.setRegimenAlojamiento(regimen);
        reserva.setDniCliente(cliente.getDni());

        // Confirmar que todo está bien y cerrar la ventana
        okClicked = true;
        stage.close();
    }


    @FXML
    private void botonCancelar() {
        stage.close();
    }
}
