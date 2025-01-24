package com.example.agenda_v2.modelo.util;

import com.example.agenda_v2.modelo.Person;
import com.example.agenda_v2.modelo.PersonVO;

import java.util.ArrayList;
import java.util.List;

public class PersonUtil {


     // Convertir PersonVO a Person
    public Person convertirPersona(PersonVO personVO){
        Person person = new Person();
        person.setIdentificador(personVO.getCodigo());
        person.setFirstName(personVO.getNombre());
        person.setLastName(personVO.getApellido());
        person.setStreet(personVO.getCalle());
        person.setPostalCode(personVO.getCodPostal());
        person.setCity(personVO.getCiudad());
        person.setBirthday(personVO.getFechaNacimiento());
        return person;
    }

    // ArrayList de PersonVO y devuelve un ArrayList de Person.
    public ArrayList<Person> lista(ArrayList<PersonVO> listaPersonVO){
        ArrayList<Person> listaPerson = new ArrayList<Person>();
        Person person = new Person();
        for(int i=0;i<listaPersonVO.size();i++){
            person=convertirPersona(listaPersonVO.get(i));
            listaPerson.add(i,person);
        }
        return listaPerson;
    }

    // Convierte un Person a PersonVO
    public PersonVO convertirPersonaVO(Person person){
        PersonVO personVO = new PersonVO();
        personVO.setCodigo(person.getIdentificador());
        personVO.setNombre(person.getFirstName());
        personVO.setApellido(person.getLastName());
        personVO.setCalle(person.getStreet());
        personVO.setCodPostal(person.getPostalCode());
        personVO.setCiudad(person.getCity());
        personVO.setFechaNacimiento(person.getBirthday());
        return personVO;
    }

    // Convierte una lista de Person a una lista de PersonVO.
    public ArrayList<PersonVO> listaPerson(ArrayList<Person> listaPerson){
        ArrayList<PersonVO> listaPersonVO = new ArrayList<PersonVO>();
        PersonVO personVO = new PersonVO();
        for(int i=0;i<listaPerson.size();i++){
            personVO=convertirPersonaVO(listaPerson.get(i));
            listaPersonVO.add(i,personVO);
        }
        return listaPersonVO;
    }
}
