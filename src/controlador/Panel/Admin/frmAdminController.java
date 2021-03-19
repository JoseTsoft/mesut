package controlador.Panel.Admin;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class frmAdminController implements Initializable {

    @FXML
    private ImageView Exit;
    @FXML
    private Label Menu;
    @FXML
    private Label MenuBack;
    @FXML
    private AnchorPane slider;
    @FXML
    private ImageView add1;
    @FXML
    private ImageView add2;
    @FXML
    private ImageView add3;
    @FXML
    private ImageView add4;
    @FXML
    private ImageView add5;
    @FXML
    private ImageView backupView;
    @FXML
    private ImageView menuView;
    @FXML
    private ImageView menuBackView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Imagenes
        File archivoAdd = new File("images/add.png");
        Image imagenAdd = new Image(archivoAdd.toURI().toString());
        add1.setImage(imagenAdd);
        add2.setImage(imagenAdd);
        add3.setImage(imagenAdd);
        add4.setImage(imagenAdd);
        add5.setImage(imagenAdd);

        File archivoBackup = new File("images/data.png");
        Image imagenBackup = new Image(archivoBackup.toURI().toString());
        backupView.setImage(imagenBackup);

        File archivoMenu = new File("images/menu.png");
        Image imagenMenu = new Image(archivoMenu.toURI().toString());
        menuView.setImage(imagenMenu);
        menuBackView.setImage(imagenMenu);

        File archivoExit = new File("images/exit.png");
        Image imagenExit = new Image(archivoExit.toURI().toString());
        Exit.setImage(imagenExit);


        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        slider.setTranslateX(-200);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-200);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(false);
                MenuBack.setVisible(true);
            });
        });

        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-200);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)->{
                Menu.setVisible(true);
                MenuBack.setVisible(false);
            });
        });

    }
}
