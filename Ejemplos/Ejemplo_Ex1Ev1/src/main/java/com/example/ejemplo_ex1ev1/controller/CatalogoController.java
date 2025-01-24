package com.example.ejemplo_ex1ev1.controller;

import com.example.ejemplo_ex1ev1.Main;
import com.example.ejemplo_ex1ev1.modelo.Articulo;
import com.example.ejemplo_ex1ev1.modelo.ArticuloVO;
import com.example.ejemplo_ex1ev1.modelo.CatalogoModelo;
import com.example.ejemplo_ex1ev1.modelo.ExceptionCatalogo;
import com.example.ejemplo_ex1ev1.modelo.repository.TotalInterface;
import com.example.ejemplo_ex1ev1.modelo.repository.impl.TotalInterfaceImpl;
import com.example.ejemplo_ex1ev1.modelo.util.CatalogoUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class CatalogoController {

    @FXML
    private TableView<Articulo> articuloTable;
    @FXML
    private TableColumn<Articulo, String> columnaNombre;
    @FXML
    private TableColumn<Articulo, String> columnaPrecio;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label descLabel;
    @FXML
    private Label precioLabel;
    @FXML
    private Label stockLabel;
    @FXML
    private TextField unidades;
    @FXML
    private TextField total;

    private Main main;
    private CatalogoModelo catalogoModelo;
    private CatalogoUtil catalogoUtil = new CatalogoUtil();
    TotalInterface totalInterface;
    private Integer id = 1;

    // Constructo que al momenot de llamarlo instancia el calculo del total
    public CatalogoController() {
        totalInterface = new TotalInterfaceImpl();
    }

    // Método para recibir la inserción del modelo
    public void setCatalogoModelo(CatalogoModelo catalogoModelo) throws ExceptionCatalogo {
        this.catalogoModelo = catalogoModelo;
    }

    public void setMain(Main main) {
        this.main = main;
        articuloTable.setItems(main.getArticuloData());
    }

    @FXML
    private void initialize() {
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columnaPrecio.setCellValueFactory(cellData -> cellData.getValue().precioProperty().asString());
        articuloTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                mostrarDatosArticulos(newValue);
        });
    }

    public ArrayList<Articulo> tablaArticulos() throws ExceptionCatalogo, SQLException {
        return catalogoModelo.obtenerListaArticulo();
    }

    // Método para mostrar los datos de los artículos
    private void mostrarDatosArticulos(Articulo articulo) {
        if (articulo != null) {
            nombreLabel.setText(articulo.getNombre());
            descLabel.setText(articulo.getDescripcion());
            precioLabel.setText(String.valueOf(articulo.getPrecio()));
            stockLabel.setText(String.valueOf(articulo.getStock()));
        } else {
            nombreLabel.setText("");
            descLabel.setText("");
            precioLabel.setText("");
            stockLabel.setText("");
        }
    }

    private void cargarDatosArticulos() throws ExceptionCatalogo, SQLException {
        ArrayList<Articulo> listaArticulos = catalogoModelo.obtenerListaArticulo();
        ObservableList<Articulo> articuloData = FXCollections.observableArrayList(listaArticulos);
        articuloTable.setItems(articuloData);
    }

    @FXML
    public void botonNuevo() {
        Articulo articulo = new Articulo();
        boolean okClicked = main.pantallaNuevo(articulo);

        if (okClicked) {
            try{
                articulo.setId(id++);
                catalogoModelo.añadirArticulo(articulo);
                main.getArticuloData().add(articulo);
                cargarDatosArticulos();
            }catch(ExceptionCatalogo | SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al añadir la persona");
                alert.setTitle("Error con la base de datos");
                alert.setContentText("No se puede conectar con la base de datos para añadir la persona");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void botonTotal() {
        try {
            if(Integer.parseInt(stockLabel.getText())>=Integer.parseInt(unidades.getText())){
                if(Integer.parseInt(unidades.getText())>=0) {
                    catalogoModelo.setTotalInterface(totalInterface);
                    total.setText(String.valueOf(catalogoModelo.total(Integer.parseInt(unidades.getText()),Float.parseFloat(precioLabel.getText()))));
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error con las unidades");
                    alert.setTitle("El numero es incorrecto");
                    alert.setContentText("Las unidades no pueden ser negativas");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error al calcular");
                alert.setTitle("Error con las unidades");
                alert.setContentText("Las unidades no pueden superar al stock actual");
                alert.showAndWait();
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error al calcular");
            alert.setTitle("Error con las unidades");
            alert.setContentText("Debe introducir un numero de unidades y seleccionar al articulo");
            alert.showAndWait();
        }
    }

}
