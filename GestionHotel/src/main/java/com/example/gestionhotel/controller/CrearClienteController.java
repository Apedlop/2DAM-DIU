package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.RegimenAlojamiento;
import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;
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

    private Stage stage;
    private Cliente cliente;
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
    }

    // Método para añadir el cliente a los campos
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        ponerDni.setText(cliente.getDni());
        ponerNombre.setText(cliente.getNombre());
        ponerApellido.setText(cliente.getApellido());
        ponerDireccion.setText(cliente.getDireccion());
        ponerLocalidad.setText(cliente.getLocalidad());
        ponerProvincia.setText(cliente.getProvincia());
    }

    public void setReserva(Reserva reserva) {
        // Asegúrate de que la reserva tenga todos los valores correctos
        System.out.println(reserva);
        if (reserva != null) {
            fechaLlegada.setValue(reserva.getFechaLlegada());
            fechaSalida.setValue(reserva.getFechaSalida());
            tipoHabitacion.setText(reserva.getTipoHabitacion().toString());
            numHabitaciones.setText(String.valueOf(reserva.getNumeroHabitaciones()));
            fumador.setSelected(reserva.isFumador());

            // Asignar régimen de alojamiento
            switch (reserva.getRegimenAlojamiento()) {
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

            // Buscar cliente por DNI y asignar
            Cliente clienteReserva = hotelModelo.buscarDNI(reserva.getDniCliente());
            setCliente(clienteReserva);
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
        if (dniTexto.isEmpty() || !dniTexto.matches("\\d{8}[A-Za-z]")) {
            mensajeError += "DNI no válido. Debe tener 8 dígitos seguidos de una letra.\n";
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
        // Validación de los campos del cliente
        if (!isValido()) {
            return; // Si los datos no son válidos, no seguimos con el proceso
        }

        // Validación de fechas de llegada y salida
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

        // Validación del número de habitaciones
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

        // Validación del tipo de habitación
        TipoHabitacion tipoHab = null;
        try {
            tipoHab = TipoHabitacion.valueOf(tipoHabitacion.getText());
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", "Tipo de habitación no válido.");
            return;
        }

        // Validación del régimen de alojamiento
        RegimenAlojamiento regimen = obtenerRegimenSeleccionado();
        if (regimen == null) {
            mostrarAlerta("Error", "Debe seleccionar un régimen de alojamiento.");
            return;
        }

        // Si todas las validaciones son correctas, se crea el cliente y la reserva
        try {
            // Verifica que los campos del cliente no estén vacíos antes de crear el objeto Cliente
            if (ponerDni.getText().trim().isEmpty() || ponerNombre.getText().trim().isEmpty() ||
                    ponerApellido.getText().trim().isEmpty() || ponerDireccion.getText().trim().isEmpty() ||
                    ponerLocalidad.getText().trim().isEmpty() || ponerProvincia.getText().trim().isEmpty()) {
                mostrarAlerta("Error", "Todos los campos del cliente son obligatorios.");
                return;
            }

            // Crear el cliente con los datos del formulario
            Cliente nuevoCliente = new Cliente(
                    ponerDni.getText().trim(),
                    ponerNombre.getText().trim(),
                    ponerApellido.getText().trim(),
                    ponerDireccion.getText().trim(),
                    ponerLocalidad.getText().trim(),
                    ponerProvincia.getText().trim()
            );

            // Crear la reserva asociada con los datos del formulario
            boolean esFumador = fumador.isSelected();
            Reserva nuevaReserva = new Reserva(0, nuevoCliente.getDni(), llegada, salida, numHab, tipoHab, esFumador, regimen);

            hotelModelo.anadirCliente(nuevoCliente);
            hotelModelo.anadirReserva(nuevaReserva);

            // Asegúrate de que no se guarden valores nulos
            if (nuevoCliente.getDni() == null || nuevaReserva.getTipoHabitacion() == null) {
                mostrarAlerta("Error", "Los datos del cliente y la reserva no son válidos.");
                return;
            }

            // Guardar el cliente y la reserva en el modelo
//            hotelModelo.anadirCliente(nuevoCliente);
//            hotelModelo.anadirReserva(nuevaReserva);

            // Si todo se guarda correctamente, cerramos la ventana
            okClicked = true;
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Hubo un problema al guardar los datos.");
        }
    }

    @FXML
    private void botonCancelar() {
        stage.close();
    }
}
