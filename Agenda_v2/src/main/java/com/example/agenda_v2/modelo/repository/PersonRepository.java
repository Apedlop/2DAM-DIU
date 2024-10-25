package com.example.agenda_v2.modelo.repository;

import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.PersonVO;
import java.util.ArrayList;
import java.util.List;

public interface PersonRepository {

    // Este es el de Cargar la lista
    ArrayList<PersonVO> ObtenerListaPersonas() throws ExceptionPersona;

    void guardarPersona(PersonVO var1) throws ExceptionPersona;

    void eliminarPersona(Integer var1) throws ExceptionPersona;

    void actualizarPersona(PersonVO var1) throws ExceptionPersona;

    // Código para gestionar actualizaciones y eliminaciones de personas.
    int codigoPersona() throws ExceptionPersona;;

}
