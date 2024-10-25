package com.example.agenda_v2;

import Modelo.ExcepcionMoneda;
import Modelo.MonedaVO;
import Modelo.repository.impl.MonedaRepositoryImpl;
import com.example.agenda_v2.modelo.ExceptionPersona;
import com.example.agenda_v2.modelo.PersonVO;
import com.example.agenda_v2.modelo.repository.impl.PersonRepositoryImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

public class Main extends Application {

    public static void main(String[] args) {
        try {
            PersonRepositoryImpl personRepository = new PersonRepositoryImpl();
            PersonVO persona = new PersonVO("Ángela", "Pedrera", "C/ San Nicolás del Puerto", "Sevilla", 41016, LocalDate.parse("2005-10-01"));
            personRepository.guardarPersona(persona);
//            System.out.println(monedarepositoryImpl.ObtenerListaMonedas().size());
//            monedarepositoryImpl.deleteMoneda(8);
//            System.out.println(monedarepositoryImpl.ObtenerListaMonedas().size());
//            monedaPrueba.setNombre("Holassssss");
//            monedaPrueba.setMultiplicador(2.0F);
//            monedaPrueba.setCodigo(22);
//            monedarepositoryImpl.editMoneda(monedaPrueba);
//            System.out.println(monedarepositoryImpl.lastId() + " last id");
//            Iterator<MonedaVO> it = monedarepositoryImpl.ObtenerListaMonedas().iterator();
//
//            while(it.hasNext()) {
//                MonedaVO mon = (MonedaVO)it.next();
//                System.out.println(mon.getCodigo() + " " + mon.getNombre() + ' ' + mon.getMultiplicador());
//            }
        } catch (ExceptionPersona e) {
            System.out.println(e.imprimirMensaje());
        }
//        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Agenda_v2.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
    }

}