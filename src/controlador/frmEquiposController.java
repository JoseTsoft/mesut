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
    @FXML private TextField tfMarca;
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
    @FXML private TableColumn<Equipos, Integer> clmnKilometraje;
    @FXML private TableColumn<Equipos, Integer> clmnHorometro;
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
        cargarTableView();
        gestionarEventos();

        conexion.cerrarConexion();

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
            clmnKilometraje.setCellValueFactory(new PropertyValueFactory<Equipos, Integer>("kilometraje"));
            clmnHorometro.setCellValueFactory(new PropertyValueFactory<Equipos, Integer>("Horometro"));
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
                            tfMarca.setText(valorSeleccionado.getMarca());
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

    @FXML
    public void guardarRegistro() throws ClassNotFoundException {
        if (tfPatente.getText().isEmpty() || tfTipo.getText().isEmpty()
                || tfMarca.getText().isEmpty()
                || tfModelo.getText().isEmpty() || tfAño.getText().isEmpty()
                || tfNroMotor.getText().isEmpty() || tfNroChasis.getText().isEmpty()
                || tfKilometraje.getText().isEmpty() || tfHorometro.getText().isEmpty()) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al registrar");
            mensaje.setContentText("No puede dejar un campo vacio");
            mensaje.show();
        } else {
            Equipos e = new Equipos(0,
                    tfPatente.getText(),
                    tfTipo.getText(),
                    tfMarca.getText(),
                    tfModelo.getText(),
                    Integer.valueOf(tfAño.getText()),
                    tfNroMotor.getText(),
                    tfNroChasis.getText(),
                    taObservaciones.getText(),
                    Integer.valueOf(tfKilometraje.getText()),
                    Integer.valueOf(tfHorometro.getText()),
                    rbActivo.isSelected() ? "activo" : "inactivo");

            conexion.establecerConexion();
            int resultado = e.guardarRegistro(conexion.getConnection());
            conexion.cerrarConexion();

            if (resultado == 1) {
                listaEquipos.add(e);
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro agregado");
                mensaje.setContentText("El registro ha sido agregado exitosamente");
                mensaje.setHeaderText("Resultado");
                mensaje.show();
            }
        }
    }

    @FXML
    public void actualizarRegistro() throws ClassNotFoundException {
        Equipos e = new Equipos(Integer.valueOf(tfID.getText()),
                tfPatente.getText(),
                tfTipo.getText(),
                tfMarca.getText(),
                tfModelo.getText(),
                Integer.valueOf(tfAño.getText()),
                tfNroMotor.getText(),
                tfNroChasis.getText(),
                taObservaciones.getText(),
                Integer.valueOf(tfKilometraje.getText()),
                Integer.valueOf(tfHorometro.getText()),
                rbActivo.isSelected() ? "activo" : "inactivo");

        conexion.establecerConexion();
        int resultado = e.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaEquipos.set(tblEquipos.getSelectionModel().getSelectedIndex(), e);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro agregado");
            mensaje.setContentText("El registro ha sido agregado exitosamente");
            mensaje.setHeaderText("Resultado");
            mensaje.show();
        }else{
            System.out.println("RESULTADO ES 0");
        }


    }
    @FXML
    public void eliminarRegistro() throws ClassNotFoundException {
        conexion.establecerConexion();
        int resultado = tblEquipos.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        if(resultado == 1){
            listaEquipos.remove(tblEquipos.getSelectionModel().getSelectedIndex());
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido elimnado exitosamente");
            mensaje.setHeaderText("Resultado");
            mensaje.show();
        }
    }

    @FXML
    public void limpiarComponentes(){
        tfID.setText(null);
        tfPatente.setText(null);
        tfTipo.setText(null);
        tfMarca.setText(null);
        tfModelo.setText(null);
        tfAño.setText(null);
        tfNroMotor.setText(null);
        tfNroChasis.setText(null);
        taObservaciones.setText(null);
        tfKilometraje.setText(null);
        tfHorometro.setText(null);
        rbActivo.setSelected(false);
        rbInactivo.setSelected(false);

        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);
    }

}
