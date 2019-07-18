package com.example.prototipo01.entidades;

import java.io.Serializable;

public class Usuario implements  Serializable{

    private Integer id_usuario;
    private String cedula;
    private String nombre;
    private String fechaIngreso;
    private String ninckname;
    private String password;
    private String telefono;
    private String correo;
    private Integer id_cargo;

    public Usuario(){

    }

    public Usuario(Integer id_usuario, String cedula, String nombre, String fechaIngreso, String ninckname, String password, String telefono, String correo, Integer id_cargo) {
        this.id_usuario = id_usuario;
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.ninckname = ninckname;
        this.password = password;
        this.telefono = telefono;
        this.correo = correo;
        this.id_cargo = id_cargo;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNinckname() {
        return ninckname;
    }

    public void setNinckname(String ninckname) {
        this.ninckname = ninckname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(Integer id_cargo) {
        this.id_cargo = id_cargo;
    }
}
