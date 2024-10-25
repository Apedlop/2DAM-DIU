module com.example.agenda_v2 {
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
    requires AccesoBBDDMoneda;
    requires java.desktop;
    requires java.sql;

    opens com.example.agenda_v2 to javafx.fxml;
    exports com.example.agenda_v2;
    exports com.example.agenda_v2.controller;
    opens com.example.agenda_v2.controller to javafx.fxml;
    exports com.example.agenda_v2.modelo;
    opens com.example.agenda_v2.modelo to javafx.fxml;
}