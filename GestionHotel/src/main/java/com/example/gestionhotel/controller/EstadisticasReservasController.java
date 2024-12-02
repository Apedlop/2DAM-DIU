package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.tablas.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.converter.NumberStringConverter;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EstadisticasReservasController {

    @FXML
    private BarChart<String, Integer> barChart; // Asegúrate de que la serie Y sea Integer
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private Integer max = 120;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Obtener un array con los nombres de los meses en inglés.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        // Convertirlo a una lista y agregarla a nuestro ObservableList de meses.
        monthNames.addAll(Arrays.asList(months));

        // Asignar los nombres de los meses como categorías para el eje X.
        xAxis.setCategories(monthNames);

        // Configuración del eje Y
        yAxis.setLowerBound(0);  // El valor mínimo será 0
        yAxis.setUpperBound(120); // El valor máximo será 120
        yAxis.setTickUnit(10);    // Las unidades de las divisiones serán de 10 en 10
        yAxis.setTickLabelsVisible(true);
    }

    public void setReservaData(List<Reserva> reservas) {
        int[] monthCounterL = new int[12];
        int[] occupiedRoomsByMonth = new int[12];
        for (Reserva p : reservas) {
            int monthL = p.getFechaLlegada().getMonthValue() - 1;
            int room = p.numeroHabitacionesProperty().get();
            occupiedRoomsByMonth[monthL] = occupiedRoomsByMonth[monthL] + room;
            monthCounterL[monthL]++;
        }

        // Creando la serie de datos con tipo Integer en lugar de Double
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (int i = 0; i < monthCounterL.length; i++) {
            // Aquí, eliminamos el cálculo del porcentaje en double y usamos un valor entero
            int valor = occupiedRoomsByMonth[i];  // Mostramos las habitaciones ocupadas directamente
            series.getData().add(new XYChart.Data<>(monthNames.get(i), valor));
        }

        barChart.getData().add(series);
    }
}
