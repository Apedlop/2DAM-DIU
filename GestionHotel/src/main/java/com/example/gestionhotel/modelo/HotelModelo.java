package com.example.gestionhotel.modelo;

import com.example.gestionhotel.modelo.repository.impl.ClienteRepositoryImpl;
import com.example.gestionhotel.modelo.repository.impl.ReservaRepositoryImpl;
import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.ClienteVO;
import com.example.gestionhotel.modelo.util.ClienteUtil;
import com.example.gestionhotel.modelo.util.ReservaUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HotelModelo {

    private ClienteRepositoryImpl clienteRepository;
    private ReservaRepositoryImpl reservaRepository;
    private ClienteUtil clienteUtil;
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
    public ArrayList<Cliente> obtenerListaClientes() throws ExeptionHotel, SQLException {
        try {
            // Inicializamos clienteRepository y clienteUtil
            clienteRepository = new ClienteRepositoryImpl();
            clienteUtil = new ClienteUtil();

            // Obtener la lista de ClienteVO desde el repositorio
            ArrayList<ClienteVO> listaClienteVO = clienteRepository.obtenerListaClientes();

            // Convertimos la lista de ClienteVO a Cliente usando ClienteUtil
            ArrayList<Cliente> listaCliente = clienteUtil.listaClientes(listaClienteVO);

            // Ordenamos la lista de clientes por apellido
            Collections.sort(listaCliente, new Comparator<Cliente>() {
                @Override
                public int compare(Cliente c1, Cliente c2) {
                    return c1.getApellido().compareTo(c2.getApellido());
                }
            });

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
    public void añadirCliente(ClienteVO clienteVO) throws ExeptionHotel {
        clienteRepository = new ClienteRepositoryImpl();
        clienteRepository.addCliente(clienteVO);
    }

    // Método para editar personas de la BD
    public void editarCliente(ClienteVO clienteVO) throws ExeptionHotel {
        clienteRepository = new ClienteRepositoryImpl();
        clienteRepository.editCliente(clienteVO);
    }

    // Método para eliminar Cliente de la BD
    public void eliminarCliente(ClienteVO clienteVO) throws ExeptionHotel {
        clienteRepository = new ClienteRepositoryImpl();
        clienteRepository.deleteCliente(clienteVO);
    }

    // Método para buscar un cliente por DNI
    public ClienteVO buscarDNI(String dni) throws ExeptionHotel {
        clienteRepository = new ClienteRepositoryImpl();
        System.out.println(dni);
        return clienteRepository.buscarPorDNI(dni);
    }

    public String obtenerIdCliente() throws ExeptionHotel {
        if (clienteRepository == null) {
            throw new ExeptionHotel("Error: clienteRepository no está inicializado.");
        }
        return clienteRepository.dniCliente();
    }

}
