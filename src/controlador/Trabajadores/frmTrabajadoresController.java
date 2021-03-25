package controlador.Trabajadores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Areas;
import modelo.Cargos;
import modelo.Conexion;
import modelo.Trabajadores;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
    @FXML private TableView<Trabajadores> tblTrabajadores;

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
        cargarCombobox();
        cargarTableView();


        conexion.cerrarConexion();
    }

    public void cargarCombobox(){
        try {
            listaCargos = FXCollections.observableArrayList();
            listaAreas = FXCollections.observableArrayList();
            Cargos.llenarInformacion(conexion.getConnection(), listaCargos);
            Areas.llenarInformacion(conexion.getConnection(), listaAreas);
            cbCargos.setItems(listaCargos);
            cbAreas.setItems(listaAreas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarTableView() {
        try {
            listaTrabajadores = FXCollections.observableArrayList();
            Trabajadores.llenarInformacion(conexion.getConnection(),listaTrabajadores);
            tblTrabajadores.setItems(listaTrabajadores);
            clmnUsuario.setCellValueFactory(new PropertyValueFactory<Trabajadores, String>("usuario"));
            clmnPermisos.setCellValueFactory(new PropertyValueFactory<Trabajadores, String>("permisos"));
            clmnEstado.setCellValueFactory(new PropertyValueFactory<Trabajadores, String>("estado"));
            clmnAreas.setCellValueFactory(new PropertyValueFactory<Areas, String>("area"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void guardarRegistro() throws ClassNotFoundException, SQLException {
        Trabajadores t = new Trabajadores(0,
                tfNombre.getText(),
                tfApellido.getText(),
                tfMail.getText(),
                cbCargos.getSelectionModel().getSelectedItem(),
                tfUsuario.getText(),
                pfPassword.getText(),
                rbAdmin.isSelected() ? "admin" : "mecanico",
                rbActivo.isSelected() ? "activo" : "inactivo",
                cbAreas.getSelectionModel().getSelectedItem());
        conexion.establecerConexion();
        int resultado = t.guardarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if(resultado == 1){
            listaTrabajadores.add(t);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro agregado");
            mensaje.setContentText("El registro ha sido agregado exitosamente");
            mensaje.setHeaderText("Resultado");
            mensaje.show();
        }
    }
}
