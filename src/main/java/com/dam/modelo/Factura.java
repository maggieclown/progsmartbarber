package com.dam.modelo;

import java.time.LocalDate;

public class Factura {
    //...atributos...
    private int id;
    private LocalDate fecha;
    private String dniCliente;
    private String dniEmpleado;
    private int idServicio;
    private int idProducto;

    //...Constructor...
    public Factura() {
        this.id = 0;
        this.fecha = null;
        this.dniCliente = "";
        this.dniEmpleado = "";
        this.idServicio = 0;
        this.idProducto = 0;
    }


    //...Setters & Getters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(int dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}