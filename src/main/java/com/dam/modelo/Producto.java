package com.dam.modelo;

import java.time.LocalDate;

public class Producto {
    //...atributos...
    private int id; //PK
    private String nombre;
    private double precio;
    private double stock;

    //...Constructor...
    public Producto() {
        this.id = 0;
        this.nombre = "";
        this.precio = 0;
        this.stock = 0;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}