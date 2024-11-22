package com.example.ejemplo_ex1ev1.modelo;

public class ExceptionCatalogo extends RuntimeException {

    public String mensaje;

    public ExceptionCatalogo() {

    }

    public ExceptionCatalogo(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

}
