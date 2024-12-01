package com.example.gestionhotel.controller;

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
    private BarChart<String, Number> estadisticas; // El gráfico de barras
    @FXML
    private CategoryAxis xAxis;  // El eje X que representa los meses
    @FXML
    private NumberAxis yAxis; // Eje Y para la cantidad de reservas

    private ObservableList<String> nombreMeses = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Cargar los nombres de los meses
        String[] meses = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        nombreMeses.addAll(Arrays.asList(meses));
        xAxis.setCategories(nombreMeses);

        // Configuración del eje Y para que tenga un rango visible adecuado
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(10);  // Ajusta según la cantidad máxima de reservas esperadas
        yAxis.setTickUnit(1);
    }

    /**
     * Método que recibe las reservas y las procesa para mostrar las estadísticas
     * @param reservaData Lista de reservas
     */
    public void setReservasMeses(List<Reserva> reservaData) {
        // Arreglo para contar las reservas por mes (índice 0 = enero, 11 = diciembre)
        int[] reservasPorMes = new int[12];

        // Verificación de si hay datos
        if (reservaData == null || reservaData.isEmpty()) {
            System.out.println("No hay reservas para mostrar.");
            return;  // Si no hay datos, no procesar nada
        }

        // Iterar sobre las reservas para contar cuántas ocurren en cada mes
        for (Reserva reserva : reservaData) {
            if (reserva.getFechaLlegada() != null) {
                // Verifica la fecha de llegada de la reserva
                System.out.println("Fecha de llegada de la reserva: " + reserva.getFechaLlegada());

                // Obtener el mes de la fecha de llegada
                int mes = reserva.getFechaLlegada().getMonthValue() - 1;  // Ajustar a 0-11
                if (mes >= 0 && mes < 12) {
                    reservasPorMes[mes]++;  // Incrementar el conteo para ese mes
                } else {
                    System.out.println("Fecha fuera de rango: " + reserva.getFechaLlegada());
                }
            } else {
                System.out.println("Reserva sin fecha de llegada.");
            }
        }

        // Crear la serie de datos para el gráfico de barras
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Reservas por Mes");

        // Añadir los datos de cada mes
        for (int i = 0; i < 12; i++) {
            System.out.println("Mes: " + nombreMeses.get(i) + " - Reservas: " + reservasPorMes[i]);  // Depuración
            series.getData().add(new XYChart.Data<>(nombreMeses.get(i), reservasPorMes[i]));
        }

        // Limpiar y agregar la nueva serie de datos al gráfico
        estadisticas.getData().clear();
        estadisticas.getData().add(series);
    }
}
