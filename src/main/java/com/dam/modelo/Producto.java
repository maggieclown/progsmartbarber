package com.dam.modelo;

import com.dam.vista.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Producto {

    //...atributos...
    private int id;
    private String nombre;
    private double precio;
    private int stock; // Tipo int alineado a la BD
    private String dniEmpleado; // Clave Foránea obligatoria

    //...constructor...
    public Producto() {
        this.id = 0;
        this.nombre = "";
        this.precio = 0.0;
        this.stock = 0;
        this.dniEmpleado = "";
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }


    //...Métodos...
    public boolean existeProducto(Producto producto) throws Exception {
        String sql = "SELECT 1 FROM Productos WHERE id=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, producto.getId());
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new Exception("Error al verificar el producto", e);
        }
    }

    public void altaProducto(Producto producto) throws Exception {
        Auxiliar.validarId(producto.getId(), "ID Producto");
        Auxiliar.validarTextoObligatorio(producto.getNombre(), "Nombre Producto");
        Auxiliar.validarPrecio(producto.getPrecio());
        Auxiliar.validarStock(producto.getStock());
        Auxiliar.validarDni(producto.getDniEmpleado());

        if (existeProducto(producto)) {
            throw new Exception("El producto con esa ID ya existe");
        }
        String sql = "INSERT INTO Productos (id, nombre, precio, stock, dni_empleado) VALUES (?,?,?,?,?)";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, producto.getId());
            pst.setString(2, producto.getNombre());
            pst.setDouble(3, producto.getPrecio());
            pst.setInt(4, producto.getStock());
            pst.setString(5, producto.getDniEmpleado());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al insertar el producto", e);
        }
    }

    public void bajaProducto(Producto producto) throws Exception {
        if (!existeProducto(producto)) {
            throw new Exception("El producto no existe");
        }
        String sql = "DELETE FROM Productos WHERE id=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, producto.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al borrar el producto", e);
        }
    }

    public static void listadoProductos(List<Producto> productos) throws Exception {
        String sql = "SELECT id, nombre, precio, stock, dni_empleado FROM Productos ORDER BY id";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setDniEmpleado(rs.getString("dni_empleado"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener los productos", e);
        }
    }
}