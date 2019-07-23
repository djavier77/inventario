package com.example.prototipo01.entidades;

import java.io.Serializable;

public class Usuarios implements Serializable {

    private String cedula;
    private String nombres;
    private String fecha_ingreso;
    private String nickname;
    private String password;
    private String telefono;
    private String mail;
    private Integer id_usuario;
    private Integer id_cargo;

    public Usuarios() {
    }

    public Usuarios(String cedula, String nombres, String fecha_ingreso, String nickname, String password, String telefono, String mail, Integer id_usuario, Integer id_cargo) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.fecha_ingreso = fecha_ingreso;
        this.nickname = nickname;
        this.password = password;
        this.telefono = telefono;
        this.mail = mail;
        this.id_usuario = id_usuario;
        this.id_cargo = id_cargo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_cargo() {return id_cargo; }

    public void setId_cargo(Integer id_cargo) {this.id_cargo = id_cargo;    }
}

