package org.example.modelo;

import java.sql.Date;
import java.util.LinkedList;

public class Funcionario extends Usuario {
    private int idFuncionario;
    private Rol rol;

    public Funcionario(int id, String nombre, String apellido, String email, LinkedList<String> telefono, String calle, String numPuerta, String cedula, ITR itr, String usuario, String password, Date fechaNacimiento, String estado, int idFuncionario, Rol rol) {
        super(id, nombre, apellido, email, telefono, calle, numPuerta, cedula, itr, usuario, password, fechaNacimiento, estado);
        this.idFuncionario = idFuncionario;
        this.rol = rol;
    }

    public Funcionario(int id, String nombre, String apellido, Rol rol) {
        super(nombre, apellido);
        this.idFuncionario = id;
        this.rol = rol;
    }

    public Funcionario() {
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }


}
