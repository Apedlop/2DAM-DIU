package com.example.ejemplo_ex1ev1.modelo.util;

import com.example.ejemplo_ex1ev1.modelo.Articulo;
import com.example.ejemplo_ex1ev1.modelo.ArticuloVO;

import java.util.ArrayList;

public class CatalogoUtil {

    // Convertir ArticuloVO a Articulo
    public Articulo convertirArticulo(ArticuloVO articuloVO) {
        Articulo articulo = new Articulo();
        articulo.setId(articuloVO.getId());
        articulo.setNombre(articuloVO.getNombre());
        articulo.setDescripcion(articuloVO.getDescripcion());
        articulo.setPrecio(articuloVO.getPrecio());
        articulo.setStock(articuloVO.getStock());
        return articulo;
    }

    // ArrayList de ArticuloVO y devuelve un ArrayList de Artuculo
    public ArrayList<Articulo> listaArticulos(ArrayList<ArticuloVO> listaArticuloVO) {
        ArrayList<Articulo> articulos = new ArrayList<>();
        Articulo articulo = new Articulo();
        for (int i = 0; i < listaArticuloVO.size(); i++) {
            articulo = convertirArticulo(listaArticuloVO.get(i));
            articulos.add(articulo);
        }
        return articulos;
    }

    // Convertir Articulo a ArticuloVO
    public ArticuloVO convetirArticuloVO(Articulo articulo) {
        ArticuloVO articuloVO = new ArticuloVO();
        articuloVO.setId(articulo.getId());
        articuloVO.setNombre(articulo.getNombre());
        articuloVO.setDescripcion(articulo.getDescripcion());
        articuloVO.setPrecio(articulo.getPrecio());
        articuloVO.setStock(articulo.getStock());
        return articuloVO;
    }

    // ArrayList de Articulo y devuelve un ArrayList de ArticuloVO
    public ArrayList<ArticuloVO> listaArticuloVO(ArrayList<Articulo> listaArticulo) {
        ArrayList<ArticuloVO> listaArticuloVO = new ArrayList<>();
        ArticuloVO articuloVO = new ArticuloVO();
        for (int i = 0; i < listaArticuloVO.size(); i++) {
            articuloVO = convetirArticuloVO(listaArticulo.get(i));
            listaArticuloVO.add(articuloVO);
        }
        return listaArticuloVO;
    }

}
