package com.example.retocomercial;

import java.io.Serializable;

public class Pedido implements Serializable {

    private String titulo;
    private String contenido;
    private String contenido2;

    public Pedido(String titulo,String contenido,String contenido2){
        this.titulo = titulo;
        this.contenido = contenido;
        this.contenido2 = contenido2;
    }
    public String getTitulo(){return titulo;}
    public String getContenido(){return contenido;}
    public String getContenido2(){return contenido2;}
}