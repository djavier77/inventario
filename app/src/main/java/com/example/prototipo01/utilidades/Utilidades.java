package com.example.prototipo01.utilidades;

public class Utilidades {



    //Constantes campos tabla cargo
    public static final String TABLA_CARGO="cargo";
    public static final String CAMPO_ID_CARGO="id_cargo";
    public static final String CAMPO_NOMBRE_CARGO="nombre_cargo";
    public static final String CAMPO_DETALLE_CARGO="detalle_cargo";
    //FOREIGN KEY("id_tipo") REFERENCES "tipo_usuario"("id_tipo")


    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO="usuario";
    public static final String CAMPO_ID_USUARIO="id_usuario";
    public static final String CAMPO_CEDULA="cedula";
    public static final String CAMPO_NOMBRES="nombres";
    public static final String CAMPO_FECHA_INGRESO="fecha_ingreso";
    public static final String CAMPO_NICKNAME="nickname";
    public static final String CAMPO_PASSWORD="password";
    public static final String CAMPO_TELEFONO="telefono";
    public static final String CAMPO_MAIL="mail";
    //public static final String CAMPO_USUARIO_ID_CARGO="id_usuario_cargo";
    //public static final String CAMPO_CIUDAD="ciudad";

    //Constantes campos tabla producto
    public static final String TABLA_PRODUCTOS="produtos";
    public static final String CAMPO_ID_PRODUCTO="id_producto";
    public static final String CAMPO_CODIGO_PRODUCTO="codigo_producto";
    public static final String CAMPO_NOMBRE_PRODUCTO="id_nombre_producto";
    public static final String CAMPO_CANTIDAD="cantidad";
    public static final String CAMPO_CANTIDAD_MINIMA="cantidad_minima";
    public static final String CAMPO_PRECIO="precio";
    public static final String CAMPO_DETALLE_PRODUCTO="detalle_producto";
    //public static final String CAMPO_PRODUCTO_USUARIO="id_producto_usuario";
    //public static final String CAMPO_OBSERVACION_TIPO_MASCOTA="observacion_tipo_mascota";

    public static final String CREAR_TABLA_CARGO="CREATE TABLE "+TABLA_CARGO+" ("+CAMPO_ID_CARGO+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CAMPO_NOMBRE_CARGO+" TEXT, "+CAMPO_DETALLE_CARGO+" TEXT)";

    public static final String CREAR_TABLA_USUARIO="CREATE TABLE "+TABLA_USUARIO+" ( "+CAMPO_ID_USUARIO+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " "+CAMPO_CEDULA+" "+"TEXT UNIQUE, " + CAMPO_NOMBRES+" "+"TEXT, "+CAMPO_FECHA_INGRESO+" "+"TEXT, " +
            CAMPO_NICKNAME+" "+"TEXT UNIQUE, " + CAMPO_PASSWORD+" "+"TEXT NOT NULL, " + CAMPO_TELEFONO+" "+"TEXT, " + CAMPO_MAIL+" "+"TEXT,"+CAMPO_ID_CARGO+" INTEGER NOT NULL," +
            "FOREIGN KEY("+CAMPO_ID_CARGO+") REFERENCES "+TABLA_CARGO+"("+CAMPO_ID_CARGO+") )";

    public static final String CREAR_TABLA_PRODUCTOS="CREATE TABLE "+TABLA_PRODUCTOS+" ("+CAMPO_ID_PRODUCTO+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CAMPO_CODIGO_PRODUCTO+" TEXT UNIQUE, "+CAMPO_NOMBRE_PRODUCTO+" TEXT, "+CAMPO_CANTIDAD+" INTEGER,"+CAMPO_CANTIDAD_MINIMA+" INTEGER, "+CAMPO_PRECIO+" TEXT, "
            +CAMPO_DETALLE_PRODUCTO+" TEXT, "+CAMPO_ID_USUARIO+" INTEGER NOT NULL," +
            "FOREIGN KEY("+CAMPO_ID_USUARIO+") REFERENCES "+TABLA_USUARIO+"("+CAMPO_ID_USUARIO+") )";

    //Constantes campos tabla
    public static final String TABLA_PAIS="pais";
    public static final String CAMPO_ID_PAIS="id_pais";
    public static final String CAMPO_NOMBRE_PAIS="nombre_pais";
    public static final String CAMPO_OBSERVACIONES="observaciones";

    public static final String CREAR_TABLA_PAIS="CREATE TABLE "+TABLA_PAIS+" ("+CAMPO_ID_PAIS+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +CAMPO_NOMBRE_PAIS+" TEXT, "+CAMPO_OBSERVACIONES+" TEXT)";


}

