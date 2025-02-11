package com.example.tutorials.Tutorials.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class TutorialsDto {

    private String id;
    private String title;
    private String description;
    private Boolean published;
    private URL imagen;

}
