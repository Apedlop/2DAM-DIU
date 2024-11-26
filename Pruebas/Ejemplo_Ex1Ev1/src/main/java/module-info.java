module com.example.ejemplo_ex1ev1 {
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

    opens com.example.ejemplo_ex1ev1 to javafx.fxml;
    exports com.example.ejemplo_ex1ev1;
    opens com.example.ejemplo_ex1ev1.controller to javafx.fxml;  // Permite acceso al paquete controller
    exports com.example.ejemplo_ex1ev1.controller;
    exports com.example.ejemplo_ex1ev1.modelo.repository;
    exports com.example.ejemplo_ex1ev1.modelo.repository.impl;
    opens com.example.ejemplo_ex1ev1.modelo to javafx.fxml;
}