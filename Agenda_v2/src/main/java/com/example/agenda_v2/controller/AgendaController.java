package com.example.agenda_v2.controller;

import com.example.agenda_v2.Main;
import com.example.agenda_v2.modelo.AgendaModelo;
import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.Person;
import com.example.agenda_v2.modelo.PersonVO;
import com.example.agenda_v2.modelo.util.DateUtil;
import com.example.agenda_v2.modelo.util.PersonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class AgendaController {

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
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

    private Main main;
    private AgendaModelo agendaModelo;
    private PersonUtil personUtil = new PersonUtil();
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    // Método para recibir el modelo y configurar los datos
    public void setAgendaModelo(AgendaModelo agendaModelo) throws ExceptionPersona {
        this.agendaModelo = agendaModelo;
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Escucha cambios de selección en la tabla de personas
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> mostrarDetallesPersona(newValue));
    }

    // Muestra detalles de una persona
    private void mostrarDetallesPersona(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    // Agrega una nueva persona al modelo y a la tabla
    public void agregarPersona(Person person) throws ExceptionPersona {
        PersonVO personVO = personUtil.convertirPersonaVO(person);
        agendaModelo.agregarPersona(personVO);
        personData.add(person);
    }

    // Actualiza los datos de una persona en el modelo
    public void actualizarPersona(Person person) throws ExceptionPersona {
        PersonVO personVO = personUtil.convertirPersonaVO(person);
        agendaModelo.actualizarPersona(personVO);
    }

    /*
     * Acciones de los botones
     */

    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personUtil = new PersonUtil();
            try {
                agendaModelo.eliminarPersona(personUtil.convertirPersonaVO(personTable.getItems().get(selectedIndex)));
                agendaModelo.decrementarContadorPersonas();
            }catch (ExceptionPersona e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al eliminar la persona");
                alert.setTitle("Error con la base de datos");
                alert.setContentText("No se puede conectar con la base de datos para eliminar la persona");
                alert.showAndWait();
            }
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("No Selection");
            alerta.setHeaderText("No Person Selected");
            alerta.setContentText("Please select a person in the table.");
            alerta.showAndWait();
        }
    }

    @FXML
    private void handleNewPerson() throws ExceptionPersona {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked) {
            try{
                tempPerson.setIdentificador(agendaModelo.obtenerNuevoCodigoPersona() + 1);
                CrearPersonAPersonVO(tempPerson);
                main.getPersonData().add(tempPerson);

            }catch(ExceptionPersona e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al añadir la persona");
                alert.setTitle("Error con la base de datos");
                alert.setContentText("No se puede conectar con la base de datos para añadir la persona");
                alert.showAndWait();
            }
        }

    }

    public void CrearPersonAPersonVO(Person person) throws ExceptionPersona {
        personUtil = new PersonUtil();
        PersonVO personVO = new PersonVO();
        personVO =  personUtil.convertirPersonaVO(person);
        agendaModelo.agregarPersona(personVO);
        agendaModelo.incrementarContadorPersonas();
    }

    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            if (showPersonEditDialog(selectedPerson)) {
                try {
                    actualizarPersona(selectedPerson);
                    mostrarDetallesPersona(selectedPerson);
                } catch (ExceptionPersona e) {
                    mostrarError("Error al editar la persona", "No se puede conectar con la base de datos para editar la persona.");
                }
            }
        } else {
            mostrarInfo("No Selection", "No Person Selected", "Please select a person in the table.");
        }
    }

    // Método auxiliar para mostrar errores
    private void mostrarError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Método auxiliar para mostrar información
    private void mostrarInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Placeholder para abrir diálogo de edición (implementar en Main)
    private boolean showPersonEditDialog(Person person) {
        return false; // Implementación pendiente
    }

    public void setMain(Main main) {
        this.main = main;
        personTable.setItems(main.getPersonData());
    }

}
