package com.dam.modelo;

import java.time.LocalDate;


import com.dam.vista.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Factura {
    //...atributos...
    private int id; //PK
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

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
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

    public boolean existeFactura(Factura factura) throws Exception {
        String sql = "SELECT * FROM Facturas WHERE id=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, factura.getId());
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new Exception("Error al verificar la factura", e);
        }
    }

    public void altaFactura(Factura factura) throws Exception {

        Auxiliar.validarId(factura.getId(), "Número de Factura");
        Auxiliar.validarFechaFactura(factura.getFecha());

        // Comprobamos los formatos de las claves foráneas (FK) antes de enviarlas a la BD
        Auxiliar.validarDni(factura.getDniCliente());
        Auxiliar.validarDni(factura.getDniEmpleado());
        Auxiliar.validarId(factura.getIdServicio(), "ID Servicio de la factura");
        Auxiliar.validarId(factura.getIdProducto(), "ID Producto de la factura");

        if (existeFactura(factura)) {
            throw new Exception("El número de factura ya existe");
        }
        String sql = "INSERT INTO Facturas VALUES (?,?,?,?,?,?)";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, factura.getId());
            pst.setDate(2, Date.valueOf(factura.getFecha()));
            pst.setString(3, factura.getDniCliente());
            pst.setString(4, factura.getDniEmpleado());
            pst.setInt(5, factura.getIdServicio());
            pst.setInt(6, factura.getIdProducto());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al insertar la factura", e);
        }
    }

    public static void listadoFacturas(List<Factura> facturas) throws Exception {
        String sql = "SELECT * FROM Facturas ORDER BY id";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Factura factura = new Factura();
                factura.setId(rs.getInt("id"));

                Date sqlDate = rs.getDate("fecha");
                if (sqlDate != null) {
                    factura.setFecha(sqlDate.toLocalDate());
                }

                factura.setDniCliente(rs.getString("dniCliente"));
                factura.setDniEmpleado(rs.getString("dniEmpleado"));
                factura.setIdServicio(rs.getInt("idServicio"));
                factura.setIdProducto(rs.getInt("idProducto"));
                facturas.add(factura);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener las facturas", e);
        }
    }
}
