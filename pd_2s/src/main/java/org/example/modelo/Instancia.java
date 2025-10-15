package org.example.modelo;

import java.sql.Timestamp;

public class Instancia {

    private int idInstancia;
    private String Titulo;
    private String comConfidencial;
    private Timestamp fechaHora;
    private String Comenatario;
    private Estudiante estudiante;
    private Funcionario funcionario;

    public Instancia() {
    }

    public Instancia(int idInstancia, String titulo, String comConfidencial, Timestamp fechaHora, String comenatario, Estudiante estudiante, Funcionario funcionario) {
        this.idInstancia = idInstancia;
        Titulo = titulo;
        this.comConfidencial = comConfidencial;
        this.fechaHora = fechaHora;
        Comenatario = comenatario;
        this.estudiante = estudiante;
        this.funcionario = funcionario;
    }

    public Instancia(int idInstancia, String titulo, Timestamp fechaHora, String comenatario, Estudiante estudiante, Funcionario funcionario) {
        this.idInstancia = idInstancia;
        Titulo = titulo;
        this.fechaHora = fechaHora;
        Comenatario = comenatario;
        this.estudiante = estudiante;
        this.funcionario = funcionario;
    }

    public int getIdInstancia() {
        return idInstancia;
    }

    public void setIdInstancia(int idInstancia) {
        this.idInstancia = idInstancia;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getComConfidencial() {
        return comConfidencial;
    }

    public void setComConfidencial(String comConfidencial) {
        this.comConfidencial = comConfidencial;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getComenatario() {
        return Comenatario;
    }

    public void setComenatario(String comenatario) {
        Comenatario = comenatario;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "Instancia{" +
                "idInstancia=" + idInstancia +
                ", Titulo='" + Titulo + '\'' +
                ", comConfidencial='" + comConfidencial + '\'' +
                ", fechaHora=" + fechaHora +
                ", Comenatario='" + Comenatario + '\'' +
                ", estudiante=" + estudiante +
                ", funcionario=" + funcionario +
                '}';
    }
}
