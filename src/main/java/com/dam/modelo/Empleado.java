package com.dam.modelo;

public class Empleado {

    //...atributos...
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;

    //...Constructor...
    public Empleado() {
        this.dni = ""; //PK
        this.nombre = "";
        this.apellidos = "";
        this.telefono = "";
    }

    //...Setters & Getters...

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}