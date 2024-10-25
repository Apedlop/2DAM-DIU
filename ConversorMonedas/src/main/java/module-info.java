module com.example.conversorMonedas {
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
    requires java.sql;

    opens com.example.conversorMonedas to javafx.fxml;
    opens com.example.conversorMonedas.controller to javafx.fxml;
    exports com.example.conversorMonedas;
}