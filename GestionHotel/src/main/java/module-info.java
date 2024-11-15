module com.example.gestionhotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.desktop;

    opens com.example.gestionhotel to javafx.fxml;
    exports com.example.gestionhotel;
    opens com.example.gestionhotel.controller to javafx.fxml;
    exports com.example.gestionhotel.controller;
}