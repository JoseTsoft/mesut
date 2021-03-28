package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

public class Clientes {

    private IntegerProperty idClientes;
    private StringProperty nombreEmpresa;
    private StringProperty rutEmpresa;
    private StringProperty telefono;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty mail;
    private Date fechaRegistro;

    public Clientes(Integer idClientes, String nombreEmpresa, String rutEmpresa,
                    String telefono, String nombre, String apellido, String mail,
                    Date fechaRegistro) {
        this.idClientes = new SimpleIntegerProperty(idClientes);
        this.nombreEmpresa = new SimpleStringProperty(nombreEmpresa);
        this.rutEmpresa = new SimpleStringProperty(rutEmpresa);
        this.telefono = new SimpleStringProperty(telefono);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.mail = new SimpleStringProperty(mail);
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdClientes() {
        return idClientes.get();
    }
    public IntegerProperty idClientesProperty() {
        return idClientes;
    }
    public void setIdClientes(int idClientes) {
        this.idClientes.set(idClientes);
    }
    public String getNombreEmpresa() {
        return nombreEmpresa.get();
    }
    public StringProperty nombreEmpresaProperty() {
        return nombreEmpresa;
    }
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa.set(nombreEmpresa);
    }
    public String getRutEmpresa() {
        return rutEmpresa.get();
    }
    public StringProperty rutEmpresaProperty() {
        return rutEmpresa;
    }
    public void setRutEmpresa(String rutEmpresa) {
        this.rutEmpresa.set(rutEmpresa);
    }
    public String getTelefono() {
        return telefono.get();
    }
    public StringProperty telefonoProperty() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }
    public String getNombre() {
        return nombre.get();
    }
    public StringProperty nombreProperty() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    public String getApellido() {
        return apellido.get();
    }
    public StringProperty apellidoProperty() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }
    public String getMail() {
        return mail.get();
    }
    public StringProperty mailProperty() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail.set(mail);
    }
    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    public static void llenarInformacion(Connection connection, ObservableList<Clientes> lista){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT * FROM clientes");
            while (resultado.next()){
                lista.add(
                        new Clientes(
                                resultado.getInt("idClientes"),
                                resultado.getString("nombreEmpresa"),
                                resultado.getString("rutEmpresa"),
                                resultado.getString("telefono"),
                                resultado.getString("nombre"),
                                resultado.getString("apellido"),
                                resultado.getString("mail"),
                                resultado.getDate("fechaRegistro"))
                        );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int guardarRegistro(Connection connection) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO clientes " +
                    "(nombreEmpresa, rutEmpresa, telefono, nombre, apellido, mail) " +
                    "VALUES (?,?,?,?,?,?)");

            seteoStatement(statement);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int actualizarRegistro(Connection connection) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE clientes SET " +
                            "nombreEmpresa = ?, " +
                            "rutEmpresa = ?, " +
                            "telefono = ?, " +
                            "nombre = ?, " +
                            "apellido = ?, " +
                            "mail = ?" +
                            "WHERE idClientes = ?");

            seteoStatement(statement);
            statement.setInt(7,idClientes.get());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }



    public int eliminarRegistro(Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM clientes WHERE idClientes = ?");
            statement.setInt(1,idClientes.get());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void seteoStatement(PreparedStatement statement) throws SQLException {
        statement.setString(1,nombreEmpresa.get());
        statement.setString(2,rutEmpresa.get());
        statement.setString(3,telefono.get());
        statement.setString(4,nombre.get());
        statement.setString(5,apellido.get());
        statement.setString(6,mail.get());
    }
}
