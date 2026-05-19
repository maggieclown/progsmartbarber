package com.dam.modelo;

public class Cliente {

    //...atributos...
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;

    //...Constructor...
    public Cliente() {
        this.dni = "";
        this.nombre = "";
        this.apellidos = "";
        this.telefono = "";
        this.email = "";
    }


    //...Setters & Getters...
    public int getDni() {
        return dni;
    }

    public void setDni(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}