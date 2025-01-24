package com.example.agenda_v2;

import com.example.agenda_v2.controller.AgendaController;
import com.example.agenda_v2.controller.BirthdayStatisticsController;
import com.example.agenda_v2.controller.EditarPersonaController;
import com.example.agenda_v2.controller.RootLayoutController;
import com.example.agenda_v2.modelo.AgendaModelo;
import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.Person;
import com.example.agenda_v2.modelo.PersonVO;
import com.example.agenda_v2.modelo.repository.PersonRepository;
import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;
import com.example.agenda_v2.modelo.util.PersonUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private AgendaModelo agendaModelo;
    private PersonUtil personUtil;
    private PersonRepositoryImpl personRepository;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    // Constructor donde se inicializan el modelo y la lista de datos de personas
    public Main() {
        personData.addAll(addList());
    }

    public ArrayList<Person> addList() {
        personUtil = new PersonUtil();
        personRepository = new PersonRepositoryImpl();
        agendaModelo = new AgendaModelo();
        agendaModelo.setPersonRepository(personRepository);

        ArrayList<PersonVO> listaPersonVO = new ArrayList<PersonVO>();
        ArrayList<Person> listaPerson = new ArrayList<Person>();

        try {
            listaPersonVO = agendaModelo.listarPersonas();
            agendaModelo.setNumPersonas(listaPersonVO.size());
        } catch (ExceptionPersona e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al listar las personas.");
            alert.setTitle("Error con la base de datos");
            alert.setContentText("No se puede conectar con la base de datos");
            alert.showAndWait();
            return listaPerson; // Retornar lista vacía si hay error
        } catch (Exception e) { // Captura errores generales
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error inesperado.");
            alert.setTitle("Error");
            alert.setContentText("Ha ocurrido un error: " + e.getMessage());
            alert.showAndWait();
            return listaPerson; // Retornar lista vacía si hay error
        }

        listaPerson = personUtil.lista(listaPersonVO);
        return listaPerson;
    }


    // Método auxiliar para mostrar alertas de error
    private void mostrarAlertaError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setTitle("Error con la base de datos");
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Devuelve una lista de Person
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Agenda SOLID");
        this.primaryStage.getIcons().add(new Image("file:resources/image/iconoAgenda.png"));

        initRootLayout();
        showPersonOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/agenda_v2/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            RootLayoutController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPersonOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/agenda_v2/Agenda_v2.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(personOverview);

            AgendaController controller = loader.getController();
            controller.setAgendaModelo(agendaModelo);
            controller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExceptionPersona e) {
            mostrarAlertaError("Error al cargar la vista de personas", e.getMessage());
        }
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/agenda_v2/EditarPersona.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.NONE);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditarPersonaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAgendaModelo(agendaModelo);
            controller.setPerson(person);

            controller.setBarrita();
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showBirthdayStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/example/agenda_v2/BirthdayStatistics.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            BirthdayStatisticsController controller = loader.getController();
            controller.setPersonData(personData);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
