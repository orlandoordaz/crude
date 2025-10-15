package org.example.modelo;

public class Rol {
    private int id;
    private tipoUsuario tipoUsuario;


    public Rol(int id, tipoUsuario tipoUsuario) {
        this.id = id;
        this.tipoUsuario = tipoUsuario;


    }

    public Rol() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public tipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(tipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}
