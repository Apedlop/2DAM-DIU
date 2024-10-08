package com.example.agenda;

import java.io.IOException;

import com.example.agenda.controller.ControllerPersona;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Agenda extends Application {

    // ... AFTER THE OTHER VARIABLES ...

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Persona> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Agenda() {
        // Add some sample data
        personData.add(new Persona("Hans", "Muster"));
        personData.add(new Persona("Ruth", "Mueller"));
        personData.add(new Persona("Heinz", "Kurz"));
        personData.add(new Persona("Cornelia", "Meier"));
        personData.add(new Persona("Werner", "Meyer"));
        personData.add(new Persona("Lydia", "Kunz"));
        personData.add(new Persona("Anna", "Best"));
        personData.add(new Persona("Stefan", "Meier"));
        personData.add(new Persona("Martin", "Mueller"));
    }

    /**
     * Returns the data as an observable list of Persons.
     *
     * @return
     */
    public ObservableList<Persona> getPersonData() {
        return personData;
    }

    // ... THE REST OF THE CLASS ...

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/agenda/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Agenda.class.getResource("/com/example/agenda/Agenda.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ControllerPersona controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}