package controlador;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FxmlLoader {
    private Pane view;

    public Pane getPane(String fileName) throws IOException {

        try {
            URL fileUrl = Main.class.getResource("../vistas/" + fileName + ".fxml");
            if(fileUrl == null){
                throw new FileNotFoundException("Archivo FXML no encontrado");
            }

            view = new FXMLLoader().load(fileUrl);
        } catch (IOException e) {
            System.out.println("Vista " + fileName + "No encontrada, revisa las vistas");
        }

        return view;
    }
}
