package com.example.gestionhotel.modelo;

import com.example.gestionhotel.modelo.repository.ClienteRepository;
import com.example.gestionhotel.modelo.repository.ReservaRepository;
import com.example.gestionhotel.modelo.repository.impl.ReservaRepositoryImpl;
import com.example.gestionhotel.modelo.tablas.*;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import com.example.gestionhotel.modelo.util.ReservaUtil;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class HotelModelo {

    private ClienteRepository clienteRepository;
    private ReservaRepository reservaRepository;
    private ClienteUtil clienteUtil = new ClienteUtil();
    private ReservaUtil reservaUtil = new ReservaUtil();

    // Constructo por defecto vacío
    public HotelModelo() {

    }

    // Inyección mediante un set de ClienteRepositoryImpl
    public void setClienteRepository(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Inyección mediante un set de ReservaRepositoryImpl
    public void setReservaRepository(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Método para obtener la lista de Clientes desde la BD y convertirla en una lista de Cliente
    public ArrayList<Cliente> obtenerListaClientes() throws ExeptionHotel, SQLException {
        try {
            // Obtener la lista de ClienteVO desde el repositorio
            ArrayList<ClienteVO> listaClienteVO = clienteRepository.obtenerListaClientes();

            // Convertimos la lista de ClienteVO a Cliente usando ClienteUtil
            ArrayList<Cliente> listaCliente = clienteUtil.listaClientes(listaClienteVO);

            // Retornamos la lista de Cliente
            return listaCliente;
        } catch (SQLException e) {
            // Manejo de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al listar los clientes.");
            alert.setTitle("Error con la base de datos");
            alert.setContentText("No se puede conectar con la base de datos");
            alert.showAndWait();
            throw new SQLException("No se pudo obtener la lista de clientes", e);
        }
    }

    // Método para añadir Clientes en la BD
    public void anadirCliente(Cliente cliente) throws ExeptionHotel {
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        clienteRepository.addCliente(clienteVO);
    }

    // Método para editar personas de la BD
    public void editarCliente(Cliente cliente) throws ExeptionHotel {
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        clienteRepository.editCliente(clienteVO);
    }

    // Método para eliminar Cliente de la BD
    public void eliminarCliente(Cliente cliente) throws ExeptionHotel {
        ClienteVO clienteVO = clienteUtil.convertirClienteVO(cliente);
        clienteRepository.deleteCliente(clienteVO);
    }

    // Método para buscar un cliente por DNI
    public Cliente buscarDNI(String dni) throws ExeptionHotel {
        ClienteVO clienteVO = clienteRepository.buscarPorDNI(dni);
        return clienteUtil.convertirCliente(clienteVO);
    }

    public String obtenerIdCliente() throws ExeptionHotel {
        if (clienteRepository == null) {
            throw new ExeptionHotel("Error: clienteRepository no está inicializado.");
        }
        return clienteRepository.dniCliente();
    }

    /*
     * RESERVAS
     */

    public ArrayList<Reserva> obtenerListaReservas() throws ExeptionHotel, SQLException {
        try {
            // Obtener la lista de ReservaVO desde el repositorio
            ArrayList<ReservaVO> listaReservaVO = reservaRepository.obtenerListaReservas();

            // Convertimos la lista de ReservaVO a Reserva usando ReservaUtil
            ArrayList<Reserva> listaReservas = reservaUtil.listaReservas(listaReservaVO);

            // Retornamos la lista de Reserva
            return listaReservas;
        } catch (SQLException e) {
            // Manejo de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al listar las reservas.");
            alert.setTitle("Error con la base de datos");
            alert.setContentText("No se puede conectar con la base de datos");
            alert.showAndWait();
            throw new SQLException("No se pudo obtener la lista de reservas", e);
        }
    }

    // Método para añadir Clientes en la BD
    public void anadirReserva(Reserva reserva) throws ExeptionHotel {
        ReservaVO reservaVO = reservaUtil.convertirReservaVO(reserva);
        reservaRepository.crearRerserva(reservaVO);
    }

    // Método para editar personas de la BD
    public void editarReserva(Reserva reserva) throws ExeptionHotel {
        reservaRepository = new ReservaRepositoryImpl();
        ReservaVO reservaVO = reservaUtil.convertirReservaVO(reserva);
        reservaRepository.editarReserva(reservaVO);
    }

    // Método para eliminar Reserva de la BD
    public void eliminarReserva(Integer idReserva) throws ExeptionHotel {
        reservaRepository.eliminarReserva(idReserva);
    }

    // Método para buscar un cliente por DNI
    public ArrayList<Reserva> buscarReserva(String dni) throws ExeptionHotel {
        ArrayList<ReservaVO> reservaVO = reservaRepository.obtenerReservasCliente(dni);
        return reservaUtil.listaReservas(reservaVO);
    }

    // Método para obtener la cantidad de habitaciones ocupadas según el tipo
    public int habitacionesOcupadas(TipoHabitacion tipoHabitacion) throws ExeptionHotel {
        reservaRepository = new ReservaRepositoryImpl();
        return reservaRepository.contarReservasPorTipoHabitacion(tipoHabitacion);
    }

}
