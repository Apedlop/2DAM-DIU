package com.example.agenda_v2.modelo;

import com.example.agenda_v2.modelo.repository.PersonRepository; // Cambiado a la interfaz
import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;

import java.util.ArrayList;

public class AgendaModelo {

    private PersonRepository personRepository; // Cambiado a la interfaz

    // Constructor que acepta un PersonRepository
    public AgendaModelo(PersonRepository personRepository) {
        this.personRepository = personRepository; // Inicializa el repositorio
    }

    public PersonVO recuperarPersonaCodigo(int codigo) {
        for (PersonVO personVO : this.personRepository.ObtenerListaPersonas()) {
            if (personVO.getCodigo() == codigo) { // Usa '==' para comparación de primitivos
                return personVO; // Retorna la persona encontrada
            }
        }
        return null; // Retorna null si no se encuentra la persona
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
