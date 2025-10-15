package org.example.modelo;

public class ITR {
    private int id;
    private String ubicacion;
    private String region;
    private String calle;
    private String nro_puerta;
    private String descripcion;

    public ITR(int id) {
        this.id = id;
    }

    public ITR(int id, String ubicacion, String region, String calle, String nro_puerta, String descripcion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.region = region;
        this.calle = calle;
        this.nro_puerta = nro_puerta;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNro_puerta() {
        return nro_puerta;
    }

    public void setNro_puerta(String nro_puerta) {
        this.nro_puerta = nro_puerta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ITR{" +
                "id=" + id +
                ", ubicacion='" + ubicacion + '\'' +
                ", region='" + region + '\'' +
                ", calle='" + calle + '\'' +
                ", nro_puerta='" + nro_puerta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

