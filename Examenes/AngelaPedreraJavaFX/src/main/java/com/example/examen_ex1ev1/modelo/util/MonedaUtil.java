package com.example.examen_ex1ev1.modelo.util;

import Modelo.MonedaVO;
import com.example.examen_ex1ev1.modelo.Moneda;

import java.util.ArrayList;

public class MonedaUtil {

    public Moneda convertirMoneda(MonedaVO monedaVO){
        Moneda moneda = new Moneda();
        moneda.setCodigo(monedaVO.getCodigo());
        moneda.setNombre(monedaVO.getNombre());
        moneda.setMultiplicador(monedaVO.getMultiplicador());
        return moneda;
    }

    public ArrayList<Moneda> listaMonedas(ArrayList<MonedaVO> listaMonedaVO){
        ArrayList<Moneda> monedas = new ArrayList<>();
        Moneda moneda = new Moneda();
        for (int i = 0; i < listaMonedaVO.size(); i++) {
            moneda = convertirMoneda(listaMonedaVO.get(i));
            monedas.add(moneda);
        }
        return monedas;
    }

}
