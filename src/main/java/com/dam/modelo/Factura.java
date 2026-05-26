package com.dam.modelo;

import com.dam.vista.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Factura {

    //..atributos..
    private int id;
    private LocalDate fecha;
    private double total; // Atributo mapeado para la BD
    private String dniCliente;
    private String dniEmpleado;
    private int idServicio;
    private int idProducto;

    //...Constructor...
    public Factura() {
        this.id = 0;
        this.fecha = null;
        this.total = 0.0;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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


    //...Métodos...
    public boolean existeFactura(Factura factura) throws Exception {
        String sql = "SELECT 1 FROM Facturas WHERE id=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, factura.getId());
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Error al verificar la factura", e);
        }
    }

    public void altaFactura(Factura factura) throws Exception {
        Auxiliar.validarId(factura.getId(), "Número de Factura");
        Auxiliar.validarFechaFactura(factura.getFecha());
        Auxiliar.validarPrecio(factura.getTotal());
        Auxiliar.validarDni(factura.getDniCliente());
        Auxiliar.validarDni(factura.getDniEmpleado());
        Auxiliar.validarId(factura.getIdServicio(), "ID Servicio");
        Auxiliar.validarId(factura.getIdProducto(), "ID Producto");

        if (existeFactura(factura)) {
            throw new Exception("El número de factura ya existe");
        }

        String sql = "INSERT INTO Facturas (id, fecha, total, dni_cliente, dni_empleado, id_servicio, id_producto) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, factura.getId());
            pst.setDate(2, Date.valueOf(factura.getFecha()));
            pst.setDouble(3, factura.getTotal());
            pst.setString(4, factura.getDniCliente());
            pst.setString(5, factura.getDniEmpleado());
            pst.setInt(6, factura.getIdServicio());
            pst.setInt(7, factura.getIdProducto());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al insertar la factura. Verifique que las FKs existan.", e);
        }
    }

    public static void listadoFacturas(List<Factura> facturas) throws Exception {

        String sql = "SELECT id, fecha, total, dni_cliente, dni_empleado, id_servicio, id_producto FROM Facturas ORDER BY id";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Factura factura = new Factura();
                factura.setId(rs.getInt("id"));

                Date sqlDate = rs.getDate("fecha");
                if (sqlDate != null) {
                    factura.setFecha(sqlDate.toLocalDate());
                }

                factura.setTotal(rs.getDouble("total"));
                factura.setDniCliente(rs.getString("dni_cliente"));
                factura.setDniEmpleado(rs.getString("dni_empleado"));
                factura.setIdServicio(rs.getInt("id_servicio"));
                factura.setIdProducto(rs.getInt("id_producto"));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener las facturas", e);
        }
    }
}