package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EstadisticasReservasController {

    @FXML
    private BarChart<String, Number> barChart; // Cambiado a Number en lugar de Integer
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    private HotelModelo hotelModelo;

    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    @FXML
    private void initialize() {
        // Obtener un array con los nombres de los meses en inglés.
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));

        // Asignar los nombres de los meses como categorías para el eje X.
        xAxis.setCategories(monthNames);

        // Configuración del eje Y
        yAxis.setLowerBound(0);  // El valor mínimo será 0
        yAxis.setUpperBound(100); // El valor máximo será 100, ya que trabajamos con porcentajes
        yAxis.setTickUnit(10);    // Intervalos de 10 unidades
    }

    public void setReservaData(List<Reserva> reservas) {
        int[] occupiedRoomsByMonth = new int[12]; // Total de habitaciones ocupadas por mes
        int totalHabitacionesDisponibles = 120;  // Total de habitaciones en el hotel

        // Contar reservas por mes
        for (Reserva reserva : reservas) {
            int monthIndex = reserva.getFechaLlegada().getMonthValue() - 1;
            occupiedRoomsByMonth[monthIndex] += reserva.getNumeroHabitaciones();  // Sumar habitaciones reservadas en cada mes
        }

        // Crear una nueva serie de datos
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Porcentaje de Ocupación");  // Nombre de la serie

        // Calcular el porcentaje ocupado por mes y agregarlo a la serie
        for (int i = 0; i < 12; i++) {
            double porcentajeMensual = (occupiedRoomsByMonth[i] / (double) totalHabitacionesDisponibles) * 100;
            series.getData().add(new XYChart.Data<>(monthNames.get(i), porcentajeMensual));
        }

        // Limpiar datos previos y agregar la nueva serie al gráfico
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
