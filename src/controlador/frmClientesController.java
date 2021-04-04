package controlador;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Areas;
import modelo.Clientes;
import modelo.Conexion;
import modelo.Trabajadores;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class frmClientesController implements Initializable {

    @FXML private TextField tfID;
    @FXML private TextField tfNombreEmpresa;
    @FXML private TextField tfRutEmpresa;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfNombre;
    @FXML private TextField tfApellido;
    @FXML private TextField tfMail;
    @FXML private DatePicker dpFecha;

    @FXML private TableView<Clientes> tblClientes;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    @FXML private TableColumn<Clientes, String> clmnNombreEmpresa;
    @FXML private TableColumn<Clientes, String> clmnRutEmpresa;
    @FXML private TableColumn<Clientes, String> clmnTelefono;
    @FXML private TableColumn<Clientes, String> clmnNombre;
    @FXML private TableColumn<Clientes, String> clmnApellido;
    @FXML private TableColumn<Clientes, String> clmnMail;
    @FXML private TableColumn<Clientes, Date> clmnFechaRegistro;

    private ObservableList<Clientes> listaClientes;
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

    public void cargarTableView() {

        listaClientes = FXCollections.observableArrayList();
        Clientes.llenarInformacion(conexion.getConnection(), listaClientes);
        tblClientes.setItems(listaClientes);
        clmnNombreEmpresa.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreEmpresa"));
        clmnRutEmpresa.setCellValueFactory(new PropertyValueFactory<Clientes, String>("rutEmpresa"));
        clmnTelefono.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefono"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombre"));
        clmnApellido.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellido"));
        clmnMail.setCellValueFactory(new PropertyValueFactory<Clientes, String>("mail"));
        clmnFechaRegistro.setCellValueFactory(new PropertyValueFactory<Clientes, Date>("fechaRegistro"));
    }

    public void gestionarEventos(){
        tblClientes.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Clientes>() {
                    @Override
                    public void changed(ObservableValue<? extends Clientes> observable, Clientes valorAnterior, Clientes valorSeleccionado) {
                        if(valorSeleccionado != null){
                            tfID.setText(String.valueOf(valorSeleccionado.getIdClientes()));
                            tfNombreEmpresa.setText(valorSeleccionado.getNombreEmpresa());
                            tfRutEmpresa.setText(valorSeleccionado.getRutEmpresa());
                            tfTelefono.setText(valorSeleccionado.getTelefono());
                            tfNombre.setText(valorSeleccionado.getNombre());
                            tfApellido.setText(valorSeleccionado.getApellido());
                            tfMail.setText(valorSeleccionado.getMail());
                            dpFecha.setValue(valorSeleccionado.getFechaRegistro().toLocalDate()); //ojo .toLocalDate

                            btnGuardar.setDisable(true);
                            btnEliminar.setDisable(false);
                            btnActualizar.setDisable(false);

                        }
                    }
                }
        );
    }

    @FXML
    public void guardarRegistro() throws ClassNotFoundException, SQLException {
        if (tfNombreEmpresa.getText().isEmpty() || tfRutEmpresa.getText().isEmpty() || tfTelefono.getText().isEmpty()
        || tfNombre.getText().isEmpty() || tfApellido.getText().isEmpty() || tfMail.getText().isEmpty()){
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al registrar");
            mensaje.setContentText("No puede dejar un campo vacio");
            mensaje.show();
        }else {
            Clientes c = new Clientes(0,
                    tfNombreEmpresa.getText(),
                    tfRutEmpresa.getText(),
                    tfTelefono.getText(),
                    tfNombre.getText(),
                    tfApellido.getText(),
                    tfMail.getText(),
                    Date.valueOf(dpFecha.getValue()));
            conexion.establecerConexion();
            int resultado = c.guardarRegistro(conexion.getConnection());
            conexion.cerrarConexion();

            if(resultado == 1){
                listaClientes.add(c);
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
        Clientes c = new Clientes(Integer.valueOf(tfID.getText()),
                tfNombreEmpresa.getText(),
                tfRutEmpresa.getText(),
                tfTelefono.getText(),
                tfNombre.getText(),
                tfApellido.getText(),
                tfMail.getText(),
                Date.valueOf(dpFecha.getValue()));

        conexion.establecerConexion();
        int resultado = c.actualizarRegistro(conexion.getConnection());
        conexion.cerrarConexion();

        if(resultado == 1){
            listaClientes.set(tblClientes.getSelectionModel().getSelectedIndex(),c);
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
        int resultado = tblClientes.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
        conexion.cerrarConexion();
        if (resultado == 1) {
            listaClientes.remove(tblClientes.getSelectionModel().getSelectedIndex());
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
        tfNombreEmpresa.setText(null);
        tfRutEmpresa.setText(null);
        tfTelefono.setText(null);
        tfNombre.setText(null);
        tfApellido.setText(null);
        tfMail.setText(null);
        dpFecha.setValue(null);
    }

}
