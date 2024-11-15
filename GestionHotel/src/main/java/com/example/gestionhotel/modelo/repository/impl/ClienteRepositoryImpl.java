package com.example.gestionhotel.modelo.repository.impl;

import com.almasb.fxgl.scene3d.Cone;
import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.repository.ClienteRepository;
import com.example.gestionhotel.modelo.tablas.ClienteVO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClienteRepositoryImpl implements ClienteRepository {

    private final ConexionJDBC conexion = new ConexionJDBC();
    private Statement statement;
    private String sentencia;
    private ArrayList<ClienteVO> clientes;
    private ClienteVO cliente;

    // Constructor por defecto vacío
    public ClienteRepositoryImpl() {

    }

    @Override
    public ArrayList<ClienteVO> obtenerListaClientes() throws ExeptionHotel, SQLException {
        Connection conn = this.conexion.conectarBD();
        this.clientes = new ArrayList<>();
        this.statement = conn.createStatement();
        this.sentencia = "SELECT * FROM clientes";
        ResultSet rs = this.statement.executeQuery(this.sentencia);
        while (rs.next()) {
            String dni = rs.getString("dni");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellidos");
            String direccion = rs.getString("direccion");
            String localidad = rs.getString("localidad");
            String provincia = rs.getString("provincia");
            this.cliente = new ClienteVO(dni, nombre, apellido, direccion, localidad, provincia);
            this.cliente.setDni(dni);
            System.out.println(this.cliente);
            this.clientes.add(this.cliente);
            System.out.println(provincia);
        }
        this.conexion.desconectarBD(conn);
        return this.clientes;
    }

    @Override
    public void addCliente(ClienteVO clienteVO) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            this.sentencia = "INSERT INTO clientes (dni, nombre, apellidos, direccion, localidad, provincia) VALUES ('" + clienteVO.getDni() + "','" + clienteVO.getNombre() + "','" + clienteVO.getApellido() + "','" + clienteVO.getDireccion() + "','" + clienteVO.getLocalidad() + "','" + clienteVO.getProvincia() + "')";
            this.statement.executeUpdate(this.sentencia);
            this.statement.close();
            this.conexion.desconectarBD(conn);
            System.out.println("echo");
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al insertar el cliente");
        }
    }

    @Override
    public void deleteCliente(ClienteVO clienteVO) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            Statement comando = conn.createStatement();
            String sql = String.format("DELETE FROM clientes WHERE dni = '%s'", clienteVO.getDni());
            comando.executeUpdate(sql);
            this.statement.close();
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al eliminar el cliente");
        }
    }

    @Override
    public void editCliente(ClienteVO clienteVO) throws ExeptionHotel {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            String sql = String.format("UPDATE clientes SET nombre = '%s', apellido = '%s', direccion = '%s', localidad = '%s', provincia = '%s' WHERE dni = %d", clienteVO.getDni() + "','" + clienteVO.getNombre() + "','" + clienteVO.getApellido() + "','" + clienteVO.getDireccion() + "','" + clienteVO.getLocalidad() + "','" + clienteVO.getProvincia());
            this.statement.executeUpdate(sql);
            this.statement.close();
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al editar el cliente");
        }
    }

    @Override
    public ClienteVO buscarPorDNI(String dni) throws ExeptionHotel {
        ClienteVO cliente = null;
        String sentencia = "SELECT * FROM cliente WHERE dni = ?";
        try {
            Connection conn = this.conexion.conectarBD();
            PreparedStatement pstmt = conn.prepareStatement(sentencia);
            pstmt.setString(1, dni); // Establecemos el valor del DNI en la consulta
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new ClienteVO(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("direccion"),
                        rs.getString("localidad"),
                        rs.getString("provincia")
                );
            }
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExeptionHotel("Error al buscar el cliente por DNI");
        }
        return cliente;
    }

    @Override
    public String dniCliente() {
        String dni = "";

        String sql = "SELECT dni FROM cliente ORDER BY dni DESC LIMIT 1";
        try (Connection conn = this.conexion.conectarBD();
             Statement comando = conn.createStatement();
             ResultSet registro = comando.executeQuery(sql)) {

            if (registro.next()) {
                dni = registro.getString("dni");
            } else {
                throw new ExeptionHotel("No se encontró ningún cliente en la base de datos.");
            }

        } catch (SQLException e) {
            throw new ExeptionHotel("Error al buscar el último DNI del cliente: " + e.getMessage());
        }

        return dni;
    }

}
