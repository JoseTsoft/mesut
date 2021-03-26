package modelo;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.*;



public class Equipos {

    private IntegerProperty idEquipos;
    private StringProperty patente;
    private StringProperty tipo;
    private StringProperty marca;
    private StringProperty modelo;
    private IntegerProperty año;
    private StringProperty nroMotor;
    private StringProperty nroChasis;
    private StringProperty observaciones;
    private IntegerProperty kilometraje;
    private IntegerProperty horometro;
    private StringProperty estado;


    public Equipos(Integer idEquipos, String patente,
                   String tipo, String marca,
                   String modelo, Integer año,
                   String nroMotor, String nroChasis,
                   String observaciones, Integer kilometraje,
                   Integer horometro, String estado)
    {
        this.idEquipos = new SimpleIntegerProperty(idEquipos);
        this.patente = new SimpleStringProperty(patente);
        this.tipo = new SimpleStringProperty(tipo);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.año = new SimpleIntegerProperty(año);
        this.nroMotor = new SimpleStringProperty(nroMotor);
        this.nroChasis = new SimpleStringProperty(nroChasis);
        this.observaciones = new SimpleStringProperty(observaciones);
        this.kilometraje = new SimpleIntegerProperty(kilometraje);
        this.horometro = new SimpleIntegerProperty(horometro);
        this.estado = new SimpleStringProperty(estado);
    }

    public Equipos (String marca){
        this.marca = new SimpleStringProperty(marca);
    }


    public int getIdEquipos() {
        return idEquipos.get();
    }
    public IntegerProperty idEquiposProperty() {
        return idEquipos;
    }
    public void setIdEquipos(int idEquipos) {
        this.idEquipos.set(idEquipos);
    }
    public String getPatente() {
        return patente.get();
    }
    public StringProperty patenteProperty() {
        return patente;
    }
    public void setPatente(String patente) {
        this.patente.set(patente);
    }
    public String getTipo() {
        return tipo.get();
    }
    public StringProperty tipoProperty() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }
    public String getMarca(){return marca.get();}
    public StringProperty marcaProperty() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca.set(marca);
    }
    public String getModelo() {
        return modelo.get();
    }
    public StringProperty modeloProperty() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }
    public int getAño() {
        return año.get();
    }
    public IntegerProperty añoProperty() {
        return año;
    }
    public void setAño(int año) {
        this.año.set(año);
    }
    public String getNroMotor() {
        return nroMotor.get();
    }
    public StringProperty nroMotorProperty() {
        return nroMotor;
    }
    public void setNroMotor(String nroMotor) {
        this.nroMotor.set(nroMotor);
    }
    public String getNroChasis() {
        return nroChasis.get();
    }
    public StringProperty nroChasisProperty() {
        return nroChasis;
    }
    public void setNroChasis(String nroChasis) {
        this.nroChasis.set(nroChasis);
    }
    public String getObservaciones() {
        return observaciones.get();
    }
    public StringProperty observacionesProperty() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones.set(observaciones);
    }
    public int getKilometraje() {
        return kilometraje.get();
    }
    public IntegerProperty kilometrajeProperty() {
        return kilometraje;
    }
    public void setKilometraje(int kilometraje) {
        this.kilometraje.set(kilometraje);
    }
    public int getHorometro() {
        return horometro.get();
    }
    public IntegerProperty horometroProperty() {
        return horometro;
    }
    public void setHorometro(int horometro) {
        this.horometro.set(horometro);
    }
    public String getEstado() {
        return estado.get();
    }
    public StringProperty estadoProperty() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado.set(estado);
    }


    public static void llenarInformacion(Connection connection, ObservableList<Equipos> lista){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT * FROM equipos");
            while (resultado.next()){
                lista.add(
                        new Equipos(
                                resultado.getInt("idEquipos"),
                                resultado.getString("patente"),
                                resultado.getString("tipo"),
                                resultado.getString("marca"),
                                resultado.getString("modelo"),
                                resultado.getInt("año"),
                                resultado.getString("nroMotor"),
                                resultado.getString("nroChasis"),
                                resultado.getString("observaciones"),
                                resultado.getInt("kilometraje"),
                                resultado.getInt("horometro"),
                                resultado.getString("estado")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int guardarRegistro(Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO equipos (patente, tipo, marca, modelo, año, nromotor, nrochasis," +
                            " observaciones, kilometraje, horometro, estado) " +
                            "VALUES  (?,?,?,?,?,?,?,?,?,?,?)");
            seteoStatement(statement);
            return  statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int actualizarRegistro(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE equipos SET patente = ?," +
                            "tipo = ?, " +
                            "marca = ?, " +
                            "modelo = ?, " +
                            "año = ?, " +
                            "nroMotor = ?, " +
                            "nroChasis = ?, " +
                            "observaciones = ?, " +
                            "kilometraje = ? ," +
                            "horometro = ?, " +
                            "estado = ? " +
                            "WHERE  idEquipos = ?");
            seteoStatement(statement);
            statement.setInt(12, idEquipos.get());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int eliminarRegistro(Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement("" +
                    "DELETE FROM equipos WHERE idEquipos = ?");
            statement.setInt(1,idEquipos.get());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private void seteoStatement(PreparedStatement statement) throws SQLException {
        statement.setString(1,patente.get());
        statement.setString(2,tipo.get());
        statement.setString(3,marca.get());
        statement.setString(4,modelo.get());
        statement.setInt(5,año.get());
        statement.setString(6,nroMotor.get());
        statement.setString(7,nroChasis.get());
        statement.setString(8,observaciones.get());
        statement.setInt(9,kilometraje.get());
        statement.setInt(10,horometro.get());
        statement.setString(11,estado.get());
    }


}
