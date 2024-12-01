module com.example.gestionhotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    // Librerías adicionales que usas, ajusta según sea necesario
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports com.example.gestionhotel;
    opens com.example.gestionhotel to javafx.fxml;
    opens com.example.gestionhotel.controller to javafx.fxml;
    exports com.example.gestionhotel.controller;
}
