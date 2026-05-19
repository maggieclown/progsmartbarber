package com.dam.modelo;

import java.time.LocalDate;

public class Servicio {
    //...atributos...
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;

    //...Constructor...
    public Servicio() {
        this.id = 0;
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0;
    }

    //...Setters & Getters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}