package com.example.Agenda.controller;

import com.example.Agenda.model.PersonaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AgendaAPI {
    List<PersonaDto> getAllPersonas();
    Optional<PersonaDto> getPersonaById(String id);
    List<PersonaDto> findByNombreContaining(String nombre);
    List<PersonaDto> findByApellidosContaining(String apellidos);

    PersonaDto updatePersona(PersonaDto personaDto, String id);
    PersonaDto addPersona(PersonaDto personaDto);
    ResponseEntity deletePersona(String id);
    ResponseEntity deleteAllPersonas();

}
