package com.example.gestionhotel.modelo.util;

import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.ReservaVO;

public class ReservaUtil {

    // Convertir ReservaVO a Reserva
    public Reserva convertirReserva(ReservaVO reservaVO) {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(reservaVO.getId_reserva());
        reserva.setDniCliente(reservaVO.getDni_cliente());
        reserva.setFechaLlegada(reservaVO.getFecha_llegada());
        reserva.setFechaSalida(reservaVO.getFecha_salida());
        reserva.setTipoHabitacion(reservaVO.getTipo_habitacion());
        reserva.setFumador(reservaVO.isFumador());
        reserva.setRegimenAlojamiento(reservaVO.getRegimen_alojamiento());
        return reserva;
    }

}
