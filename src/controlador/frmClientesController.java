package controlador;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML private TextField tfFecha;

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
                            tfFecha.setText(String.valueOf(valorSeleccionado.getFechaRegistro())); //ojo .toLocalDate

                            btnGuardar.setDisable(true);
                            btnEliminar.setDisable(false);
                            btnActualizar.setDisable(false);

                        }
                    }
                }
        );
    }

}
