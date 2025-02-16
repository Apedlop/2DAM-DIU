package com.example.tutorials.Tutorials.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.net.URL;

@Getter
@Setter
@Builder
public class Tutorials {
    @Id
    private String id;
    private String title;
    private String description;
    private Boolean published;
    private String imagen;

}

