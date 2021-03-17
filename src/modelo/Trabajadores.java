package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Trabajadores {

    public Trabajadores(int idTrabajadores, String nombre, String apellido, String mail, String cargo, String permisos,
                        String usuario, String contraseña, String area) {
        this.idTrabajadores = new SimpleIntegerProperty(idTrabajadores);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.mail = new SimpleStringProperty(mail);
        this.cargo = new SimpleStringProperty(cargo);
        this.permisos = new SimpleStringProperty(permisos);
        this.usuario = new SimpleStringProperty(usuario);
        this.contraseña = new SimpleStringProperty(contraseña);
        this.area = new SimpleStringProperty(area);
    }

    private IntegerProperty idTrabajadores;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty mail;
    private StringProperty cargo;
    private StringProperty permisos;
    private StringProperty usuario;
    private StringProperty contraseña;
    private StringProperty area;

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

    public String getCargo() {
        return cargo.get();
    }

    public StringProperty cargoProperty() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo.set(cargo);
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

    public String getContraseña() {
        return contraseña.get();
    }

    public StringProperty contraseñaProperty() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña.set(contraseña);
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
}
