package controlador;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Conexion;
import modelo.Equipos;


import java.net.URL;
import java.util.ResourceBundle;

public class frmEquiposController implements Initializable {

    @FXML private TextField tfID;
    @FXML private TextField tfPatente;
    @FXML private TextField tfTipo;
    @FXML private ComboBox<Equipos> cbMarca;
    @FXML private TextField tfModelo;
    @FXML private TextField tfAño;
    @FXML private TextField tfNroMotor;
    @FXML private TextField tfNroChasis;
    @FXML private TextField tfKilometraje;
    @FXML private TextField tfHorometro;
    @FXML private RadioButton rbActivo;
    @FXML private RadioButton rbInactivo;
    @FXML private TextArea taObservaciones;
    @FXML private TableView<Equipos> tblEquipos;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    @FXML private TableColumn<Equipos, String> clmnPatente;
    @FXML private TableColumn<Equipos, String> clmnTipo;
    @FXML private TableColumn<Equipos, String> clmnMarca;
    @FXML private TableColumn<Equipos, String> clmnModelo;
    @FXML private TableColumn<Equipos, Integer> clmnAño;
    @FXML private TableColumn<Equipos, Double> clmnKilometraje;
    @FXML private TableColumn<Equipos, Double> clmnHorometro;
    @FXML private TableColumn<Equipos, String> clmnEstado;

    private ObservableList<Equipos> listaMarcas;
    private ObservableList<Equipos> listaEquipos;

    private Conexion conexion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new Conexion();
        try {
            conexion.establecerConexion();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cargarCombobox();
        cargarTableView();
        gestionarEventos();

        conexion.cerrarConexion();

    }

    public void cargarCombobox(){
        try {
            listaMarcas = FXCollections.observableArrayList();
            Equipos.llenarInformacionCombobox(conexion.getConnection(),listaMarcas);
            cbMarca.setItems(listaMarcas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cargarTableView(){
        try {
            listaEquipos = FXCollections.observableArrayList();
            Equipos.llenarInformacion(conexion.getConnection(),listaEquipos);
            tblEquipos.setItems(listaEquipos);
            clmnPatente.setCellValueFactory(new PropertyValueFactory<Equipos, String>("patente"));
            clmnTipo.setCellValueFactory(new PropertyValueFactory<Equipos, String>("tipo"));
            clmnMarca.setCellValueFactory(new PropertyValueFactory<Equipos, String>("marca"));
            clmnModelo.setCellValueFactory(new PropertyValueFactory<Equipos, String>("modelo"));
            clmnAño.setCellValueFactory(new PropertyValueFactory<Equipos, Integer>("año"));
            clmnKilometraje.setCellValueFactory(new PropertyValueFactory<Equipos, Double>("kilometraje"));
            clmnHorometro.setCellValueFactory(new PropertyValueFactory<Equipos, Double>("Horometro"));
            clmnEstado.setCellValueFactory(new PropertyValueFactory<Equipos, String>("estado"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void gestionarEventos(){
        tblEquipos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Equipos>() {
                    @Override
                    public void changed(ObservableValue<? extends Equipos> observableValue,
                                        Equipos valorAnterior, Equipos valorSeleccionado) {
                        if(valorSeleccionado != null){
                            tfID.setText(String.valueOf(valorSeleccionado.getIdEquipos()));
                            tfPatente.setText(valorSeleccionado.getPatente());
                            tfTipo.setText(valorSeleccionado.getTipo());
                            cbMarca.setValue(new Equipos(valorSeleccionado.getMarca()));
                            tfModelo.setText(valorSeleccionado.getModelo());
                            tfAño.setText(String.valueOf(valorSeleccionado.getAño()));
                            tfNroMotor.setText(valorSeleccionado.getNroMotor());
                            tfNroChasis.setText(valorSeleccionado.getNroChasis());
                            tfKilometraje.setText(String.valueOf(valorSeleccionado.getKilometraje()));
                            tfHorometro.setText(String.valueOf(valorSeleccionado.getHorometro()));
                            if(valorSeleccionado.getEstado().equals("activo")){
                                rbActivo.setSelected(true);
                                rbInactivo.setSelected(false);
                            }else if (valorSeleccionado.getEstado().equals("inactivo")){
                                rbActivo.setSelected(false);
                                rbInactivo.setSelected(true);
                            }
                            taObservaciones.setText(valorSeleccionado.getObservaciones());

                            btnGuardar.setDisable(true);
                            btnEliminar.setDisable(false);
                            btnActualizar.setDisable(false);
                        }
                    }
                }
        );
    }
}
