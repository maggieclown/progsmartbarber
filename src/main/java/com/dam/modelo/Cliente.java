package com.dam.modelo;

import com.dam.vista.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase Cliente (Métodos de existe, alta y baja)
 *
 * @author María Magdalena Cano
 *  @author Sergio Torres Llamas
 *  @author Juan de Dios Valero Rodríguez
 *  @since 27/05/2026
 *  @version 12.0
 */


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

    //...Métodos...

    /**
     * Método existeCliente
     *
     * @param cliente Instancia del cliente que contiene el DNI a verificar.
     * @return {@code true} si el DNI ya está registrado en la base de datos; {@code false} en caso contrario.
     * @throws Exception Si ocurre un error de acceso a datos durante la consulta SQL.
     */

    public boolean existeCliente(Cliente cliente) throws Exception {
        String sql = "SELECT 1 FROM Clientes WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, cliente.getDni());
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Error al verificar el cliente", e);
        }
    }

    /**
     * Método altaCliente
     *
     * @param cliente Instancia con los datos del cliente que se desea dar de alta.
     * @throws Exception Si alguna validación de formato falla, si el cliente ya existe, o si ocurre un error en la inserción SQL.
     */

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
        String sql = "INSERT INTO Clientes (dni, nombre, apellidos, telefono, email) VALUES (?,?,?,?,?)";
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


    /**
     * Método bajaCliente
     *
     * @param cliente Instancia del cliente que contiene el DNI del registro a eliminar.
     * @throws Exception Si el formato del DNI es inválido, si el cliente no existe en el sistema, o si ocurre un error en el borrado SQL.
     */

    public void bajaCliente(Cliente cliente) throws Exception {
        Auxiliar.validarDni(cliente.getDni());
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


    /**
     * Método listadoClientes
     *
     * @param clientes Colección en memoria (List) donde se añadirán los clientes recuperados.
     * @throws Exception Si ocurre un error de lectura en la base de datos durante la ejecución de la consulta.
     */


    public static void listadoClientes(List<Cliente> clientes) throws Exception {
        String sql = "SELECT dni, nombre, apellidos, telefono, email FROM Clientes ORDER BY dni";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setDni(rs.getString("dni"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener los clientes", e);
        }
    }
}