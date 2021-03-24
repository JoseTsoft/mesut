package controlador.Trabajadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import modelo.Areas;
import modelo.Cargos;
import modelo.Conexion;
import modelo.Trabajadores;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class frmTrabajadoresController implements Initializable {

    //Campos
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellido;
    @FXML private TextField tfMail;
    @FXML private ComboBox<Cargos> cbCargos;
    @FXML private RadioButton rbAdmin;
    @FXML private RadioButton rbConsultor;
    @FXML private RadioButton rbMecanico;
    @FXML private RadioButton rbActivo;
    @FXML private RadioButton rbInactivo;
    @FXML private TextField tfUsuario;
    @FXML private PasswordField pfPassword;
    @FXML private ComboBox<Areas> cbAreas;

    //Botones
    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    //Columnas
    @FXML private TableColumn<Trabajadores, String> clmnUsuario;
    @FXML private TableColumn<Trabajadores, String> clmnPermisos;
    @FXML private TableColumn<Trabajadores, String> clmnEstado;
    @FXML private TableColumn<Areas, String> clmnAreas;

    //Colecciones
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Areas> listaAreas;
    private ObservableList<Trabajadores> listaTrabajadores;

    private Conexion conexion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexion = new Conexion();
        try {
            conexion.establecerConexion();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //LLenado Combobox de Cargos
        try {
            listaCargos = FXCollections.observableArrayList();
            Cargos.llenarInformacion(conexion.getConnection(), listaCargos);
            cbCargos.setItems(listaCargos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //LLenado Combobox de Areas
        try {
            listaAreas = FXCollections.observableArrayList();
            Areas.llenarInformacion(conexion.getConnection(), listaAreas);
            cbAreas.setItems(listaAreas);
        } catch (Exception e) {
            e.printStackTrace();
        }

        conexion.cerrarConexion();
    }
}
