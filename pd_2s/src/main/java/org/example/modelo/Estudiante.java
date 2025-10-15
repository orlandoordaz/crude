package org.example.modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;

public class Estudiante extends Usuario {

    private int idEstudiante;
    private Grupo grupo;
    private Carrera carrera;
    private String sistSalud;
    private String motivoDerivacion;
    private String estadoSalud;
    private String comentarios;
    private String observacionesConfidenciales;
    private String foto;

    public Estudiante(int id, String nombre, String apellido, String email, LinkedList<String> telefono, String calle, String numPuerta, String cedula, ITR itr, String usuario, String password, Date fechaNacimiento, String estado, int idEstudiante, Grupo grupo, Carrera carrera, String sistSalud, String motivoDerivacion, String estadoSalud, String comentarios, String observacionesConfidenciales, String foto) {
        super(id, nombre, apellido, email, telefono, calle, numPuerta, cedula, itr, usuario, password, fechaNacimiento, estado);
        this.idEstudiante = idEstudiante;
        this.grupo = grupo;
        this.carrera = carrera;
        this.sistSalud = sistSalud;
        this.motivoDerivacion = motivoDerivacion;
        this.estadoSalud = estadoSalud;
        this.comentarios = comentarios;
        this.observacionesConfidenciales = observacionesConfidenciales;
        this.foto = foto;
    }

    public Estudiante(int id, String nombre, String apellido, String email, LinkedList<String> telefono, String calle, String numPuerta, String cedula, ITR itr, String usuario, String password, Date fechaNacimiento, String estado, int idEstudiante, String sistSalud, String motivoDerivacion, String estadoSalud, String comentarios, String observacionesConfidenciales, String foto) {
        super(id, nombre, apellido, email, telefono, calle, numPuerta, cedula, itr, usuario, password, fechaNacimiento, estado);
        this.idEstudiante = idEstudiante;
        this.sistSalud = sistSalud;
        this.motivoDerivacion = motivoDerivacion;
        this.estadoSalud = estadoSalud;
        this.comentarios = comentarios;
        this.observacionesConfidenciales = observacionesConfidenciales;
        this.foto = foto;
    }
    public Estudiante() {
        super();
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getSistSalud() {
        return sistSalud;
    }

    public void setSistSalud(String sistSalud) {
        this.sistSalud = sistSalud;
    }

    public String getMotivoDerivacion() {
        return motivoDerivacion;
    }

    public void setMotivoDerivacion(String motivoDerivacion) {
        this.motivoDerivacion = motivoDerivacion;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getObservacionesConfidenciales() {
        return observacionesConfidenciales;
    }

    public void setObservacionesConfidenciales(String observacionesConfidenciales) {
        this.observacionesConfidenciales = observacionesConfidenciales;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    @Override
    public String toString() {
        return "Estudiante{" +
                "idEstudiante=" + idEstudiante +
                ", grupo='" + grupo + '\'' +
                ", carrera='" + carrera + '\'' +
                ", sistSalud='" + sistSalud + '\'' +
                ", motivoDerivacion='" + motivoDerivacion + '\'' +
                ", estadoSalud='" + estadoSalud + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", observacionesConfidenciales='" + observacionesConfidenciales + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
