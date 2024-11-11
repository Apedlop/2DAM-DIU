package com.example.gestionhotel.modelo.repository;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.tablas.ReservaVO;

import java.util.ArrayList;

public interface ReservaRepository {

    // Método para conseguir la lista de Reservas
    ArrayList<ReservaVO> obtenerListaReservas() throws ExeptionHotel;

    // Método para añadir una Reserva
    void addReserva(ReservaVO reserva) throws ExeptionHotel;

    // Método para elimianr una Reserva
    void deleteReserva(ReservaVO reserva) throws ExeptionHotel;

    // Método para editar una Reserva
    void editReserva(ReservaVO reserva) throws ExeptionHotel;

}
