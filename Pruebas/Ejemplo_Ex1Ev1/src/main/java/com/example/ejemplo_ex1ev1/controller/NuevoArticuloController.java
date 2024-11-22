package com.example.ejemplo_ex1ev1.controller;

import com.example.ejemplo_ex1ev1.modelo.Articulo;
import com.example.ejemplo_ex1ev1.modelo.CatalogoModelo;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.ArcTo;
import javafx.stage.Stage;

public class NuevoArticuloController {

    @FXML
    private Label numArticulos;
    @FXML
    private TextField nombre;
    @FXML
    private TextField descripcion;
    @FXML
    private TextField precio;
    @FXML
    private TextField stock;

    private Stage stage;
    private Articulo articulo;
    private CatalogoModelo catalogoModelo;
    private boolean okClicked = false;
    private IntegerProperty numeroArticulos = new SimpleIntegerProperty();

    public void setCatalogoModelo(CatalogoModelo catalogoModelo) {
        this.catalogoModelo = catalogoModelo;
    }

    public NuevoArticuloController() {

    }

    public void setNumArticulos() {
        this.numeroArticulos.bind(catalogoModelo.getNumArticulos());
        numArticulos.setText(String.valueOf(numeroArticulos.get()));
        numeroArticulos.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                numArticulos.setText(String.valueOf(numeroArticulos.get()));
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        nombre.setText(articulo.getNombre());
        descripcion.setText(articulo.getDescripcion());
        precio.setText(String.valueOf(articulo.getPrecio()));
        stock.setText(Integer.toString(articulo.getStock()));
    }

    private boolean isValido() {
        String errorMessage = "";

        if (nombre.getText() == null || nombre.getText().length() == 0) {
            errorMessage += "Nombre invalido\n";
        }
        if (descripcion.getText() == null || descripcion.getText().length() == 0) {
            errorMessage += "Descripcion invalida\n";
        }
        if (precio.getText() == null || precio.getText().length() == 0) {
            errorMessage += "Precio invalido\n";
        }else {
            try {
                Float.parseFloat(precio.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Debe introducir un numero\n";
            }
        }

        if (stock.getText() == null || stock.getText().length() == 0) {
            errorMessage += "Stock invalido\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(stock.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Debe introducir un numero\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Campos invalidos");
            alerta.setHeaderText("Introduzca correctamente los campos");
            alerta.setContentText(errorMessage);
            alerta.showAndWait();
            return false;
        }
    }

    @FXML
    public void botonAceptar() {
        if (isValido()) {
            articulo.setNombre(nombre.getText());
            articulo.setDescripcion(descripcion.getText());
            articulo.setPrecio(Float.parseFloat(precio.getText()));
            articulo.setStock(Integer.parseInt(precio.getText()));
            okClicked = true;
            stage.close();
        }
    }

    @FXML
    public void botonCancelar() {
        stage.close();
    }

}
