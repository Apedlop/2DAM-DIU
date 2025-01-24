package com.example.ejemplo_ex1ev1.modelo.repository.impl;

import com.example.ejemplo_ex1ev1.modelo.ArticuloVO;
import com.example.ejemplo_ex1ev1.modelo.ExceptionCatalogo;
import com.example.ejemplo_ex1ev1.modelo.repository.CatalogoRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CatalogoRepositoryImpl implements CatalogoRepository {

    private final ConexionJDBC conexion = new ConexionJDBC();
    private Statement statement;
    private String sentencia;
    private ArrayList<ArticuloVO> articulos;
    private ArticuloVO articulo;

    public CatalogoRepositoryImpl() {

    }

    @Override
    public ArrayList<ArticuloVO> obtenerListaArticulos() throws ExceptionCatalogo {
       try {
           Connection conn = this.conexion.conectarBD();
           this.articulos = new ArrayList<>();
           this.statement = conn.createStatement();
           this.sentencia = "SELECT * FROM articulo";
           ResultSet rs = this.statement.executeQuery(this.sentencia);
           while (rs.next()) {
               Integer id = rs.getInt("id");
               String nombre = rs.getString("nombre");
               String descripcion = rs.getString("descripcion");
               float precio = rs.getFloat("precio");
               Integer stock = rs.getInt("stock");
               this.articulo = new ArticuloVO(nombre, descripcion, precio, stock);
               this.articulo.setId(id);
               this.articulos.add(this.articulo);
           }
           this.conexion.desconectarBD(conn);
           return this.articulos;
       } catch (SQLException e) {
           throw new ExceptionCatalogo("No se ha podido realizar la operación");
       }
    }

    @Override
    public void addArticulo(ArticuloVO articuloVO) throws ExceptionCatalogo {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            this.sentencia = "INSERT INTO articulo (nombre, descripcion, precio, stock) VALUES ('" + articuloVO.getNombre() + "','" + articuloVO.getDescripcion() + "','" + articuloVO.getPrecio() + "','" + articuloVO.getStock() + "')";
            this.statement.executeUpdate(this.sentencia);
            this.statement.close();
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExceptionCatalogo("Error al insertar el articulo");
        }
    }

    @Override
    public void deleteArticulo(ArticuloVO articuloVO) throws ExceptionCatalogo {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            Statement comando = conn.createStatement();
            String sql = String.format("DELETE FROM articulo WHERE id = '%'", articuloVO.getId());
            comando.executeUpdate(sql);
            this.conexion.desconectarBD(conn);
        } catch (SQLException e) {
            throw new ExceptionCatalogo("No se ha podido realizar la eliminación.");
        }
    }

    @Override
    public void editArticulo(ArticuloVO articuloVO) throws ExceptionCatalogo {
        try {
            Connection conn = this.conexion.conectarBD();
            this.statement = conn.createStatement();
            String sql = String.format("UPDATE articulo SET nombre = '%s', descripcion = '%s', precio = '%s', stock = '%s' WHERE id = %d", articuloVO.getNombre(), articuloVO.getDescripcion(), articuloVO.getPrecio(), articuloVO.getStock(), articuloVO.getId());
            this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new ExceptionCatalogo("No se ha podido realizar la edición.");
        }
    }
}
