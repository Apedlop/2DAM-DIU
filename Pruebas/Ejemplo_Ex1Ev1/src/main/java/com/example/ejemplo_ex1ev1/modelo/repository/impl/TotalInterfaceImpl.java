package com.example.ejemplo_ex1ev1.modelo.repository.impl;

import com.example.ejemplo_ex1ev1.modelo.repository.TotalInterface;

public class TotalInterfaceImpl implements TotalInterface {

    @Override
    public float total(Integer unidades, Float precio) {
        return unidades * precio;
    }

}
