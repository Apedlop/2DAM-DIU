package com.example.gestionhotel.modelo.util;

import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.ReservaVO;

import java.util.ArrayList;

public class ReservaUtil {

    // Convertir ReservaVO en Reserva
    public Reserva convertirReserva(ReservaVO reservaVO) {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(reservaVO.getId_reserva());
        reserva.setDniCliente(reservaVO.getDni_cliente());
        reserva.setFechaLlegada(reservaVO.getFecha_llegada());
        reserva.setFechaSalida(reservaVO.getFecha_salida());
        reserva.setNumeroHabitaciones(reservaVO.getNumero_habitaciones());
        reserva.setTipoHabitacion(reservaVO.getTipo_habitacion());
        reserva.setFumador(reservaVO.isFumador());
        reserva.setRegimenAlojamiento(reservaVO.getRegimen_alojamiento());
        return reserva;
    }

    // ArrayList de ReservaVO y devuelve un ArrayList de Reserva
    public ArrayList<Reserva> listaReservas(ArrayList<ReservaVO> listaReservasVO) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        for (ReservaVO reservaVO : listaReservasVO) {
            reservas.add(convertirReserva(reservaVO));
        }
        return reservas;
    }

    // Convertir Reserva en ReservaVO
    public ReservaVO convertirReservaVO(Reserva reserva) {
        ReservaVO reservaVO = new ReservaVO(
                reserva.getIdReserva(),
                reserva.getFechaLlegada(),
                reserva.getFechaSalida(),
                reserva.getNumeroHabitaciones(),
                reserva.getTipoHabitacion(),
                reserva.isFumador(),
                reserva.getRegimenAlojamiento(),
                reserva.getDniCliente()
        );
        return reservaVO;
    }

    // Convertir una lista de Reserva a una lista de ReservaVO
    public ArrayList<ReservaVO> listaReservaVO(ArrayList<Reserva> listaReservas) {
        ArrayList<ReservaVO> listaReservaVO = new ArrayList<>();
        for (Reserva reserva : listaReservas) {
            listaReservaVO.add(convertirReservaVO(reserva));
        }
        return listaReservaVO;
    }
}
