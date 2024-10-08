module com.example.ejercicios {
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
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.ejercicios to javafx.fxml;
    opens com.example.prueba to javafx.fxml;
    exports com.example.ejercicios.ContadorPuls;
    opens com.example.ejercicios.ContadorPuls to javafx.fxml;
    exports com.example.ejercicios.ContadorPuls.controler;
    opens com.example.ejercicios.ContadorPuls.controler to javafx.fxml;
}