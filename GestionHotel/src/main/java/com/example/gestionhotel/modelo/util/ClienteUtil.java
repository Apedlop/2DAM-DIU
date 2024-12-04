package com.example.gestionhotel.modelo.util;

import com.example.gestionhotel.modelo.tablas.Cliente;
import com.example.gestionhotel.modelo.tablas.ClienteVO;

import java.util.ArrayList;

public class ClienteUtil {

    // Convertir ClienteVO en Cliente
    public static Cliente convertirCliente(ClienteVO clienteVO) {
        Cliente cliente = new Cliente();
        cliente.setDni(clienteVO.getDni());
        cliente.setNombre(clienteVO.getNombre());
        cliente.setApellido(clienteVO.getApellido());
        cliente.setDireccion(clienteVO.getDireccion());
        cliente.setLocalidad(clienteVO.getLocalidad());
        cliente.setProvincia(clienteVO.getProvincia());
        return cliente;
    }

    // ArrayList de ClienteVO y devuelve un ArrayList de Cliente
    public static ArrayList<Cliente> listaClientes(ArrayList<ClienteVO> listaClientesVO) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (ClienteVO clienteVO : listaClientesVO) {
            Cliente cliente = convertirCliente(clienteVO);
            clientes.add(cliente);
        }
        return clientes;
    }

    // Convertir Cliente a ClienteVO
    public static ClienteVO convertirClienteVO(Cliente cliente) {
        ClienteVO clienteVO = new ClienteVO();
        clienteVO.setDni(cliente.getDni());
        clienteVO.setNombre(cliente.getNombre());
        clienteVO.setApellido(cliente.getApellido());
        clienteVO.setDireccion(cliente.getDireccion());
        clienteVO.setLocalidad(cliente.getLocalidad());
        clienteVO.setProvincia(cliente.getProvincia());
        return clienteVO;
    }

    // Convertir una lista de Cliente a una lista de ClienteVO
    public static ArrayList<ClienteVO> listaClienteVO(ArrayList<Cliente> listaCliente) {
        ArrayList<ClienteVO> listaClienteVO = new ArrayList<>();
        for (Cliente cliente : listaCliente) {
            ClienteVO clienteVO = convertirClienteVO(cliente);
            listaClienteVO.add(clienteVO);
        }
        return listaClienteVO;
    }
}
