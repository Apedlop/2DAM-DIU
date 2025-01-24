package com.example.gestionhotel.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebViewController {

    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        // Obtener el WebEngine del WebView
        WebEngine webEngine = webView.getEngine();

        // Cargar el archivo HTML desde la carpeta de recursos
        // Si el archivo HTML está en el directorio de recursos, usa el siguiente código:
        String htmlFile = getClass().getResource("/com/example/gestionhotel/docs/index.html").toExternalForm();

        // Cargar el archivo HTML en el WebView
        webEngine.load(htmlFile);
    }
}
