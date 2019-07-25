package com.example.prototipo01.entidades;

import java.io.Serializable;

public class Productos implements Serializable {
    private String id_producto;
    private String codigo_producto;
    private String id_nombre_producto;
    private Integer cantidad;
    private Integer cantidad_minima;
    private String precio;
    private String detalle_producto;
    private Integer id_usuario;

    public Productos() {
    }

    public Productos(String id_producto, String codigo_producto, String id_nombre_producto, Integer cantidad, Integer cantidad_minima, String precio, String detalle_producto, Integer id_usuario) {
        this.id_producto = id_producto;
        this.codigo_producto = codigo_producto;
        this.id_nombre_producto = id_nombre_producto;
        this.cantidad = cantidad;
        this.cantidad_minima = cantidad_minima;
        this.precio = precio;
        this.detalle_producto = detalle_producto;
        this.id_usuario = id_usuario;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_nombre_producto() {
        return id_nombre_producto;
    }

    public void setId_nombre_producto(String id_nombre_producto) {
        this.id_nombre_producto = id_nombre_producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidad_minima() {
        return cantidad_minima;
    }

    public void setCantidad_minima(Integer cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDetalle_producto() {
        return detalle_producto;
    }

    public void setDetalle_producto(String detalle_producto) {
        this.detalle_producto = detalle_producto;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }
}
