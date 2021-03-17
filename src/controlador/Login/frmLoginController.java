package controlador.Login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import modelo.Conexion;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;
import java.net.URL;

public class frmLoginController implements Initializable {

    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblMensajeIngreso;
    @FXML
    private ImageView logoMesutView;
    @FXML
    private ImageView logoGNView;
    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfContraseña;
    @FXML
    private Button btnIngresar;

    //Metodos
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //Logo mesut
        File archivoLogoMesut = new File("images/LogoMesut.png");
        Image imagenLogoMesut = new Image(archivoLogoMesut.toURI().toString());
        logoMesutView.setImage(imagenLogoMesut);

        //Logo Grupo Nomade
        File archivoGN = new File("images/LogoGN.png");
        Image imagenGN = new Image(archivoGN.toURI().toString());
        logoGNView.setImage(imagenGN);
    }

    public void btnIngresarEnAccion(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!tfUsuario.getText().isEmpty() && !tfContraseña.getText().isEmpty()) {
            validarLogin();
        } else {
            lblMensajeIngreso.setText("Porfavor ingrese usuario y contraseña");
        }
    }

    public void btnCancelarEnAccion(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void validarLogin() throws SQLException, ClassNotFoundException {
        Conexion connectNow = new Conexion();
        Connection connectDB = connectNow.establecerConexion();

        try {
            PreparedStatement statement = connectDB.prepareStatement("SELECT permisos, estado " +
                    "FROM trabajadores " +
                    "WHERE usuario = '" + tfUsuario.getText() + "' " +
                    "AND contraseña = '" + tfContraseña.getText() + "'");
            ResultSet queryResult = statement.executeQuery();

            if (queryResult.next()) {
                String permiso = queryResult.getString("permisos");
                String estado = queryResult.getString("estado");

                if (permiso.equals("admin") && estado.equals("activo")) {
                    abrirPanelAdmin();
                } else if (permiso.equals("consultor") && estado.equals("activo")) {
                    abrirPanelConsultor();
                } else if (permiso.equals("mecanico") && estado.equals("activo")) {
                    abrirPanelMecanico();
                } else {
                    lblMensajeIngreso.setText("Usuario Inactivo");
                }
            } else {
                lblMensajeIngreso.setText("Ingreso invalido, intenta nuevamente");
                tfUsuario.setText("");
                tfContraseña.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void abrirPanelAdmin() throws IOException {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("../Panel/Admin/frmAdmin.fxml"));
        Stage adminStage = new Stage();
        adminStage.initStyle(StageStyle.UNDECORATED);
        adminStage.setScene(new Scene(root, 520, 400));
        adminStage.show();
    }

    public void abrirPanelConsultor() throws IOException {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("../Panel/Consultor/frmConsultor.fxml"));
        Stage adminStage = new Stage();
        adminStage.initStyle(StageStyle.UNDECORATED);
        adminStage.setScene(new Scene(root, 520, 400));
        adminStage.show();
    }

    public void abrirPanelMecanico() throws IOException {
        Stage stage = (Stage) btnIngresar.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("../Panel/Mecanico/frmMecanico.fxml"));
        Stage adminStage = new Stage();
        adminStage.initStyle(StageStyle.UNDECORATED);
        adminStage.setScene(new Scene(root, 520, 400));
        adminStage.show();
    }


}
