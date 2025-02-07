package com.example.Agenda.repository;

import com.example.Agenda.model.PersonaVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends MongoRepository<PersonaVO, String> {
    Optional<PersonaVO> getAgendaById(String id);
    List<PersonaVO> findAll();
    List<PersonaVO> findByNombreContaining(String nombre);
    List<PersonaVO> findByApellidosContaining(String apellidos);
    List<PersonaVO> findByCalleContaining(String calle);
    List<PersonaVO> findByCodigoPostalContaining(Integer codigoPostal);
    List<PersonaVO> findByCiudadContaining(String ciudad);
    List<PersonaVO> findByCumpleanosContaining(LocalDate cumpleano);
}
