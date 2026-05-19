package com.dam.modelo;


import com.dam.vista.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Producto {
    //...atributos...
    private int id; //PK
    private String nombre;
    private double precio;
    private double stock;


    //...Constructor...
    public Producto() {
        this.id = 0;
        this.nombre = "";
        this.precio = 0;
        this.stock = 0;
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

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }


    public boolean existeProducto(Producto producto) throws Exception {
        String sql = "SELECT * FROM Productos WHERE id=?";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, producto.getId());
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new Exception("Error al verificar el producto", e);
        }
    }

    public void altaProducto(Producto producto) throws Exception {

        Auxiliar.validarId(producto.getId(), "ID Producto");
        Auxiliar.validarTextoObligatorio(producto.getNombre(), "Nombre Producto");
        Auxiliar.validarPrecio(producto.getPrecio());
        Auxiliar.validarStock(producto.getStock());

        if (existeProducto(producto)) {
            throw new Exception("El producto con esa ID ya existe");
        }
        String sql = "INSERT INTO Productos VALUES (?,?,?,?)";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            pst.setInt(1, producto.getId());
            pst.setString(2, producto.getNombre());
            pst.setDouble(3, producto.getPrecio());
            pst.setDouble(4, producto.getStock());
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
        String sql = "SELECT * FROM Productos ORDER BY id";
        try (PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getDouble("stock"));
                productos.add(producto);
            }
        } catch (Exception e) {
            throw new Exception("Error al obtener los productos", e);
        }
    }
}
