package com.example.prototipo01.entidades;

import java.io.Serializable;

public class Cargo implements Serializable {

    private Integer idCargo;
    private String nombreCargo;
    private String detalleCargo;

    public Cargo(){
    }

    public Cargo(Integer idCargo, String nombreCargo, String detalleCargo) {
        this.idCargo = idCargo;
        this.nombreCargo = nombreCargo;
        this.detalleCargo = detalleCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDetalleCargo() {
        return detalleCargo;
    }

    public void setDetalleCargo(String detalleCargo) {
        this.detalleCargo = detalleCargo;
    }
}
