package com.example.agenda_v2.controller;

import com.example.agenda_v2.modelo.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class BirthdayStatisticsController {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Obtenemos un array de los nombres de los meses en Inglés
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convertimos en una lista y lo agregamos a un ObservableList de meses.
        monthNames.addAll(Arrays.asList(months));

        // Asignamos los nombres de los meses como categorías para el eje horizontal(x).
        xAxis.setCategories(monthNames);
    }

    // Establecemos las personas para mostrar las estadísticas correspondientes
    public void setPersonData(List<Person> persons) {
        // Contamos el número de personas que cumplen años en el mismo mes
        int[] monthCounter = new int[12];
        for (Person p : persons) {
            int month = p.getBirthday().getMonthValue() - 1;
            monthCounter[month]++;
        }

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        //  Creamos un objeto XYChart.Data para cada mes y lo agregamos a la serie.
        for (int i = 0; i < monthCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
        }

        barChart.getData().add(series);
    }

}
