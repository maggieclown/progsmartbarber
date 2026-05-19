package com.dam.modelo;

import com.dam.vista.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Cliente {

    //...atributos...
    private String dni; //PK
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean existeCliente(Cliente cliente) throws Exception {
        String sql = "SELECT * FROM Clientes WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, cliente.getDni());
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new Exception("Error al verificar el cliente", e);
        }
    }

    public void altaCliente(Cliente cliente) throws Exception {

        Auxiliar.validarDni(cliente.getDni());
        Auxiliar.validarTextoObligatorio(cliente.getNombre(), "Nombre");
        Auxiliar.validarTextoObligatorio(cliente.getApellidos(), "Apellidos");
        Auxiliar.validarTelefono(cliente.getTelefono());
        Auxiliar.validarTextoObligatorio(cliente.getEmail(), "Email");
        Auxiliar.validarEmail(cliente.getEmail());

        if (existeCliente(cliente)) {
            throw new Exception("El cliente ya existe");
        }
        String sql = "INSERT INTO Clientes VALUES (?,?,?,?,?)";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, cliente.getDni());
            pst.setString(2, cliente.getNombre());
            pst.setString(3, cliente.getApellidos());
            pst.setString(4, cliente.getTelefono());
            pst.setString(5, cliente.getEmail());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al insertar el cliente", e);
        }
    }

    public void bajaCliente(Cliente cliente) throws Exception {
        if (!existeCliente(cliente)) {
            throw new Exception("El cliente no existe");
        }
        String sql = "DELETE FROM Clientes WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, cliente.getDni());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al borrar el cliente", e);
        }
    }

    public static void listadoClientes(List<Cliente> clientes) throws Exception {
        String sql = "SELECT * FROM Clientes ORDER BY dni";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener los clientes", e);
        }
    }
}

