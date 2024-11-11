package com.example.gestionhotel.modelo.tablas;

import java.time.LocalDate;

public class ReservaVO {

    Integer id_reserva;
    String dni_cliente;
    LocalDate fecha_llegada, fecha_salida;
    int numero_habitaciones;
    TipoHabitacion tipo_habitacion;
    boolean fumador;
    RegimenAlojamiento regimen_alojamiento;

    // Constructor vacío
    public ReservaVO() {

    }

    public ReservaVO(Integer id_reserva, String dni_cliente, LocalDate fecha_llegada, LocalDate fecha_salida, int numero_habitaciones, TipoHabitacion tipo_habitacion, boolean fumador, RegimenAlojamiento regimen_alojamiento) {
        this.id_reserva = id_reserva;
        this.dni_cliente = dni_cliente;
        this.fecha_llegada = fecha_llegada;
        this.fecha_salida = fecha_salida;
        this.numero_habitaciones = numero_habitaciones;
        this.tipo_habitacion = tipo_habitacion;
        this.fumador = fumador;
        this.regimen_alojamiento = regimen_alojamiento;
    }

    public Integer getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(Integer id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public LocalDate getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(LocalDate fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public LocalDate getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(LocalDate fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public int getNumero_habitaciones() {
        return numero_habitaciones;
    }

    public void setNumero_habitaciones(int numero_habitaciones) {
        this.numero_habitaciones = numero_habitaciones;
    }

    public TipoHabitacion getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(TipoHabitacion tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public boolean isFumador() {
        return fumador;
    }

    public void setFumador(boolean fumador) {
        this.fumador = fumador;
    }

    public RegimenAlojamiento getRegimen_alojamiento() {
        return regimen_alojamiento;
    }

    public void setRegimen_alojamiento(RegimenAlojamiento regimen_alojamiento) {
        this.regimen_alojamiento = regimen_alojamiento;
    }
}
