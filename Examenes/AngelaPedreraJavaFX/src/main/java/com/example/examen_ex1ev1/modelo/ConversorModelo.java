package com.example.examen_ex1ev1.modelo;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;
import com.example.examen_ex1ev1.modelo.util.MonedaUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConversorModelo {

    private MonedaRepository monedaRepository;
    private MonedaUtil monedaUtil = new MonedaUtil();

    // Constructor vacío por defecto
    public ConversorModelo() {

    }

    public void setMonedaRepository(MonedaRepository monedaRepository) {
        this.monedaRepository = monedaRepository;
    }

    public ArrayList<Moneda> obtenerListaMonedas() throws ExcepcionMoneda {
        ArrayList<MonedaVO> listaMonedasVO = monedaRepository.ObtenerListaMonedas();
        ArrayList<Moneda> listaMonedas = monedaUtil.listaMonedas(listaMonedasVO);
        return listaMonedas;
    }

    public float convertir(float multiplicador, float cantidad) {
        float resultado = multiplicador * cantidad;
        return resultado;
    }

    public float recuperarMult(String tipoMoneda) throws ExcepcionMoneda {
        Iterator<MonedaVO> it = this.monedaRepository.ObtenerListaMonedas().iterator();
        float mult = 0;
        while (it.hasNext()) {
            MonedaVO moneda = it.next();
            if (moneda.getNombre().equalsIgnoreCase(tipoMoneda)) {
                mult = moneda.getMultiplicador();
            }
        }
        return mult;
    }

}
