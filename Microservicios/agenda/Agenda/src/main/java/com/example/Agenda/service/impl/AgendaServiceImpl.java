package com.example.Agenda.service.impl;

import com.example.Agenda.model.PersonaDto;
import com.example.Agenda.model.PersonaVO;
import com.example.Agenda.repository.AgendaRepository;
import com.example.Agenda.service.AgendaService;
import com.example.Agenda.util.AgendaMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AgendaServiceImpl implements AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    /*
    * MÉTODOS DE GESTIÓN DE AGENDA
    */
    @Override
    public List<PersonaDto> getAllPersonas() {
        List<PersonaVO> personasDto = agendaRepository.findAll();
        return personasDto.stream()
                .map(AgendaMapper::agendaMapperEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonaDto> getPersonaById(String id) {
        Optional<PersonaVO> personaOptional = agendaRepository.findById(id);
        return personaOptional.map(AgendaMapper::agendaMapperEntityToDto);
    }

    @Override
    public List<PersonaDto> findByNombreContaining(String nombre) {
        List<PersonaVO> personaVO = agendaRepository.findByNombreContaining(nombre);
        return AgendaMapper.personaVOListMapperEntityToDto(personaVO);
    }

    @Override
    public List<PersonaDto> findByApellidosContaining(String apellidos) {
        List<PersonaVO> personaVO = agendaRepository.findByApellidosContaining(apellidos);
        return AgendaMapper.personaVOListMapperEntityToDto(personaVO);
    }

    @Override
    public PersonaDto updatePersona(PersonaDto personaDto) {
        Optional<PersonaVO> personaOptional = agendaRepository.findById(personaDto.getId());

        if (personaOptional.isPresent()) {
            PersonaVO personaVO = personaOptional.get();
            personaVO.setNombre(personaDto.getNombre());
            personaVO.setApellidos(personaDto.getApellidos());
            personaVO.setCalle(personaDto.getCalle());
            personaVO.setCodigoPostal(personaDto.getCodigoPostal());
            personaVO.setCiudad(personaDto.getCiudad());
            personaVO.setCumpleanos(personaDto.getCumpleanos());
            personaVO.setTutorials(personaDto.getTutorials());
            PersonaVO updatePersona = agendaRepository.save(personaVO);
            return AgendaMapper.agendaMapperEntityToDto(updatePersona);
        } else {
            return null;
        }
    }

    @Override
    public PersonaDto addPersona(PersonaDto personaDto) {
        PersonaVO personaVO = AgendaMapper.agendaMapperDtoToEntity(personaDto);
        PersonaVO createPersona = agendaRepository.save(personaVO);
        return AgendaMapper.agendaMapperEntityToDto(createPersona);
    }

    @Override
    public ResponseEntity deletePersona(String id) {
        System.out.println("ID recibido: " + id); // Para verificar el ID
        try {
            Optional<PersonaVO> personaOptional = agendaRepository.findById(id);
            if (personaOptional.isPresent()) {
                agendaRepository.deleteById(id);
                return ResponseEntity.ok("Persona eliminada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Persona no encontrada con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar a la persona");
        }
    }

    @Override
    public ResponseEntity deleteAllPersonas() {
        agendaRepository.deleteAll();
        ResponseEntity.ok("Personas eliminadas exitosamente");
        return ResponseEntity.ok().build();
    }
}
