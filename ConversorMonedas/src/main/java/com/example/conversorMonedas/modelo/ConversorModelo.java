package com.example.conversorMonedas.modelo;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.MonedaRepository;
import Modelo.repository.impl.MonedaRepositoryImpl;

import java.util.Iterator;

public class ConversorModelo {

    private MonedaRepositoryImpl monedaRepository;

    public void setMonedaRepository(MonedaRepositoryImpl monedaRepository) {
        this.monedaRepository = monedaRepository;
    }

    public float convertir(float multiplicador, float cantidad) {
        // Realizar la conversión
        float resultado = cantidad * multiplicador;
        return resultado;
    }

    public float recuperarMoneda() throws ExcepcionMoneda {
        Iterator<MonedaVO> it = this.monedaRepository.ObtenerListaMonedas().iterator();
        float mult=0;
        while(it.hasNext()) {
            MonedaVO mon = (MonedaVO)it.next();
            if (mon.getNombre().equalsIgnoreCase("euro")){
                mult = mon.getMultiplicador();
            }
        }
        return mult;
    }
}
