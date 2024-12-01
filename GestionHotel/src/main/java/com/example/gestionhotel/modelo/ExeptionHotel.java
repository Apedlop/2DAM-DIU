package com.example.gestionhotel.modelo;

public class ExeptionHotel extends RuntimeException {

    public String mensaje;

    public ExeptionHotel() {
        super();
    }

    public ExeptionHotel(String mensaje) {
        super(mensaje);
    }

    public String getMensaje() {
        return mensaje;
    }

}
