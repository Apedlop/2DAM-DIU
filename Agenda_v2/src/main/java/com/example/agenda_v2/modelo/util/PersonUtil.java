package com.example.agenda_v2.modelo.util;

import com.example.agenda_v2.modelo.Person;
import com.example.agenda_v2.modelo.PersonVO;

import java.util.ArrayList;
import java.util.List;

public class PersonUtil {

    // Convierte una lista de PersonVO a una lista de Person
    public List<Person> convierteVOaPerson(List<PersonVO> personVOList) {
        List<Person> personList = new ArrayList<>();
        for (PersonVO personVO : personVOList) {
            personList.add(new Person(personVO.getNombre(), personVO.getApellido(), personVO.getCalle(), personVO.getCiudad(), personVO.getCodPostal(), personVO.getFechaNacimiento()));
        }
        return personList;
    }

    // Convierte una lista de Person a una lista de PersonVO
    public List<PersonVO> convierteAPersonVO(List<Person> personList) {
        List<PersonVO> personVOList = new ArrayList<>();
        for (Person person : personList) {
            personVOList.add(new PersonVO(person.getFirstName(), person.getLastName(), person.getStreet(), person.getCity(), person.getPostalCode(), person.getBirthday()));
        }
        return personVOList;
    }
}
