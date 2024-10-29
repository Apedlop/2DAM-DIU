package com.example.agenda_v2.controller;

import com.example.agenda_v2.modelo.AgendaModelo;
import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.PersonVO;
import com.example.agenda_v2.modelo.repository.PersonRepository;
import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class AgendaController {

    @FXML
    private TableView<PersonVO> personTable;
    @FXML
    private TableColumn<PersonVO, String> firstNameColumn;
    @FXML
    private TableColumn<PersonVO, String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Button newPersonButton;
    @FXML
    private Button editPersonButton;
    @FXML
    private Button deletePersonButton;

    private PersonRepository personRepository;
    private ObservableList<PersonVO> personList;
    private AgendaModelo agendaModelo; // Cambia a AgendaModelo

    public AgendaController() {
        this.personRepository = new PersonRepositoryImpl(); // Inicializa el repositorio
        this.agendaModelo = new AgendaModelo(personRepository); // Pasa el repositorio a AgendaModelo
        this.personList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("apellido"));

        // Cargar la lista de personas
        loadPersonData();

        // Configurar el evento de selección de la tabla
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    private void loadPersonData() {
        try {
            ArrayList<PersonVO> personas = agendaModelo.obtenerTodasLasPersonas(); // Usa el método del modelo
            personList.setAll(personas);
            personTable.setItems(personList);
        } catch (ExceptionPersona e) {
            // Manejo de errores
            e.printStackTrace();
        }
    }

    private void showPersonDetails(PersonVO person) {
        if (person != null) {
            firstNameLabel.setText(person.getNombre());
            lastNameLabel.setText(person.getApellido());
            streetLabel.setText(person.getCalle());
            cityLabel.setText(person.getCiudad());
            postalCodeLabel.setText(String.valueOf(person.getCodPostal()));
            birthdayLabel.setText(person.getFechaNacimiento().toString());
        } else {
            clearPersonDetails(); // Limpia los detalles si no hay selección
        }
    }

    private void clearPersonDetails() {
        firstNameLabel.setText("");
        lastNameLabel.setText("");
        streetLabel.setText("");
        cityLabel.setText("");
        postalCodeLabel.setText("");
        birthdayLabel.setText("");
    }

    @FXML
    private void handleNewPerson() {
        // Lógica para añadir una nueva persona
    }

    @FXML
    private void handleEditPerson() {
        // Lógica para editar la persona seleccionada
    }

    @FXML
    private void handleDeletePerson() {
        // Obtener el objeto PersonVO seleccionado en la tabla
        PersonVO selectedPerson = personTable.getSelectionModel().getSelectedItem();

        // Verificar si hay una persona seleccionada
        if (selectedPerson != null) {
            int codigo = selectedPerson.getCodigo(); // Obtiene el código de la persona seleccionada

            try {
                agendaModelo.eliminarPersona(codigo); // Elimina la persona usando el código
                loadPersonData(); // Recargar la lista después de la eliminación
            } catch (ExceptionPersona e) {
                // Manejo de errores
                e.printStackTrace();
            }
        } else {
            // Mostrar mensaje de advertencia si no se seleccionó ninguna persona
            System.out.println("Por favor, selecciona una persona para eliminar.");
        }
    }


    public int getCodigo() {
        PersonVO selectedPerson = personTable.getSelectionModel().getSelectedItem();
        return selectedPerson != null ? selectedPerson.getCodigo() : -1; // Retorna el código o -1 si no hay selección
    }
}
