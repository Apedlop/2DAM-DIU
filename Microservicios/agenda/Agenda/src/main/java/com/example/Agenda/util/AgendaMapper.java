package com.example.Agenda.util;

import com.example.Agenda.model.PersonaDto;
import com.example.Agenda.model.PersonaVO;

import java.util.List;
import java.util.stream.Collectors;

public class AgendaMapper {

    public static PersonaVO agendaMapperDtoToEntity(PersonaDto Persona){
        return PersonaVO.builder()
                .id(Persona.getId())
                .nombre(Persona.getNombre())
                .apellidos(Persona.getApellidos())
                .calle(Persona.getCalle())
                .codigoPostal(Persona.getCodigoPostal())
                .ciudad(Persona.getCiudad())
                .cumpleanos(Persona.getCumpleanos())
                .tutorials(Persona.getTutorials())
                .build();
    }

    public static PersonaDto agendaMapperEntityToDto(PersonaVO personaVO){
        return PersonaDto.builder()
                .id(personaVO.getId())
                .nombre(personaVO.getNombre())
                .apellidos(personaVO.getApellidos())
                .calle(personaVO.getCalle())
                .codigoPostal(personaVO.getCodigoPostal())
                .ciudad(personaVO.getCiudad())
                .cumpleanos(personaVO.getCumpleanos())
                .tutorials(personaVO.getTutorials())
                .build();
    }


    public static List<PersonaVO> personaVOListMapperDtoToEntity(List<PersonaDto> PersonaDtoList) {
        return PersonaDtoList.stream()
                .map(AgendaMapper::agendaMapperDtoToEntity)
                .collect(Collectors.toList());
    }


    public static List<PersonaDto> personaVOListMapperEntityToDto(List<PersonaVO> PersonaVOList) {
        return PersonaVOList.stream()
                .map(AgendaMapper::agendaMapperEntityToDto)
                .collect(Collectors.toList());
    }

}
