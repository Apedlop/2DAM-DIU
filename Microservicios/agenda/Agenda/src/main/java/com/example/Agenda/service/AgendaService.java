package com.example.Agenda.service;

import com.example.Agenda.model.PersonaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AgendaService {

    // Métodos de gestión de Personas de la agenda
    List<PersonaDto> getAllPersonas();
    Optional<PersonaDto> getPersonaById(String id);
    List<PersonaDto> findByNombreContaining(String nombre);
    List<PersonaDto> findByApellidosContaining(String apellidos);

    PersonaDto updatePersona(PersonaDto personaDto);
    PersonaDto addPersona(PersonaDto personaDto);
    ResponseEntity deletePersona(String id);
    ResponseEntity deleteAllPersonas();

    // Métodos para la gestión de login

}
