package com.dam.modelo;

import com.dam.vista.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase Producto (Métodos de existe, alta, baja y listado)
 *
 * @author María Magdalena Cano
 * @author Sergio Torres Llamas
 * @author Juan de Dios Valero Rodríguez
 * @version 12.0
 * @since 27/05/2026
 */


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

    /**
     * Método existeProducto
     *
     * @param producto Instancia que contiene el ID de producto a verificar.
     * @return {@code true} si el producto ya está registrado; {@code false} en caso contrario.
     * @throws Exception Si ocurre un error de comunicación o de sintaxis SQL al realizar la consulta.
     */

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


    /**
     * Método altaProducto
     *
     * @param producto Instancia con los datos completos del producto a dar de alta.
     * @throws Exception Si falla alguna validación de formato, si el ID del producto ya existe, o si se produce un error de inserción SQL (por ejemplo, si la FK del empleado no existe).
     */

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

    /**
     * Método bajaProducto
     *
     * @param producto Instancia del producto que almacena el ID del registro a eliminar.
     * @throws Exception Si el producto no consta en el sistema o si surge una excepción de restricción o borrado SQL en la base de datos.
     */


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


    /**
     * Método listadoProductos
     *
     * @param productos Colección destino (List) donde se inyectarán los objetos Producto mapeados.
     * @throws Exception Si ocurre un fallo en el volcado de datos o durante la lectura de la consulta relacional SQL.
     */

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