package org.example.modelo;

import java.util.Date;

public class InformeAdjunto {

    private int id_informe;
    private String nombre;
    private boolean confidencial;
    private String categoria;
    private Date fechaSubida;
    private String rutaArchivo;
    private String tipo;
    private int id_estudiante;

    public InformeAdjunto(int id_informe, String nombre, boolean confidencial, String categoria, Date fechaSubida, String rutaArchivo, String tipo, int id_estudiante) {
        this.id_informe = id_informe;
        this.nombre = nombre;
        this.confidencial = confidencial;
        this.categoria = categoria;
        this.fechaSubida = fechaSubida;
        this.rutaArchivo = rutaArchivo;
        this.tipo = tipo;
        this.id_estudiante = id_estudiante;
    }

    public int getId_informe() {
        return id_informe;
    }

    public void setId_informe(int id_informe) {
        this.id_informe = id_informe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isConfidencial() {
        return confidencial;
    }

    public void setConfidencial(boolean confidencial) {
        this.confidencial = confidencial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public java.sql.Date getFechaSubida() {
        return (java.sql.Date) fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    @Override
    public String toString() {
        return "InformeAdjunto{" +
                "id_informe=" + id_informe +
                ", nombre='" + nombre + '\'' +
                ", confidencial=" + confidencial +
                ", categoria='" + categoria + '\'' +
                ", fechaSubida=" + fechaSubida +
                ", rutaArchivo='" + rutaArchivo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", id_estudiante=" + id_estudiante +
                '}';
    }
}
