package org.example.modelo;

public class Grupo {
    private int idGrupo;
    private String nombreGrupo;
    private Carrera carrera;


    public Grupo() {
    }

    public Grupo(int idGrupo, String nombreGrupo, Carrera carrera) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.carrera = carrera;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "idGrupo=" + idGrupo +
                ", nombreGrupo='" + nombreGrupo + '\'' +
                ", carrera=" + carrera +
                '}';
    }
}
