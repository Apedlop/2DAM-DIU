package com.example.gestionhotel.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class WebViewController {

    @FXML
    private WebView webView;

    @FXML
    private void initialize() {
        // Cargar la URL de la documentación en el WebView
        webView.getEngine().load("https://www.gopagify.com/page?#2PACX-1vQz0hZ-80KkUSvX8ze1_nY-QC50gOIIVsds1nXqUsLxZ8dkLg1X16etO4pMz81kic15ehfb2yJLz-uQ");
    }

}
