package com.example.ejercicios.ContadorPuls.controler;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class controlerPuls {

    @FXML
    private Label cont;  // Etiqueta donde se mostrará el contador

    @FXML
    private TextField remplNum; // Campo de texto para introducir el número

    @FXML
    private ProgressBar progress; // Barra de progreso

    @FXML
    private int maxPuls = 50; // Máximo de pulsaciones

    @FXML
    private IntegerProperty numContador = new SimpleIntegerProperty(0);  // Contador almacenado como propiedad

    @FXML
    private void contador(int num) {     // Metodo que actualiza el valor del contador según el número recibido
        numContador.set((num == 0) ? 0 : numContador.get() + num);
        cont.setText(String.valueOf(numContador.get()));

        // Actualiza la barra de progreso
        double progreso = (double) numContador.get() / maxPuls; // Progreso en relación a 50
        progress.setProgress(progreso);
    }

    @FXML
    private void sumar() {
        contador(1);
    }

    @FXML
    private void restar() {
        contador(-1);
    }

    @FXML
    private void resetear() {
        contador(0);
    }

    @FXML
    private void remplazar() {
        // Lógica para reemplazar el contador con el número ingresado
        int nuevoValor = Integer.parseInt(remplNum.getText());
        numContador.set(nuevoValor);
        cont.setText(String.valueOf(nuevoValor));
        double progreso = (double) nuevoValor / maxPuls; // Progreso en relación a 50
        progress.setProgress(progreso);
    }

    @FXML
    private void initialize() {
        cont.setText(String.valueOf(numContador.get()));
        progress.setProgress(0.0); // Inicializa la barra de progreso
        numContador.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
                cont.setText(String.valueOf(newVal));
                // Calcula el progreso como un porcentaje de 50
                double progresoCalculado = newVal.doubleValue() / maxPuls; // maxPuls es 50
                progress.setProgress(progresoCalculado); // Establece el progreso en la barra
            }
        });
    }

    public IntegerProperty getContador() {
        return numContador;
    }
}
