package com.example.Agenda.controller.impl;

import com.example.Agenda.controller.AgendaAPI;
import com.example.Agenda.model.PersonaDto;
import com.example.Agenda.repository.AgendaRepository;
import com.example.Agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AgendaController implements AgendaAPI {

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private AgendaRepository agendaRepository;

    @Override
    @GetMapping("/agenda")
    public List<PersonaDto> getAllPersonas() {
        return agendaService.getAllPersonas();
    }

    @Override
    @GetMapping("/agenda/{id}")
    public Optional<PersonaDto> getPersonaById(String id) {
        return agendaService.getPersonaById(id);
    }

    @Override
    @GetMapping("/agenda/nombre/{nombre}")
    public List<PersonaDto> findByNombreContaining(String nombre) {
        return agendaService.findByNombreContaining(nombre);
    }

    @Override
    @GetMapping("/agenda/apellidos/{apellidos}")
    public List<PersonaDto> findByApellidosContaining(String apellidos) {
        return agendaService.findByApellidosContaining(apellidos);
    }

    @Override
    @PutMapping("/agenda/{id}")
    public PersonaDto updatePersona(@RequestBody PersonaDto personaDto, @PathVariable String id) {
        return agendaService.updatePersona(personaDto);
    }

    @Override
    @PostMapping("/agenda")
    public PersonaDto addPersona(@RequestBody PersonaDto personaDto) {
        return agendaService.addPersona(personaDto);
    }

    @Override
    @DeleteMapping("/agenda/{id}")
    public ResponseEntity deletePersona(String id) {
        return agendaService.deletePersona(id);
    }

    @Override
    @DeleteMapping("/tutorials")
    public ResponseEntity deleteAllPersonas() {
        return agendaService.deleteAllPersonas();
    }
}
