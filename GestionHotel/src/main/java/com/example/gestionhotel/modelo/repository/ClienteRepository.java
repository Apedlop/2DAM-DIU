package com.example.gestionhotel.modelo.repository;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.tablas.ClienteVO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ClienteRepository {

    // Método para convertir la lista de Clientes
    ArrayList<ClienteVO> obtenerListaClientes() throws ExeptionHotel, SQLException;

    // Método para añadir un Cliente
    void addCliente(ClienteVO clienteVO) throws ExeptionHotel;

    // Método para eliminar un Cliente
    void deleteCliente(ClienteVO clienteVO) throws ExeptionHotel;

    // Método para editar un Cliente
    void editCliente(ClienteVO clienteVO) throws ExeptionHotel;

    // Método para buscar un Cliente por DNI
    ClienteVO buscarPorDNI(String dni) throws ExeptionHotel;

    // Método para conseguir el DNI de un Cliente
    String dniCliente();

    // Método para ver las Reservas del Cliente


}
