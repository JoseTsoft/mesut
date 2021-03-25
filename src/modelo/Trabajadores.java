package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.*;

public class Trabajadores {


    public Trabajadores(Integer idTrabajadores, String nombre, String apellido, String mail, Cargos cargos, String usuario, String password, String permisos, String estado, Areas area) {
        this.idTrabajadores = new SimpleIntegerProperty(idTrabajadores);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.mail = new SimpleStringProperty(mail);
        this.cargos = cargos;
        this.usuario = new SimpleStringProperty(usuario);
        this.password = new SimpleStringProperty(password);
        this.permisos = new SimpleStringProperty(permisos);
        this.estado = new SimpleStringProperty(estado);
        this.area = area;
    }

    private IntegerProperty idTrabajadores;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty mail;
    private Cargos cargos;
    private StringProperty permisos;
    private StringProperty usuario;
    private StringProperty password;
    private Areas area;
    private StringProperty estado;

    public String getEstado() {
        return estado.get();
    }
    public StringProperty estadoProperty() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado.set(estado);
    }
    public Integer getIdTrabajadores() {
        return idTrabajadores.get();
    }
    public IntegerProperty idTrabajadoresProperty() {
        return idTrabajadores;
    }
    public void setIdTrabajadores(int idTrabajadores) {
        this.idTrabajadores.set(idTrabajadores);
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
    public Cargos getCargos() {
        return cargos;
    }
    public void setCargos(Cargos s) {
        this.cargos = cargos;
    }
    public String getPermisos() {
        return permisos.get();
    }
    public StringProperty permisosProperty() {
        return permisos;
    }
    public void setPermisos(String permisos) {
        this.permisos.set(permisos);
    }
    public String getUsuario() {
        return usuario.get();
    }
    public StringProperty usuarioProperty() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }
    public String getPassword() {
        return password.get();
    }
    public StringProperty passwordProperty() {
        return password;
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    public Areas getArea() {
        return area;
    }
    public void setArea(Areas area) {
        this.area = area;
    }

    public static void llenarInformacion(Connection connection, ObservableList<Trabajadores> lista) throws SQLException {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery(
                    "SELECT * FROM trabajadores t INNER JOIN areas a ON (t.area = a.area) INNER JOIN cargos c ON (t.cargos = c.cargos)");
            while (resultado.next()) {
                lista.add(
                        new Trabajadores(
                                resultado.getInt("idTrabajadores"),
                                resultado.getString("nombre"),
                                resultado.getString("apellido"),
                                resultado.getString("mail"),
                                new Cargos(resultado.getInt("idCargos"),resultado.getString("cargos")),
                                resultado.getString("usuario"),
                                resultado.getString("contraseña"),
                                resultado.getString("permisos"),
                                resultado.getString("estado"),
                                new Areas(resultado.getString("area")
                                ))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int guardarRegistro(Connection connection) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO trabajadores " +
                    "(NOMBRE, APELLIDO, MAIL, cargos, PERMISOS, USUARIO, CONTRASEÑA, ESTADO, AREA) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)");
            statement.setString(1,nombre.get());
            statement.setString(2,apellido.get());
            statement.setString(3,mail.get());
            statement.setString(4,cargos.getCargos());
            statement.setString(5,permisos.get());
            statement.setString(6,usuario.get());
            statement.setString(7,password.get());
            statement.setString(8,estado.get());
            statement.setString(9,area.getArea());

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
