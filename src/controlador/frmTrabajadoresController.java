package controlador;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Areas;
import modelo.Cargos;
import modelo.Conexion;
import modelo.Trabajadores;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class frmTrabajadoresController implements Initializable {

    //Campos
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellido;
    @FXML
    private TextField tfMail;
    @FXML
    private ComboBox<Cargos> cbCargos;
    @FXML
    private RadioButton rbAdmin;
    @FXML
    private RadioButton rbMecanico;
    @FXML
    private RadioButton rbActivo;
    @FXML
    private RadioButton rbInactivo;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private ComboBox<Areas> cbAreas;
    @FXML
    private TableView<Trabajadores> tblTrabajadores;

    //Imagenes
    @FXML
    private ImageView logo;

    //Botones
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;

    //Columnas
    @FXML
    private TableColumn<Trabajadores, String> clmnUsuario;
    @FXML
    private TableColumn<Trabajadores, String> clmnPermisos;
    @FXML
    private TableColumn<Trabajadores, String> clmnEstado;
    @FXML
    private TableColumn<Areas, String> clmnAreas;

    //Colecciones
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Areas> listaAreas;
    private ObservableList<Trabajadores> listaTrabajadores;

    private Conexion conexion;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        File archivoLogoMesut = new File("images/LogoMesut.png");
        Image imagenLogoMesut = new Image(archivoLogoMesut.toURI().toString());
        logo.setImage(imagenLogoMesut);


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

    public void cargarCombobox() {
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
            Trabajadores.llenarInformacion(conexion.getConnection(), listaTrabajadores);
            tblTrabajadores.setItems(listaTrabajadores);
            clmnUsuario.setCellValueFactory(new PropertyValueFactory<Trabajadores, String>("usuario"));
            clmnPermisos.setCellValueFactory(new PropertyValueFactory<Trabajadores, String>("permisos"));
            clmnEstado.setCellValueFactory(new PropertyValueFactory<Trabajadores, String>("estado"));
            clmnAreas.setCellValueFactory(new PropertyValueFactory<Areas, String>("area"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void gestionarEventos() {
        tblTrabajadores.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Trabajadores>() {
                    @Override
                    public void changed(ObservableValue<? extends Trabajadores> observableValue,
                                        Trabajadores valorAnterior, Trabajadores valorSeleccionado) {
                        if (valorSeleccionado != null) {
                            tfID.setText(String.valueOf(valorSeleccionado.getIdTrabajadores()));
                            tfNombre.setText(valorSeleccionado.getNombre());
                            tfApellido.setText(valorSeleccionado.getApellido());
                            tfMail.setText(valorSeleccionado.getMail());
                            cbCargos.setValue(valorSeleccionado.getCargos());
                            if (valorSeleccionado.getPermisos().equals("admin")) {
                                rbAdmin.setSelected(true);
                                rbMecanico.setSelected(false);
                            } else if (valorSeleccionado.getPermisos().equals("mecanico")) {
                                rbAdmin.setSelected(false);
                                rbMecanico.setSelected(true);
                            }
                            if (valorSeleccionado.getEstado().equals("activo")) {
                                rbActivo.setSelected(true);
                                rbInactivo.setSelected(false);
                            } else if (valorSeleccionado.getEstado().equals("inactivo")) {
                                rbActivo.setSelected(false);
                                rbInactivo.setSelected(true);
                            }
                            tfUsuario.setText(valorSeleccionado.getUsuario());
                            pfPassword.setText(valorSeleccionado.getPassword());
                            cbAreas.setValue(valorSeleccionado.getArea());

                            btnGuardar.setDisable(true);
                            btnEliminar.setDisable(false);
                            btnActualizar.setDisable(false);


                        }
                    }
                }
        );
    }

    @FXML
    public void guardarRegistro() throws ClassNotFoundException, SQLException, IOException {
        if (tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfMail.getText().isEmpty()
                || cbCargos.getSelectionModel().getSelectedItem().equals("")
                || tfUsuario.getText().isEmpty() || pfPassword.getText().isEmpty()
                || cbAreas.getSelectionModel().getSelectedItem().equals("")) {

            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al registrar");
            mensaje.setContentText("No puede dejar un campo vacio");
            mensaje.show();
        } else {
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

            if (resultado == 1) {
                listaTrabajadores.add(t);
                Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
                mensaje.setTitle("Registro agregado");
                mensaje.setContentText("El registro ha sido agregado exitosamente");
                mensaje.setHeaderText("Resultado");
                mensaje.show();
            }
        }
    }

    @FXML
    public void actualizarRegistro() throws ClassNotFoundException, SQLException {
        Trabajadores t = new Trabajadores(Integer.valueOf(tfID.getText()),
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
        int resultado = t.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if (resultado == 1) {
            listaTrabajadores.set(tblTrabajadores.getSelectionModel().getSelectedIndex(), t);
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro actualizado");
            mensaje.setContentText("El registro ha sido actualizado exitosamente");
            mensaje.setHeaderText("Resultado");
            mensaje.show();
        }
    }

    @FXML
    public void eliminarRegistro() throws ClassNotFoundException {
        conexion.establecerConexion();
        int resultado = tblTrabajadores.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            listaTrabajadores.remove(tblTrabajadores.getSelectionModel().getSelectedIndex());
            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido elimnado exitosamente");
            mensaje.setHeaderText("Resultado");
            mensaje.show();
        }

    }

    @FXML
    public void limpiarComponentes() {
        tfID.setText(null);
        tfNombre.setText(null);
        tfApellido.setText(null);
        tfMail.setText(null);
        cbCargos.setValue(null);
        tfUsuario.setText(null);
        pfPassword.setText(null);
        rbAdmin.setSelected(false);
        rbMecanico.setSelected(false);
        rbActivo.setSelected(false);
        rbInactivo.setSelected(false);
        cbAreas.setValue(null);

        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);
    }

}
