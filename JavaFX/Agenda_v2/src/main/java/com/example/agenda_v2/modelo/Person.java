package com.example.agenda_v2.modelo;

import javafx.beans.property.*;
import javafx.fxml.FXML;

import java.time.LocalDate;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person {
    public  Integer Identificador;
    @FXML
    private final StringProperty firstName;
    @FXML
    private final StringProperty lastName;
    @FXML
    private final StringProperty street;
    @FXML
    private final IntegerProperty postalCode;
    @FXML
    private final StringProperty city;
    @FXML
    private final ObjectProperty<LocalDate> birthday;

    /**d
     * Default constructor.
     */
    public Person() {
        this(null, null);
    }

    /**
     * Contructor solo escribes el nombre y apellido
     * @param firstName
     * @param lastName
     */
    public Person(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // Some initial dummy data, just for convenient testing.
        this.street = new SimpleStringProperty("some street");
        this.postalCode = new SimpleIntegerProperty(12345);
        this.city = new SimpleStringProperty("some city");
        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
    }

    public void setIdentificador(Integer identificador) {
        Identificador = identificador;
    }

    public Integer getIdentificador() {
        return Identificador;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public int getPostalCode() {
        return postalCode.get();
    }

    public IntegerProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }
}