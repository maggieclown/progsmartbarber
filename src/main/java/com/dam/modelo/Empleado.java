package com.dam.modelo;

import com.dam.vista.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Empleado {

    //...atributos...
    private String dni; //PK
    private String nombre;
    private String apellidos;
    private String telefono;

    //...Constructor...
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

    public boolean existeEmpleado(Empleado empleado) throws Exception {
        String sql = "SELECT * FROM Empleados WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, empleado.getDni());
            ResultSet rs = pst.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            throw new Exception("Error al obtener el empleado", e);
        }
    }

    public void altaEmpleado(Empleado empleado) throws Exception {
        if (existeEmpleado(empleado)) {
            throw new Exception("El empleado ya existe");
        }
        String sql = "INSERT INTO Empleados VALUES (?,?,?,?)";
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

    public void bajaEmpleado(Empleado empleado) throws Exception {
        if (!existeEmpleado(empleado)) {
            throw new Exception("El empleado no existe");
        }
        String sql = "DELETE FROM Empleados WHERE dni=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setString(1, empleado.getDni());
            pst.executeUpdate();
        }  catch (SQLException e) {
            throw new Exception("Error al borrar el empleado", e);
        }
    }

    /* public void actualizaEmpleado(Empleado empleado) throws Exception {
         if (!existeEmpleado(empleado)) {
             throw new Exception("El empleado no existe");
         }
         String sql = "UPDATE Empleados SET nombre =? WHERE dni=?";
         try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
             pst.setString(1, empleado.getNombre());
             pst.setString(2, empleado.getDni());
             pst.executeUpdate();
         }  catch (SQLException e) {
             throw new Exception("Error al actualizar el empleado", e);
         }
     }
     */
    public static void listadoEmpleados(List<Empleado> empleados) throws Exception {
        String sql = "SELECT * FROM Empleados ORDER BY dni";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            Empleado empleado;
            while (rs.next()) {
                empleado = new Empleado();
                empleado.setDni(rs.getString("dni"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellidos(rs.getString("apellidos"));
                empleado.setTelefono(rs.getString("telefono"));
                empleados.add(empleado);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener el empleado", e);
        }


    }
}
