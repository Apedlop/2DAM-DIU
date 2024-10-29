package com.example.agenda_v2;

import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.PersonVO;
import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    private PersonRepositoryImpl personRepository;

    public static void main(String[] args) {
        launch(); // Lanza la aplicación JavaFX
    }

    @Override
    public void init() {
        try {
            // Inicializa el repositorio y carga datos iniciales
            personRepository = new PersonRepositoryImpl();
            cargarDatosIniciales();
        } catch (Exception e) {
            System.err.println("Error al inicializar datos: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace para facilitar la depuración
        }
    }

    private void cargarDatosIniciales() {
        try {
            PersonVO persona = new PersonVO("Juan", "García", "C/ Gran Vía", "Madrid", 28013, LocalDate.parse("1990-05-15"));
            personRepository.guardarPersona(persona);
            // Agrega más datos iniciales si es necesario
        } catch (ExceptionPersona e) {
            System.err.println(e.imprimirMensaje());
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/example/agenda_v2/Agenda_v2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Agenda SOLID");
        stage.setScene(scene);
        stage.show();
    }

    public PersonRepositoryImpl getPersonRepository() {
        return personRepository;
    }
}
