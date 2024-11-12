package com.example.gestionhotel.modelo;

import com.example.gestionhotel.modelo.repository.impl.ClienteRepositoryImpl;
import com.example.gestionhotel.modelo.repository.impl.ReservaRepositoryImpl;
import com.example.gestionhotel.modelo.tablas.ClienteVO;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import com.example.gestionhotel.modelo.util.ReservaUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class HotelModelo {

    private ClienteRepositoryImpl clienteRepository;
    private ReservaRepositoryImpl reservaRepository;
    private ClienteUtil clienteUtil = new ClienteUtil();
    private ReservaUtil reservaUtil = new ReservaUtil();

    // Constructo por defecto vacío
    public HotelModelo() {

    }

    // Inyección mediante un set de ClienteRepositoryImpl
    public void setClienteRepository(ClienteRepositoryImpl clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Inyección mediante un set de ReservaRepositoryImpl
    public void setReservaRepository(ReservaRepositoryImpl reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    // Método para obtener la lista de Clientes desde la BD y convertirla en una lista de Cliente
    public ArrayList<ClienteVO> listarClientes() throws ExeptionHotel, SQLException {
        return clienteRepository.obtenerListaClientes();
    }

    // Método para añadir Clientes en la BD
    public void añadirCliente(ClienteVO clienteVO) throws ExeptionHotel {
        clienteRepository.addCliente(clienteVO);
    }

    // Método para editar personas de la BD
    public void editarCliente(ClienteVO clienteVO) throws ExeptionHotel {
        clienteRepository.editCliente(clienteVO);
    }

    // Método para eliminar Cliente de la BD
    public void eliminarCliente(ClienteVO clienteVO) throws ExeptionHotel {
        clienteRepository.deleteCliente(clienteVO);
    }

    // Método para buscar un cliente por DNI
    public ClienteVO buscarDNI(String dni) throws ExeptionHotel {
        return clienteRepository.buscarPorDNI(dni);
    }

}
