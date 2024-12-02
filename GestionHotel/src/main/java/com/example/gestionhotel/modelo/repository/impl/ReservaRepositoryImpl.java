package com.example.gestionhotel.modelo.repository.impl;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.repository.ReservaRepository;
import com.example.gestionhotel.modelo.tablas.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservaRepositoryImpl implements ReservaRepository {

    private final ConexionJDBC conexion = new ConexionJDBC();
    private Statement statement;
    private String sentencia;
    private ArrayList<ReservaVO> listaReservas;
    private ReservaVO reserva;

    // Constructor por defecto vacío
    public ReservaRepositoryImpl() {

    }

    // Obtener lista de reservas
    @Override
    public ArrayList<ReservaVO> obtenerListaReservas() throws ExeptionHotel {
        this.listaReservas = new ArrayList<>();
        String query = "SELECT * FROM reserva ORDER BY fechaLlegada ASC";
        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Integer idReserva = rs.getInt("id");
                Date fechaLlegadaSQL = rs.getDate("fechaLlegada");
                Date fechaSalidaSQL = rs.getDate("fechaSalida");
                Integer numeroHabitaciones = rs.getInt("nHabitaciones");
                String tipoHabitacionStr = rs.getString("tipoHabitacion");
                Boolean fumador = rs.getBoolean("fumador");
                String regimenAlojamientoStr = rs.getString("regimenAlojamiento");
                String dniCliente = rs.getString("dni");

                // Convertir java.sql.Date a LocalDate utilizando toLocalDate
                LocalDate fechaLlegada = fechaLlegadaSQL != null ? fechaLlegadaSQL.toLocalDate() : null;
                LocalDate fechaSalida = fechaSalidaSQL != null ? fechaSalidaSQL.toLocalDate() : null;

                // Convertir tipoHabitacion y regimenAlojamiento a Enum
                TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(tipoHabitacionStr);
                RegimenAlojamiento regimenAlojamiento = RegimenAlojamiento.valueOf(regimenAlojamientoStr);

                // Crear el objeto ReservaVO y añadirlo a la lista
                ReservaVO reservaVO = new ReservaVO(idReserva, fechaLlegada, fechaSalida, numeroHabitaciones,
                        tipoHabitacion, fumador, regimenAlojamiento, dniCliente);
                this.listaReservas.add(reservaVO);
            }

            return this.listaReservas;
        } catch (SQLException e) {
            throw new ExeptionHotel("No se ha podido realizar la operación de obtener reservas");
        }
    }

    @Override
    public int contarReservasPorTipoHabitacion(TipoHabitacion tipoHabitacion) throws ExeptionHotel {
        String query = "SELECT COUNT(*) AS total FROM reserva WHERE tipoHabitacion = ?";
        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tipoHabitacion.name()); // Usamos el nombre del enum como valor en la consulta.
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total"); // Devuelve el número total de reservas.
                }
            }
        } catch (SQLException e) {
            throw new ExeptionHotel("No se ha podido realizar la operación de contar reservas por tipo de habitación");
        }
        return 0;
    }

    // Crear una nueva reserva
    @Override
    public void crearRerserva(ReservaVO reservaVO) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            String query = "INSERT INTO reserva (fechaLlegada, fechaSalida, nHabitaciones, tipoHabitacion, fumador, regimenAlojamiento, dni) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(query);
            System.out.println("EStoy en crear reseva del IMpl");
            // Establecer los parámetros de la consulta
            stmt.setDate(1, java.sql.Date.valueOf(reservaVO.getFecha_llegada())); // fecha_llegada
            stmt.setDate(2, java.sql.Date.valueOf(reservaVO.getFecha_salida())); // fecha_salida
            stmt.setInt(3, reservaVO.getNumero_habitaciones()); // numero_habitaciones
            stmt.setString(4, reservaVO.getTipo_habitacion().toString()); // tipo_habitacion (Enum -> String)
            stmt.setBoolean(5, reservaVO.isFumador()); // fumador
            stmt.setString(6, reservaVO.getRegimen_alojamiento().toString()); // regimen_alojamiento (Enum -> String)
            stmt.setString(7, reservaVO.getDni_cliente()); // dni_cliente

            // Ejecutar la actualización
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Lanzar una excepción personalizada con detalles
//            e.printStackTrace();  // Imprimir el stack trace para depurar
            throw new ExeptionHotel("No se ha podido realizar la operación de crear reserva. Detalles: " + e.getMessage());
        }
    }

    // Eliminar una reserva
    @Override
    public void eliminarReserva(Integer idReserva) throws ExeptionHotel {
        String sql = "DELETE FROM reserva WHERE id = ?";

        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignar el valor del parámetro
            stmt.setInt(1, idReserva);

            // Ejecutar la actualización
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Lanzar una excepción personalizada con detalles
            throw new ExeptionHotel("No se ha podido realizar la eliminación de reserva. Detalles: " + e.getMessage());
        }
    }

    // Editar una reserva
    @Override
    public void editarReserva(ReservaVO reservaVO) throws ExeptionHotel {
        try {
            String sql = "UPDATE reserva SET " + "fechaLlegada = ?, fechaSalida = ?, nHabitaciones = ?, " + "tipoHabitacion = ?, fumador = ?, regimenAlojamiento = ?, dni = ? " + "WHERE id = ?";

            Connection conn = this.conexion.conectarBD();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Establecer los parámetros de la consulta
            stmt.setDate(1, java.sql.Date.valueOf(reservaVO.getFecha_llegada())); // fecha_llegada
            stmt.setDate(2, java.sql.Date.valueOf(reservaVO.getFecha_salida())); // fecha_salida
            stmt.setInt(3, reservaVO.getNumero_habitaciones()); // numero_habitaciones
            stmt.setString(4, reservaVO.getTipo_habitacion().toString()); // tipo_habitacion (enum a String)
            stmt.setBoolean(5, reservaVO.isFumador()); // fumador
            stmt.setString(6, reservaVO.getRegimen_alojamiento().toString()); // regimen_alojamiento (enum a String)
            stmt.setString(7, reservaVO.getDni_cliente()); // dni_cliente
            stmt.setInt(8, reservaVO.getId_reserva()); // id_reserva (para la condición WHERE)

            // Ejecutar la actualización
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Lanzar una excepción personalizada con detalles
            throw new ExeptionHotel("No se ha podido realizar la edición de reserva. Detalles: " + e.getMessage());
        }
    }

    // Obtener el último ID de reserva
    @Override
    public int ultimoIdReserva() throws ExeptionHotel {
        int ultimoId = 0;

        try {
            Connection conn = this.conexion.conectarBD();
            Statement comando = conn.createStatement();
            String sql = "SELECT id FROM reserva ORDER BY id DESC LIMIT 1";
            ResultSet rs = comando.executeQuery(sql);
            if (rs.next()) {
                ultimoId = rs.getInt("id");
            }
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExeptionHotel("No se ha podido obtener el último ID de reserva");
        }

        return ultimoId;
    }

    public ArrayList<ReservaVO> obtenerReservasCliente(String dniCliente) throws ExeptionHotel {
        this.listaReservas = new ArrayList<>();
        String query = "SELECT * FROM reserva WHERE dni = ? ORDER BY fechaLlegada ASC";
        try (Connection conn = this.conexion.conectarBD();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer el valor del parámetro antes de ejecutar la consulta
            stmt.setString(1, dniCliente);

            // Ejecutar la consulta y obtener los resultados
            ResultSet rs = stmt.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                Integer idReserva = rs.getInt("id");
                Date fechaLlegadaSQL = rs.getDate("fechaLlegada");
                Date fechaSalidaSQL = rs.getDate("fechaSalida");
                Integer numeroHabitaciones = rs.getInt("nHabitaciones");
                String tipoHabitacionStr = rs.getString("tipoHabitacion");
                Boolean fumador = rs.getBoolean("fumador");
                String regimenAlojamientoStr = rs.getString("regimenAlojamiento");

                // Convertir java.sql.Date a LocalDate utilizando toLocalDate
                LocalDate fechaLlegada = fechaLlegadaSQL != null ? fechaLlegadaSQL.toLocalDate() : null;
                LocalDate fechaSalida = fechaSalidaSQL != null ? fechaSalidaSQL.toLocalDate() : null;

                // Convertir tipoHabitacion y regimenAlojamiento a Enum
                TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(tipoHabitacionStr);
                RegimenAlojamiento regimenAlojamiento = RegimenAlojamiento.valueOf(regimenAlojamientoStr);

                // Crear el objeto ReservaVO y añadirlo a la lista
                ReservaVO reservaVO = new ReservaVO(idReserva, fechaLlegada, fechaSalida, numeroHabitaciones,
                        tipoHabitacion, fumador, regimenAlojamiento, dniCliente);
                this.listaReservas.add(reservaVO);
            }

            return this.listaReservas;
        } catch (SQLException e) {
            throw new ExeptionHotel("No se ha podido realizar la operación de obtener reservas");
        }
    }

}
