package com.example.tutorials.Tutorials.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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

