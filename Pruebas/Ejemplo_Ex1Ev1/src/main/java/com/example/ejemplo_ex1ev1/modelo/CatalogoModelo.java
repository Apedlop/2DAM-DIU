package com.example.ejemplo_ex1ev1.modelo;

import com.example.ejemplo_ex1ev1.modelo.repository.CatalogoRepository;
import com.example.ejemplo_ex1ev1.modelo.repository.TotalInterface;
import com.example.ejemplo_ex1ev1.modelo.util.CatalogoUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;

public class CatalogoModelo {

    private CatalogoRepository catalogoRepository;
    private CatalogoUtil catalogoUtil = new CatalogoUtil();
    private IntegerProperty numArticulos = new SimpleIntegerProperty();
    private TotalInterface totalInterface;

    // Constructor vacío por defecto
    public CatalogoModelo() {

    }

    // Inyección medeante un set de CatalogoRepositoryImpl
    public void setCatalogoRepository(CatalogoRepository catalogoRepository) {
        this.catalogoRepository = catalogoRepository;
    }

    public void setTotalInterface(TotalInterface totalInterface) {
        this.totalInterface = totalInterface;
    }

    // Métodos para contar la cantidad de árticulos que hay en la BD
    public void setNumArticulos(Integer numArticulos) {
        this.numArticulos.set(numArticulos);
    }

    public void incNumArticulos() {
        this.numArticulos.set(numArticulos.get() + 1);
    }

    public IntegerProperty getNumArticulos() {
        return numArticulos;
    }

    // Método para obtener la lista de Articulos desde la BD y convertirla en una lista de Articulo
    public ArrayList<Articulo> obtenerListaArticulo() throws ExceptionCatalogo, SQLException {
        try {
            ArrayList<ArticuloVO> listaArticuloVO = catalogoRepository.obtenerListaArticulos();
            ArrayList<Articulo> listaArticulo = catalogoUtil.listaArticulos(listaArticuloVO);
            return listaArticulo;
        } catch (Exception e) {
            // Manejo de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al listar los articulos");
            alert.setTitle("Error con la base de datos");
            alert.setContentText("No se puede conectar con la base de datos");
            alert.showAndWait();
            throw new SQLException("No se pudo obtener la lista de clientes", e);
        }
    }

    // Método para añadir articulos a la BD
    public void añadirArticulo(Articulo articulo) throws ExceptionCatalogo {
        ArticuloVO articuloVO = catalogoUtil.convetirArticuloVO(articulo);
        catalogoRepository.addArticulo(articuloVO);
    }

    // Método para calcular el total
    public float total(Integer unidades, Float precio){
        return totalInterface.total(unidades,precio);
    }

}
