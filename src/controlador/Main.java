package controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("Login/frmLogin.fxml"));*/
        Parent root = FXMLLoader.load(getClass().getResource("Trabajadores/frmTrabajadores.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        /*primaryStage.setScene(new Scene(root, 520, 400));*/
        primaryStage.setScene(new Scene(root, 716, 525));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
