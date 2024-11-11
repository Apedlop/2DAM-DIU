package com.example.gestionhotel.modelo.tablas;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private final StringProperty dni;
    private final StringProperty nombre;
    private final StringProperty apellido;
    private final StringProperty direccion;
    private final StringProperty localidad;
    private final StringProperty provincia;

    // Lista de reservas del cliente
    private final List<Reserva> reservas;

    // Constructor por defecto
    public Cliente() {
        this(null, null, null, null, null, null);
    }

    // Constructor con datos iniciales
    public Cliente(String dni, String nombre, String apellido, String direccion, String localidad, String provincia) {
        this.dni = new SimpleStringProperty(dni);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.direccion = new SimpleStringProperty(direccion);
        this.localidad = new SimpleStringProperty(localidad);
        this.provincia = new SimpleStringProperty(provincia);
        this.reservas = new ArrayList<>();
    }

    /*
    * MÉTODOS PARA GESTIONAR LAS RESERVAS
    */

    // Añadir una reserva
    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    // Eliminar una reserva
    public void eliminarReserva(Reserva reserva) {
        reservas.remove(reserva);
    }

    // Obtener la lista de reservas
    public List<Reserva> getReservas() {
        return reservas;
    }

    // Obtener el número de reservas
    public int getNumeroReservas() {
        return reservas.size();
    }

    // Mostrar todas las reservas del cliente
    public void mostrarReservas() {
        for (Reserva reserva : reservas) {
            System.out.println("ID Reserva: " + reserva.getIdReserva());
            System.out.println("DNI Cliente: " + reserva.getDniCliente());
            System.out.println("Fecha Llegada: " + reserva.getFechaLlegada());
            System.out.println("Fecha Salida: " + reserva.getFechaSalida());
            System.out.println();
        }
    }

    /*
    * RESTO DE MÉTODOS
    */

    // Getters y Setters para nombre
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    // Getters y Setters para apellido
    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    // Getters y Setters para direccion
    public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    // Getters y Setters para localidad
    public String getLocalidad() {
        return localidad.get();
    }

    public void setLocalidad(String localidad) {
        this.localidad.set(localidad);
    }

    public StringProperty localidadProperty() {
        return localidad;
    }

    // Getters y Setters para provincia
    public String getProvincia() {
        return provincia.get();
    }

    public void setProvincia(String provincia) {
        this.provincia.set(provincia);
    }

    public StringProperty provinciaProperty() {
        return provincia;
    }

    // Getters y Setters para dni
    public String getDni() {
        return dni.get();
    }

    public void setDni(String dni) {
        this.dni.set(dni);
    }

    public StringProperty dniProperty() {
        return dni;
    }
}
