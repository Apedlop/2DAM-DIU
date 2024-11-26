package com.example.examen_ex1ev1.controller;

import Modelo.ExcepcionMoneda;
import com.example.examen_ex1ev1.Main;
import com.example.examen_ex1ev1.modelo.ConversorModelo;
import com.example.examen_ex1ev1.modelo.Moneda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Float.parseFloat;

public class VPController {

    @FXML
    private TableView<Moneda> tablaMonedas;
    @FXML
    private TableColumn<Moneda, String> columnaNombreMoneda;
    @FXML
    private TextField moneda;
    @FXML
    private TextField euros;
    @FXML
    private Label monedaSeleccionada;

    private Main main;
    private ConversorModelo conversorModelo;
    private float mult;

    public VPController() {

    }

    public void setMain(Main main) {
        tablaMonedas.getItems();
        this.main = main;
    }

    public void setConversorModelo(ConversorModelo conversorModelo) {
        this.conversorModelo = conversorModelo;
    }

    private void mostrarMonedaSeleccionada(Moneda moneda) {
        if (moneda != null) {
            monedaSeleccionada.setText(moneda.getNombre());
        } else {
            monedaSeleccionada.setText("");
        }
    }

    @FXML
    public void initialize() {
        // Asigna las propiedades de las columnas del TableView para mostrar los datos de la persona.
        columnaNombreMoneda.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        tablaMonedas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            mostrarMonedaSeleccionada(newValue);
        });

    }

    public ArrayList<Moneda> tablaMonedas() throws ExcepcionMoneda, SQLException {
        cargarDatosMonedas();
        return conversorModelo.obtenerListaMonedas();
    }

    // Método para cargar y ordenar los clientes en la interfaz
    private void cargarDatosMonedas() throws ExcepcionMoneda, SQLException {
        // Obtener la lista de clientes desde el modelo
        ArrayList<Moneda> listaMonedas = conversorModelo.obtenerListaMonedas();

        // Convertir la lista a un ObservableList (para usar con TableView de JavaFX)
        ObservableList<Moneda> monedasData = FXCollections.observableArrayList(listaMonedas);

        // Asignar la lista ordenada a la tabla
        tablaMonedas.setItems(monedasData);
    }

    public float getMult() {
        Moneda monedaSelec = tablaMonedas.getSelectionModel().getSelectedItem();
        try {
            if (monedaSelec != null) {
                mult = conversorModelo.recuperarMult(monedaSelec.getNombre());
            }
        } catch (ExcepcionMoneda e) {
            throw new RuntimeException(e);
        }
        return mult;
    }

    public void EurosMoneda() {
        float cantidadIngresada = parseFloat(euros.getText());
        float cambioMoneda;
        try {
            cambioMoneda = conversorModelo.convertir(mult, cantidadIngresada);
            moneda.setText(cambioMoneda + "");
        } catch (NumberFormatException e) {
            moneda.setText("Entreda invalida");
        }
    }

    public void MonedaEuros() {
        float cantidadIngresada = parseFloat(moneda.getText());
        float cambioMoneda;
        try {
            cambioMoneda = conversorModelo.convertir(mult, cantidadIngresada);
            moneda.setText(String.format("%.2f", cambioMoneda));
        } catch (NumberFormatException e) {
            moneda.setText("Entreda invalida");
        }
    }

    @FXML
    public void convertirEuros() {
        EurosMoneda();

    }

    @FXML
    public void convertirMoneda() {

        MonedaEuros();
    }

    @FXML
    public void botonConversor() {
        float cantidadIngresada = Float.parseFloat(moneda.getText());
        float cambioMoneda;
        try {
            cambioMoneda = conversorModelo.convertir(mult, cantidadIngresada);
            moneda.setText(String.format("%.2f", cambioMoneda));
        } catch (NumberFormatException e) {
            moneda.setText("Entreda invalida");
        }
    }

    @FXML
    public void botonEliminar() {
        moneda.setText("");
        euros.setText("");
    }
}
