module com.example.examen_ex1ev1 {
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
    requires AccesoBBDDExamenV2;
    requires java.sql;
    requires java.desktop;

    opens com.example.examen_ex1ev1 to javafx.fxml;
    exports com.example.examen_ex1ev1;
    opens com.example.examen_ex1ev1.controller to javafx.fxml;
    exports com.example.examen_ex1ev1.controller;
}