package com.dam.modelo;


import com.dam.vista.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Servicio {
    //...atributos...
    private int id; //PK
    private String nombre;
    private String descripcion;
    private double precio;
    private TIPOS tipos;

    public enum TIPOS {
        Corte, Barba, Tinte, Mechas
    }

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
    public TIPOS getTipos() {
        return tipos;
    }
    public String getTiposString() {
        if (tipos == null) {
            return "";
        } else {
            return tipos.toString();
        }
    }
    public void setTipos(TIPOS tipos) {
        this.tipos = tipos;
    }

    public boolean existeServicio(Servicio servicio) throws Exception {
        String sql = "SELECT * FROM Servicios WHERE id=?";
        try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, servicio.getId());
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new Exception("Error al verificar el servicio", e);
        }
    }

    public void altaServicio(Servicio servicio) throws Exception {

        Auxiliar.validarId(servicio.getId(), "ID Servicio");
        Auxiliar.validarTextoObligatorio(servicio.getNombre(), "Nombre Servicio");
        Auxiliar.validarTextoObligatorio(servicio.getDescripcion(), "Descripción");
        Auxiliar.validarPrecio(servicio.getPrecio());

        // Ejecuta la validación que avisa por consola si viene null
        Auxiliar.validarTipoServicio(servicio.getTipos());

        if (existeServicio(servicio)) {
            throw new Exception("El servicio con esa ID ya existe");
        }
        String sql = "INSERT INTO Servicios VALUES (?,?,?,?,?)";
        try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, servicio.getId());
            pst.setString(2, servicio.getNombre());
            pst.setString(3, servicio.getDescripcion());
            pst.setDouble(4, servicio.getPrecio());
            pst.setString(5, servicio.getTipos() != null ? servicio.getTipos().name() : null);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al insertar el servicio", e);
        }
    }

    public void bajaServicio(Servicio servicio) throws Exception {
        if (!existeServicio(servicio)) {
            throw new Exception("El servicio no existe");
        }
        String sql = "DELETE FROM Servicios WHERE id=?";
        try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, servicio.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al borrar el servicio", e);
        }
    }

    public static void listadoServicios(List<Servicio> servicios) throws Exception {
        String sql = "SELECT * FROM Servicios ORDER BY id";
        try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Servicio servicio = new Servicio();
                servicio.setId(rs.getInt("id"));
                servicio.setNombre(rs.getString("nombre"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecio(rs.getDouble("precio"));

                String tipoStr = rs.getString("tipos");
                if (tipoStr != null) {
                    servicio.setTipos(Servicio.TIPOS.valueOf(tipoStr));
                }

                servicios.add(servicio);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener los servicios", e);
        }
    }

}
