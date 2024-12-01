package com.example.gestionhotel.modelo.tablas;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Reserva {

    private final IntegerProperty id_reserva;
    private final StringProperty dni_cliente;
    private final ObjectProperty<LocalDate> fecha_llegada;
    private final ObjectProperty<LocalDate> fecha_salida;
    private final IntegerProperty numero_habitaciones;
    private final ObjectProperty<TipoHabitacion> tipo_habitacion;
    private final BooleanProperty fumador;
    private final ObjectProperty<RegimenAlojamiento> regimen_alojamiento;

    // Constructor por defecto
    public Reserva() {
        this(0, null, null, null, 0, null, false, null);
    }

    // Constructor con algunos datos iniciales
    public Reserva(int id_reserva, String dni_cliente, LocalDate fecha_llegada, LocalDate fecha_salida, int numero_habitaciones, TipoHabitacion tipo_habitacion, boolean fumador, RegimenAlojamiento regimen_alojamiento) {
        this.id_reserva = new SimpleIntegerProperty(id_reserva);
        this.numero_habitaciones = new SimpleIntegerProperty(numero_habitaciones);
        this.dni_cliente = new SimpleStringProperty(dni_cliente);
        this.fecha_llegada = new SimpleObjectProperty<LocalDate>(fecha_llegada);
        this.fecha_salida = new SimpleObjectProperty<LocalDate>(fecha_salida);
        this.tipo_habitacion = new SimpleObjectProperty<TipoHabitacion>(tipo_habitacion);
        this.fumador = new SimpleBooleanProperty(fumador);
        this.regimen_alojamiento = new SimpleObjectProperty<RegimenAlojamiento>(regimen_alojamiento);
    }

    // Getters y Setters para id_reserva
    public Integer getIdReserva() {
        return id_reserva.get();
    }

    public void setIdReserva(int id_reserva) {
        this.id_reserva.set(id_reserva);
    }

    public IntegerProperty idReservaProperty() {
        return id_reserva;
    }

    // Getters y Setters para numero_habitaciones
    public int getNumeroHabitaciones() {
        return numero_habitaciones.get();
    }

    public void setNumeroHabitaciones(int numero_habitaciones) {
        this.numero_habitaciones.set(numero_habitaciones);
    }

    public IntegerProperty numeroHabitacionesProperty() {
        return numero_habitaciones;
    }

    // Getters y Setters para dni_cliente
    public String getDniCliente() {
        return dni_cliente.get();
    }

    public void setDniCliente(String dni_cliente) {
        this.dni_cliente.set(dni_cliente);
    }

    public StringProperty dniClienteProperty() {
        return dni_cliente;
    }

    // Getters y Setters para fecha_llegada
    public LocalDate getFechaLlegada() {
        return fecha_llegada.get();
    }

    public void setFechaLlegada(LocalDate fecha_llegada) {
        this.fecha_llegada.set(fecha_llegada);
    }

    public ObjectProperty<LocalDate> fechaLlegadaProperty() {
        return fecha_llegada;
    }

    // Getters y Setters para fecha_salida
    public LocalDate getFechaSalida() {
        return fecha_salida.get();
    }

    public void setFechaSalida(LocalDate fecha_salida) {
        this.fecha_salida.set(fecha_salida);
    }

    public ObjectProperty<LocalDate> fechaSalidaProperty() {
        return fecha_salida;
    }

    // Getters y Setters para tipo_habitacion
    public TipoHabitacion getTipoHabitacion() {
        return tipo_habitacion.get();
    }

    public void setTipoHabitacion(TipoHabitacion tipo_habitacion) {
        this.tipo_habitacion.set(tipo_habitacion);
    }

    public ObjectProperty<TipoHabitacion> tipoHabitacionProperty() {
        return tipo_habitacion;
    }

    // Getters y Setters para fumador
    public boolean isFumador() {
        return fumador.get();
    }

    public void setFumador(boolean fumador) {
        this.fumador.set(fumador);
    }

    public BooleanProperty fumadorProperty() {
        return fumador;
    }

    // Getters y Setters para regimen_alojamiento
    public RegimenAlojamiento getRegimenAlojamiento() {
        return regimen_alojamiento.get();
    }

    public void setRegimenAlojamiento(RegimenAlojamiento regimen_alojamiento) {
        this.regimen_alojamiento.set(regimen_alojamiento);
    }

    public ObjectProperty<RegimenAlojamiento> regimenAlojamientoProperty() {
        return regimen_alojamiento;
    }
}
