package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.Reserva;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressIndicator;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EstadisticasReservasController {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private ProgressIndicator progressIndicator; // Nuevo componente para la ocupación actual

    private ObservableList<String> monthNames = FXCollections.observableArrayList();
    private HotelModelo hotelModelo;

    // Constantes de habitaciones totales por tipo
    private final int TOTAL_DOBLES_INDIVIDUALES = 20;
    private final int TOTAL_DOBLES = 80;
    private final int TOTAL_JUNIOR_SUITES = 15;
    private final int TOTAL_SUITES = 5;

    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    @FXML
    private void initialize() {
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        monthNames.addAll(Arrays.asList(months));
        xAxis.setCategories(monthNames);

        // Configuración del eje Y para porcentajes
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(100);
        yAxis.setTickUnit(10);
    }

    public void setReservaData(List<Reserva> reservas) {
        // Contadores de habitaciones ocupadas por tipo y mes
        int[][] ocupacionPorTipoYMes = new int[4][12]; // 4 tipos de habitación

        // Calcular ocupación por tipo y mes
        for (Reserva reserva : reservas) {
            int mes = reserva.getFechaLlegada().getMonthValue() - 1; // Mes actual (0-11)
            int numHabitaciones = reserva.getNumeroHabitaciones();
            TipoHabitacion tipo = reserva.getTipoHabitacion();

            switch (tipo) {
                case dobleIndividual -> ocupacionPorTipoYMes[0][mes] += numHabitaciones;
                case doble -> ocupacionPorTipoYMes[1][mes] += numHabitaciones;
                case juniorSuite -> ocupacionPorTipoYMes[2][mes] += numHabitaciones;
                case suite -> ocupacionPorTipoYMes[3][mes] += numHabitaciones;
            }
        }

        // Crear series de datos por tipo de habitación
        XYChart.Series<String, Number> seriesOcupacion = new XYChart.Series<>();
        seriesOcupacion.setName("Ocupación Total por Mes");

        for (int mes = 0; mes < 12; mes++) {
            int totalOcupadasMes = 0;

            // Calcular ocupación por tipo
            int ocupacionDobleIndividual = ocupacionPorTipoYMes[0][mes];
            int ocupacionDoble = ocupacionPorTipoYMes[1][mes];
            int ocupacionJuniorSuite = ocupacionPorTipoYMes[2][mes];
            int ocupacionSuite = ocupacionPorTipoYMes[3][mes];

            // Mostrar porcentajes individuales en la consola
            double porcentajeDobleIndividual = (ocupacionDobleIndividual / (double) TOTAL_DOBLES_INDIVIDUALES) * 100;
            double porcentajeDoble = (ocupacionDoble / (double) TOTAL_DOBLES) * 100;
            double porcentajeJuniorSuite = (ocupacionJuniorSuite / (double) TOTAL_JUNIOR_SUITES) * 100;
            double porcentajeSuite = (ocupacionSuite / (double) TOTAL_SUITES) * 100;

            // Imprimir porcentajes individuales
//            System.out.printf("Mes: %s\n", monthNames.get(mes));
//            System.out.printf("Doble Individual: %.2f%%\n", porcentajeDobleIndividual);
//            System.out.printf("Doble: %.2f%%\n", porcentajeDoble);
//            System.out.printf("Junior Suite: %.2f%%\n", porcentajeJuniorSuite);
//            System.out.printf("Suite: %.2f%%\n", porcentajeSuite);

            // Calcular el porcentaje total mensual
            totalOcupadasMes += ocupacionDobleIndividual + ocupacionDoble + ocupacionJuniorSuite + ocupacionSuite;
            double porcentajeMes = (totalOcupadasMes / 120.0) * 100; // 120 es el total de habitaciones

            // Imprimir porcentaje total mensual
//            System.out.printf("Porcentaje Total: %.2f%%\n\n", porcentajeMes);

            // Agregar los datos al gráfico
            seriesOcupacion.getData().add(new XYChart.Data<>(monthNames.get(mes), porcentajeMes));
        }

        // Limpiar y agregar la serie al gráfico
        barChart.getData().clear();
        barChart.getData().add(seriesOcupacion);
    }

}
