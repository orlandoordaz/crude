package org.example.modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedList;

import org.example.seguridad.CifradoCesar;

public class Usuario {
    protected int id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected LinkedList<String> telefono;
    protected String calle;
    protected String numPuerta;
    protected String cedula;
    protected ITR itr;
    protected String usuario;
    protected String password;
    protected Date fechaNacimiento;
    protected String estado;

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuario(int id,String nombre, String apellido, String email, LinkedList<String> telefono, String calle, String numPuerta, String cedula, ITR itr, String usuario, String password, Date fechaNacimiento, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.calle = calle;
        this.numPuerta = numPuerta;
        this.cedula = cedula;
        this.itr = itr;
        this.usuario = usuario;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    public Usuario(int id,String nombre, String apellido, String email, String calle, String numPuerta, String cedula, String usuario, String password, Date fechaNacimiento, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.calle = calle;
        this.numPuerta = numPuerta;
        this.cedula = cedula;
        this.usuario = usuario;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
    }

    public Usuario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinkedList<String> getTelefono() {
        return telefono;
    }

    public void setTelefono(LinkedList<String> telefono) {
        this.telefono = telefono;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumPuerta() {
        return numPuerta;
    }

    public void setNumPuerta(String numPuerta) {
        this.numPuerta = numPuerta;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public ITR getItr() {
        return itr;
    }

    public void setItr(ITR itr) {
        this.itr = itr;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = CifradoCesar.cifrar(usuario);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = CifradoCesar.cifrar(password);
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean estadoActivo() {
        return estado.equalsIgnoreCase("Activo");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", calle='" + calle + '\'' +
                ", numPuerta='" + numPuerta + '\'' +
                ", cedula='" + cedula + '\'' +
                ", itr=" + itr +
                ", usuario='" + usuario + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }

    public int getEdad() {
        if (fechaNacimiento == null) return 0;

        LocalDate nacimiento = fechaNacimiento.toLocalDate();
        LocalDate hoy = LocalDate.now();
        System.out.println(Period.between(nacimiento, hoy).getYears());
        return Period.between(nacimiento, hoy).getYears();
    }
}
