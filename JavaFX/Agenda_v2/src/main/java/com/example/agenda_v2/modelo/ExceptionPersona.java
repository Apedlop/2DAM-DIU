package com.example.agenda_v2.modelo;

public class ExceptionPersona extends RuntimeException {

    private String mensaje;

    public ExceptionPersona() {
    }

    public ExceptionPersona(String ms) {
        this.mensaje = ms;
    }

    public String imprimirMensaje() {
        return this.mensaje;
    }
}
