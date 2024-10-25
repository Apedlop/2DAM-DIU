package com.example.agenda_v2.modelo;

import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;

import java.util.Iterator;

public class AgendaModelo {

    private PersonRepositoryImpl personRepository;

    public void setPersonRepository(PersonRepositoryImpl personRepository) {
        this.personRepository = personRepository;
    }

    public PersonVO recuperarPersona(int codigo)  {
        Iterator<PersonVO> it = this.personRepository.ObtenerListaPersonas().iterator();
        PersonVO person = null;
        while (it.hasNext()) {
            PersonVO personVO = it.next();
            if (personVO.getCodigo().equals(codigo)) {
                person = personVO;
            }
        }
        return person;
    }

}
