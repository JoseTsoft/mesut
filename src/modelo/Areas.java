package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Areas {

    private IntegerProperty idAreas;
    private StringProperty area;

    public Areas(Integer idAreas, String area) {
        this.idAreas = new SimpleIntegerProperty(idAreas);
        this.area = new SimpleStringProperty(area);
    }

    public int getIdAreas() {
        return idAreas.get();
    }

    public IntegerProperty idAreasProperty() {
        return idAreas;
    }

    public void setIdAreas(int idAreas) {
        this.idAreas.set(idAreas);
    }

    public String getArea() {
        return area.get();
    }

    public StringProperty areaProperty() {
        return area;
    }

    public void setArea(String area) {
        this.area.set(area);
    }

    public static void llenarInformacion(Connection connection, ObservableList<Areas> lista){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT  * FROM areas");
            while (resultado.next()){
                lista.add(new Areas(
                        resultado.getInt("idAreas"),
                        resultado.getString("area")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return area.get();
    }
}
