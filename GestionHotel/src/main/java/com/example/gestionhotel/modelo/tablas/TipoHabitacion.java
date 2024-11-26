package com.example.gestionhotel.modelo.tablas;

public enum TipoHabitacion {
    DOBLE_USO_INVIDIDUAL, DOBLE, JUNIOR_SUITE, SUITE;

    // Si quieres un nombre más legible
    @Override
    public String toString() {
        switch (this) {
            case DOBLE_USO_INVIDIDUAL:
                return "Individual";
            case DOBLE:
                return "Doble";
            case JUNIOR_SUITE:
                return "Junior Suite";
            case SUITE:
                return "Suite";
            default:
                return "Desconocido";
        }
    }
}
