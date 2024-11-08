package com.example.gestionhotel.modelo;

public class ExeptionHotel extends RuntimeException {

    public String mensaje;

    public ExeptionHotel() {}

    public ExeptionHotel(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
