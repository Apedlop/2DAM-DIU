package com.example.agenda_v2.modelo.repository;

import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.PersonVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface PersonRepository {

    // Método para conseguir la lista de personas
    ArrayList<PersonVO> ObtenerListaPersonas() throws ExceptionPersona, SQLException;

    // Método para añadir una persona
    void addPerson(PersonVO var1) throws ExceptionPersona;

    // Método para eliminar una persona
    void deletePerson(Integer var1) throws ExceptionPersona;

    // Método para editar una persona
    void editPerson(PersonVO var1) throws ExceptionPersona;

    // Código para gestionar actualizaciones y eliminaciones de personas.
    int codigoPersona() throws ExceptionPersona;;

}
