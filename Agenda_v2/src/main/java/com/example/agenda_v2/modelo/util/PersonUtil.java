package com.example.agenda_v2.modelo.util;

import com.example.agenda_v2.modelo.Person;
import com.example.agenda_v2.modelo.PersonVO;
import eu.hansolo.toolbox.properties.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Date;

public class PersonUtil {

    ArrayList<PersonVO> personRepositoryArrayList = new ArrayList<>();
    ArrayList<Person> personArrayList = new ArrayList<>();

    // Que transforme de lista de personVO a lista de person y otro al revés

    // devuelve un arrayList de tipo person
    public ArrayList<Person> convierteVOAPerson() {
        for (PersonVO personVO : personRepositoryArrayList) {
            personArrayList.add(new Person(personVO.getNombre(), personVO.getApellido(), personVO.getCalle(), personVO.getCiudad(), personVO.getCodigo(), personVO.getFechaNacimiento()));
        }
        return personArrayList;
    }

    // devuelve un arrayList de tipo personVO
    public ArrayList<PersonVO> convierteVOAPersonVO() {
        for (Person person : personArrayList) {
            personRepositoryArrayList.add(new PersonVO(person.getFirstName(), person.getLastName(), person.getStreet(), person.getCity(), person.getPostalCode(), person.getBirthday()));
        }
        return personRepositoryArrayList;
    }


}
