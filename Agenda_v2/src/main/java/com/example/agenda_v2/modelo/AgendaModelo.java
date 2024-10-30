package com.example.agenda_v2.modelo;

import com.example.agenda_v2.modelo.repository.PersonRepository;
import com.example.agenda_v2.modelo.util.PersonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;

public class AgendaModelo {

    private PersonRepository personRepository; // Cambiado a la interfaz
    private PersonUtil personUtil;

    // Constructor que acepta un PersonRepository
    public AgendaModelo(PersonRepository personRepository) {
        this.personRepository = personRepository; // Inicializa el repositorio
    }

    // Método para recuperar personas de la base de datos y convertirlas a ObservableList<Person>
    public ObservableList<Person> recuperarPersonas() throws ExceptionPersona {
        ArrayList<PersonVO> personVOList = this.personRepository.ObtenerListaPersonas();
        ObservableList<Person> personList = FXCollections.observableArrayList();
        Iterator<PersonVO> itPersonVO = personVOList.iterator();
        while (itPersonVO.hasNext()) {
            PersonVO personVO = itPersonVO.next();
            Person person = new Person(
                    personVO.getNombre(),
                    personVO.getApellido(),
                    personVO.getCalle(),
                    personVO.getCiudad(),
                    personVO.getCodPostal(),
                    personVO.getFechaNacimiento()
            );
            personList.add(person);
        }
        return personList;
    }

    public void agregarPersona(PersonVO nuevaPersona) {
        personRepository.guardarPersona(nuevaPersona);
    }

    public void eliminarPersona(int codigo) {
        personRepository.eliminarPersona(codigo);
    }

    public void actualizarPersona(PersonVO personaActualizada) {
        personRepository.actualizarPersona(personaActualizada);
    }

    public ArrayList<PersonVO> obtenerTodasLasPersonas() {
        return this.personRepository.ObtenerListaPersonas();
    }
}
