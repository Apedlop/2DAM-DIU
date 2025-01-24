package com.example.gestionhotel.modelo.repository;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.tablas.ReservaVO;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservaRepository {

    // Método para convertir la lista de Clientes
    ArrayList<ReservaVO> obtenerListaReservas() throws ExeptionHotel, SQLException;

    public int contarReservasPorTipoHabitacion(TipoHabitacion tipoHabitacion) throws ExeptionHotel;

    public void crearRerserva(ReservaVO reservaVO) throws ExeptionHotel;

    public void eliminarReserva(Integer idReserva) throws ExeptionHotel;

    public void editarReserva(ReservaVO reservaVO) throws ExeptionHotel;

    public int ultimoIdReserva() throws ExeptionHotel;

    public ArrayList<ReservaVO> obtenerReservasCliente(String dniCliente) throws ExeptionHotel;

}

