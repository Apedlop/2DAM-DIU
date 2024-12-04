package com.example.gestionhotel.controller;

import com.example.gestionhotel.modelo.ExeptionHotel;
import com.example.gestionhotel.modelo.HotelModelo;
import com.example.gestionhotel.modelo.tablas.TipoHabitacion;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class TipoHabitacionesController {

    // FXML para las imágenes de las habitaciones
    @FXML
    private ImageView imagenDobleInvididual;
    @FXML
    private ImageView imagenDoble;
    @FXML
    private ImageView imagenJuniorSuite;
    @FXML
    private ImageView imagenSuite;

    // FXML para los ProgressIndicators
    @FXML
    private ProgressIndicator progressDobleInvididual;
    @FXML
    private ProgressIndicator progressDoble;
    @FXML
    private ProgressIndicator progressJuniorSuite;
    @FXML
    private ProgressIndicator progressSuite;

    // ImageView para las flechas de navegación
    @FXML private ImageView flechaDerechaDobleInvididualView;
    @FXML private ImageView flechaIzquierdaDobleInvididualView;
    @FXML private ImageView flechaDerechaDobleView;
    @FXML private ImageView flechaIzquierdaDobleView;
    @FXML private ImageView flechaDerechaJuniorSuiteView;
    @FXML private ImageView flechaIzquierdaJuniorSuiteView;
    @FXML private ImageView flechaDerechaSuiteView;
    @FXML private ImageView flechaIzquierdaSuiteView;

    // Definir las listas de imágenes para cada tipo de habitación
    private List<Image> imagenesDobleInvididual;
    private List<Image> imagenesDoble;
    private List<Image> imagenesJuniorSuite;
    private List<Image> imagenesSuite;

    // Variables de índice para controlar qué imagen mostrar
    private int indiceDobleInvididual = 0;
    private int indiceDoble = 0;
    private int indiceJuniorSuite = 0;
    private int indiceSuite = 0;

    private HotelModelo hotelModelo;

    // Constructor vacío
    public TipoHabitacionesController() {
    }

    // Inyectamos el modelo
    public void setHotelModelo(HotelModelo hotelModelo) {
        this.hotelModelo = hotelModelo;
    }

    // Método de inicialización para cargar las imágenes y configurar los progress indicators
    public void inicializarDatos() {
        // Cargar las imágenes en las listas
        cargarImagenes();

        // Configurar imágenes iniciales
        actualizarImagen(imagenDobleInvididual, imagenesDobleInvididual, indiceDobleInvididual);
        actualizarImagen(imagenDoble, imagenesDoble, indiceDoble);
        actualizarImagen(imagenJuniorSuite, imagenesJuniorSuite, indiceJuniorSuite);
        actualizarImagen(imagenSuite, imagenesSuite, indiceSuite);

        // Actualizar el progreso de las habitaciones
        actualizarProgresoHabitaciones();
    }

    // Método para cargar las imágenes en las listas correspondientes
    private void cargarImagenes() {
        // Inicializar las listas
        imagenesDobleInvididual = new ArrayList<>();
        imagenesDoble = new ArrayList<>();
        imagenesJuniorSuite = new ArrayList<>();
        imagenesSuite = new ArrayList<>();

        // Asegurarse de que las rutas a las imágenes sean correctas
        try {
            imagenesDobleInvididual.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/dobleInvididual1.jpg")));
            imagenesDobleInvididual.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/dobleInvididual2.jpg")));
            imagenesDobleInvididual.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/dobleInvididual3.jpg")));

            imagenesDoble.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/doble1.jpg")));
            imagenesDoble.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/doble2.jpg")));
            imagenesDoble.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/doble3.jpg")));

            imagenesJuniorSuite.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/juniorSuite1.jpg")));
            imagenesJuniorSuite.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/juniorSuite2.jpg")));
            imagenesJuniorSuite.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/juniorSuite3.jpg")));

            imagenesSuite.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/suite1.jpg")));
            imagenesSuite.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/suite2.jpg")));
            imagenesSuite.add(new Image(getClass().getResourceAsStream("/com/example/gestionhotel/images/suite3.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
        }
    }

    // Método para actualizar la imagen mostrada en el ImageView
    private void actualizarImagen(ImageView imageView, List<Image> imagenes, int indice) {
        if (!imagenes.isEmpty()) {
            imageView.setImage(imagenes.get(indice));
        }
    }

    // Método para actualizar el progreso de ocupación de habitaciones
    private void actualizarProgresoHabitaciones() {
        try {
            // Obtener el número de reservas por tipo de habitación
            int reservasDobleInvididual = hotelModelo.habitacionesOcupadas(TipoHabitacion.dobleIndividual);
            int reservasDoble = hotelModelo.habitacionesOcupadas(TipoHabitacion.doble);
            int reservasJuniorSuite = hotelModelo.habitacionesOcupadas(TipoHabitacion.juniorSuite);
            int reservasSuite = hotelModelo.habitacionesOcupadas(TipoHabitacion.suite);

            // Número máximo de habitaciones por tipo
            int maxDobleInvididual = 20;
            int maxDoble = 80;
            int maxJuniorSuite = 15;
            int maxSuite = 5;

            // Calcular el porcentaje de ocupación y actualizar los ProgressBar
            double porcentajeDobleInvididual = (double) reservasDobleInvididual / maxDobleInvididual;
            double porcentajeDoble = (double) reservasDoble / maxDoble;
            double porcentajeJuniorSuite = (double) reservasJuniorSuite / maxJuniorSuite;
            double porcentajeSuite = (double) reservasSuite / maxSuite;

            // Actualizar la interfaz de usuario con el progreso
            progressDobleInvididual.setProgress(porcentajeDobleInvididual);
            progressDoble.setProgress(porcentajeDoble);
            progressJuniorSuite.setProgress(porcentajeJuniorSuite);
            progressSuite.setProgress(porcentajeSuite);

        } catch (ExeptionHotel e) {
            System.err.println("Error al contar las reservas por tipo de habitación: " + e.getMessage());
        }
    }

    // Métodos para manejar la navegación de imágenes
    @FXML
    private void handleNextDobleIndividual() {
        cambiarImagenDerecha(imagenDobleInvididual, imagenesDobleInvididual, ++indiceDobleInvididual);
    }

    @FXML
    private void handlePrevDobleIndividual() {
        indiceDobleInvididual = (indiceDobleInvididual - 1 + imagenesDobleInvididual.size()) % imagenesDobleInvididual.size();
        cambiarImagenIzquierda(imagenDobleInvididual, imagenesDobleInvididual, indiceDobleInvididual);
    }

    @FXML
    private void handleNextDoble() {
        cambiarImagenDerecha(imagenDoble, imagenesDoble, ++indiceDoble);
    }

    @FXML
    private void handlePrevDoble() {
        indiceDoble = (indiceDoble - 1 + imagenesDoble.size()) % imagenesDoble.size();
        cambiarImagenIzquierda(imagenDoble, imagenesDoble, indiceDoble);
    }

    @FXML
    private void handleNextJuniorSuite() {
        cambiarImagenDerecha(imagenJuniorSuite, imagenesJuniorSuite, ++indiceJuniorSuite);
    }

    @FXML
    private void handlePrevJuniorSuite() {
        indiceJuniorSuite = (indiceJuniorSuite - 1 + imagenesJuniorSuite.size()) % imagenesJuniorSuite.size();
        cambiarImagenIzquierda(imagenJuniorSuite, imagenesJuniorSuite, indiceJuniorSuite);
    }

    @FXML
    private void handleNextSuite() {
        cambiarImagenDerecha(imagenSuite, imagenesSuite, ++indiceSuite);
    }

    @FXML
    private void handlePrevSuite() {
        indiceSuite = (indiceSuite - 1 + imagenesSuite.size()) % imagenesSuite.size();
        cambiarImagenIzquierda(imagenSuite, imagenesSuite, indiceSuite);
    }

    private void cambiarImagenDerecha(ImageView imageView, List<Image> imagenes, int indice) {
        if (!imagenes.isEmpty()) {
            actualizarImagen(imageView, imagenes, indice % imagenes.size());
        }
    }

    private void cambiarImagenIzquierda(ImageView imageView, List<Image> imagenes, int indice) {
        if (!imagenes.isEmpty()) {
            actualizarImagen(imageView, imagenes, indice % imagenes.size());
        }
    }
}
