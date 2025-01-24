package com.example.ejemplo_ex1ev1.modelo.repository;

import com.example.ejemplo_ex1ev1.modelo.ArticuloVO;
import com.example.ejemplo_ex1ev1.modelo.ExceptionCatalogo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CatalogoRepository {

    ArrayList<ArticuloVO> obtenerListaArticulos() throws ExceptionCatalogo;

    void addArticulo(ArticuloVO articuloVO) throws ExceptionCatalogo;

    void deleteArticulo(ArticuloVO articuloVO) throws ExceptionCatalogo;

    void editArticulo(ArticuloVO articuloVO) throws ExceptionCatalogo;

}
