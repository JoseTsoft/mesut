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

public class Cargos {

    private IntegerProperty idCargos;
    private StringProperty cargos;

    public Cargos(Integer idCargos, String cargos) {
        this.idCargos = new SimpleIntegerProperty(idCargos);
        this.cargos = new SimpleStringProperty(cargos);
    }

    public int getIdCargos() {
        return idCargos.get();
    }

    public IntegerProperty idCargosProperty() {
        return idCargos;
    }

    public void setIdCargos(int idCargos) {
        this.idCargos.set(idCargos);
    }

    public String getCargos() {
        return cargos.get();
    }

    public StringProperty cargosProperty() {
        return cargos;
    }

    public void setCargos(String cargos) {
        this.cargos.set(cargos);
    }

    public static void llenarInformacion(Connection connection, ObservableList<Cargos> lista){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT * FROM cargos");
            while (resultado.next()){
                lista.add(new Cargos(
                        resultado.getInt("idCargos"),
                        resultado.getString("cargos")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return cargos.get();
    }
}
