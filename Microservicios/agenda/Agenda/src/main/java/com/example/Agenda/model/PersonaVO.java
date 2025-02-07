package com.example.Agenda.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@Builder
public class PersonaVO {
    @Id
    private String id;
    private String nombre;
    private String apellidos;
    private String calle;
    private Integer codigoPostal;
    private String ciudad;
    private LocalDate cumpleanos;
    private ArrayList<String> tutorials;
}
