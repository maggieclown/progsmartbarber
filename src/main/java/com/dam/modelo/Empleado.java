package com.dam.modelo;

import com.dam.vista.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * Clase Empleado (Métodos de existe, alta y baja)
 *
 * @author María Magdalena Cano
 * @author Sergio Torres Llamas
 * @author Juan de Dios Valero Rodríguez
 * @version 12.0
 * @since 27/05/2026
 */

public class Empleado {

    //...atributos...
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;

    //...constructor...
    public Empleado() {
        this.dni = "";
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

    //...Métodos...

    /**
     * Método existeEmpleados
     *
     * @param empleado Instancia del empleado que contiene el DNI objeto de la búsqueda.
     * @return {@code true} si el registro existe en la base de datos; {@code false} en caso contrario.
     * @throws Exception Si se produce una anomalía de comunicación o un error de sintaxis en la consulta SQL.
     */

    public boolean existeEmpleado(Empleado empleado) throws Exception {
        String sql = "SELECT 1 FROM Empleados WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, empleado.getDni());
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Error al verificar el empleado", e);
        }
    }


    /**
     * Método altaEmpleados
     *
     * @param empleado Instancia con los datos completos del empleado a insertar.
     * @throws Exception Si las validaciones de formato fallan, si el DNI está duplicado o si surge un problema durante la inserción en la base de datos.
     */

    public void altaEmpleado(Empleado empleado) throws Exception {
        Auxiliar.validarDni(empleado.getDni());
        Auxiliar.validarTextoObligatorio(empleado.getNombre(), "Nombre");
        Auxiliar.validarTextoObligatorio(empleado.getApellidos(), "Apellidos");
        Auxiliar.validarTelefono(empleado.getTelefono());

        if (existeEmpleado(empleado)) {
            throw new Exception("El empleado ya existe");
        }
        String sql = "INSERT INTO Empleados (dni, nombre, apellidos, telefono) VALUES (?,?,?,?)";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, empleado.getDni());
            pst.setString(2, empleado.getNombre());
            pst.setString(3, empleado.getApellidos());
            pst.setString(4, empleado.getTelefono());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al insertar el empleado", e);
        }
    }


    /**
     * Método bajaEmpleados
     *
     * @param empleado Instancia del empleado que almacena el DNI que se desea borrar.
     * @throws Exception Si el DNI suministrado no es válido, si el empleado no consta en el sistema o si se genera un error de persistencia SQL.
     */

    public void bajaEmpleado(Empleado empleado) throws Exception {
        Auxiliar.validarDni(empleado.getDni());
        if (!existeEmpleado(empleado)) {
            throw new Exception("El empleado no existe");
        }
        String sql = "DELETE FROM Empleados WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, empleado.getDni());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al borrar el empleado", e);
        }
    }


    /**
     * Método listadoEmpleados
     *
     * @param empleados Colección destino (List) donde se inyectarán los objetos Empleado recuperados.
     * @throws Exception Si ocurre un fallo en el mapeo de datos o en la consulta de lectura de la base de datos.
     */

    public static void listadoEmpleados(List<Empleado> empleados) throws Exception {
        String sql = "SELECT dni, nombre, apellidos, telefono FROM Empleados ORDER BY dni";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setDni(rs.getString("dni"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellidos(rs.getString("apellidos"));
                empleado.setTelefono(rs.getString("telefono"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener los empleados", e);
        }
    }
}