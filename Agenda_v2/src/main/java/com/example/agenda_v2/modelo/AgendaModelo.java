package com.example.agenda_v2.modelo;

import com.example.agenda_v2.modelo.repository.PersonRepository;
import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;
import com.example.agenda_v2.modelo.util.PersonUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.SQLException;
import java.util.ArrayList;

public class AgendaModelo {

    private PersonRepositoryImpl personRepository;
    private IntegerProperty numPersonas = new SimpleIntegerProperty();
    private PersonUtil personUtil = new PersonUtil();

    // Inyección mediante un set de PersonRepositoryImpl
    public void setPersonRepository (PersonRepositoryImpl personRepository) {
        this.personRepository = personRepository;
    }

    // Constructor vacio
    public AgendaModelo() {

    }

    // Método para obtener la lista de personas desde la BD y convertirla en una lista de Person
    public ArrayList<PersonVO> listarPersonas() throws ExceptionPersona, SQLException {
        return personRepository.ObtenerListaPersonas();
    }


    // Métodos para añadir, editar y eliminar personas en la BD
    public void agregarPersona(PersonVO personVO) throws ExceptionPersona {
        personRepository.addPerson(personVO);
        incrementarContadorPersonas();
    }

    public void actualizarPersona(PersonVO personVO) throws ExceptionPersona {
        personRepository.editPerson(personVO);
    }

    public void eliminarPersona(PersonVO personVO) throws ExceptionPersona {
        personRepository.deletePerson(personVO.getCodigo());
        decrementarContadorPersonas();
    }

    public int obtenerNuevoCodigoPersona() throws ExceptionPersona {
        return personRepository.codigoPersona();
    }

    // Métodos para modificar y obtener el número de personas en la BD
    public void setNumPersonas(int numPers) {
        this.numPersonas.set(numPers);
    }

    public void decrementarContadorPersonas() {
        this.numPersonas.set(numPersonas.get() - 1);
    }

    public void incrementarContadorPersonas() {
        this.numPersonas.set(numPersonas.get() + 1);
    }

    public IntegerProperty getNumPersonas() {
        return numPersonas;
    }
}
