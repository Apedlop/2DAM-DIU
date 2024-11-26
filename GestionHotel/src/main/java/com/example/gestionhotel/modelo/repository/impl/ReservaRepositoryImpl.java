package com.example.gestionhotel.modelo.repository.impl;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.repository.ReservaRepository;
import com.example.gestionhotel.modelo.tablas.ClienteVO;
import com.example.gestionhotel.modelo.tablas.RegimenAlojamiento;
import com.example.gestionhotel.modelo.tablas.ReservaVO;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaRepositoryImpl implements ReservaRepository {

    private final ConexionJDBC conexion = new ConexionJDBC();
    private Statement statement;
    private String sentencia;
    private ArrayList<ReservaVO> reservas;
    private ReservaVO reserva;

    // Constructor por defecto vacío
    public ReservaRepositoryImpl() {

    }

    @Override
    public ArrayList<ReservaVO> obtenerListaReservas() throws ExeptionHotel, SQLException {
        Connection conn = this.conexion.conectarBD();
        this.reservas = new ArrayList<>();
        this.statement = conn.createStatement();
        this.sentencia = "SELECT * FROM reservas";
        ResultSet rs = this.statement.executeQuery(this.sentencia);
        while (rs.next()) {
            Integer id_reserva = rs.getInt("id_reserva");
            String dni_cliente = rs.getString("dni_cliente");
            LocalDate fecha_llegada = rs.getDate("fecha_llegada").toLocalDate();
            LocalDate fecha_salida = rs.getDate("fecha_salida").toLocalDate();
            Integer numero_habitaciones = rs.getInt("numero_habitaciones");
            TipoHabitacion tipo_habitacion = TipoHabitacion.valueOf(rs.getString("tipo_habitacion"));
            Boolean fumador = rs.getBoolean("fumador");
            RegimenAlojamiento regimen_alojamiento = RegimenAlojamiento.valueOf(rs.getString("regimen_alojamiento"));
            this.reserva = new ReservaVO(id_reserva, dni_cliente, fecha_llegada, fecha_salida, numero_habitaciones.intValue(), tipo_habitacion, fumador, regimen_alojamiento);
            this.reserva.setId_reserva(id_reserva);
            this.reservas.add(this.reserva);
        }
        this.conexion.desconectarBD(conn);
        return this.reservas;
    }

    @Override
    public void addReserva(ReservaVO reserva) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            String sentencia = "INSERT INTO reservas (dni_cliente, fecha_llegada, fecha_salida, numero_habitaciones, tipo_habitacion, fumador, regimen_alojamiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sentencia);
            pstmt.setString(1, reserva.getDni_cliente());
            pstmt.setDate(2, Date.valueOf(reserva.getFecha_llegada()));
            pstmt.setDate(3, Date.valueOf(reserva.getFecha_salida()));
            pstmt.setInt(4, reserva.getNumero_habitaciones());
            pstmt.setString(5, reserva.getTipo_habitacion().toString());  // Convertir el tipo a String si es necesario
            pstmt.setBoolean(6, reserva.isFumador());
            pstmt.setString(7, reserva.getRegimen_alojamiento().toString());  // Convertir el tipo a String si es necesario
            pstmt.executeUpdate();
            pstmt.close();
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al insertar la reserva.");
        }
    }

    @Override
    public void deleteReserva(ReservaVO reserva) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            String sentencia = "DELETE FROM reservas WHERE id_reserva = ?";
            PreparedStatement pstmt = conn.prepareStatement(sentencia);
            pstmt.setInt(1, reserva.getId_reserva());
            pstmt.executeUpdate();
            pstmt.close();
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al eliminar la reserva.");
        }
    }

    @Override
    public void editReserva(ReservaVO reserva) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            String sentencia = "UPDATE reservas SET dni_cliente = ?, fecha_llegada = ?, fecha_salida = ?, numero_habitaciones = ?, tipo_habitacion = ?, fumador = ?, regimen_alojamiento = ? WHERE id_reserva = ?";
            PreparedStatement pstmt = conn.prepareStatement(sentencia);
            pstmt.setString(1, reserva.getDni_cliente());
            pstmt.setDate(2, Date.valueOf(reserva.getFecha_llegada()));
            pstmt.setDate(3, Date.valueOf(reserva.getFecha_salida()));
            pstmt.setInt(4, reserva.getNumero_habitaciones());
            pstmt.setString(5, reserva.getTipo_habitacion().toString());
            pstmt.setBoolean(6, reserva.isFumador());
            pstmt.setString(7, reserva.getRegimen_alojamiento().toString());
            pstmt.setInt(8, reserva.getId_reserva());
            pstmt.executeUpdate();
            pstmt.close();
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al editar la reserva.");
        }
    }

    @Override
    public ReservaVO buscarReserva(String dni) throws ExeptionHotel {
        ReservaVO reserva = null;
        String sentencia = "SELECT * FROM reservas WHERE dni_cliente = ?";
        try {
            Connection conn = this.conexion.conectarBD();
            PreparedStatement pstmt = conn.prepareStatement(sentencia);
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reserva = new ReservaVO(
                        rs.getInt("id_reserva"),
                        rs.getString("dni_cliente"),
                        rs.getDate("fecha_llegada").toLocalDate(),  // Convertir java.sql.Date a LocalDate
                        rs.getDate("fecha_salida").toLocalDate(),   // Convertir java.sql.Date a LocalDate
                        rs.getInt("numero_habitaciones"),
                        TipoHabitacion.valueOf(rs.getString("tipo_habitacion")),  // Suponiendo que TipoHabitacion tiene un valor correspondiente
                        rs.getBoolean("fumador"),  // Obtener el valor booleano
                        RegimenAlojamiento.valueOf(rs.getString("regimen_alojamiento"))  // Suponiendo que RegimenAlojamiento tiene un valor correspondiente
                );
            }
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al buscar la reserva del cliente.");
        }
        return reserva;
    }

}
