package com.example.gestionhotel.modelo.repository;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.tablas.ReservaVO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservaRepository {

    // Método para convertir la lista de Clientes
    ArrayList<ReservaVO> obtenerListaReservas() throws ExeptionHotel, SQLException;

    // Método para añadir una Reserva
    void addReserva(ReservaVO reserva) throws ExeptionHotel;

    // Método para elimianr una Reserva
    void deleteReserva(ReservaVO reserva) throws ExeptionHotel;

    // Método para editar una Reserva
    void editReserva(ReservaVO reserva) throws ExeptionHotel;

    // Método para buscar reserva de un cliente
    ReservaVO buscarReserva(String dni) throws ExeptionHotel;

}
