package com.dam.modelo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionBD {
    /* Atributos **************************************************************/
    private static Connection conn;

    /* Constructores **********************************************************/
    public ConexionBD() {
        conn = null;
    }

    /* Métodos getters & setters **********************************************/
    public static Connection getConn() {
        return conn;
    }

    /* Métodos ****************************************************************/
    private static void crearTablas() throws Exception {
        String sql;
        try (Statement st = conn.createStatement()) {
            sql = "CREATE TABLE IF NOT EXISTS Empleados ("
                    + "dni VARCHAR(9) NOT NULL,"
                    + "nombre VARCHAR(20) NOT NULL,"
                    + "apellidos VARCHAR(20) NOT NULL,"
                    + "telefono VARCHAR(9),"
                    + "CONSTRAINT pk_Empleados PRIMARY KEY (dni) )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Clientes ("
                    + "dni VARCHAR(9) NOT NULL,"
                    + "nombre VARCHAR(20) NOT NULL,"
                    + "apellidos VARCHAR(20) NOT NULL,"
                    + "telefono VARCHAR(9),"
                    + "email VARCHAR(25) NOT NULL UNIQUE,"
                    + "CONSTRAINT pk_Clientes PRIMARY KEY (dni) )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Facturas ("
                    + "id INTEGER,"
                    + "fecha DATE NOT NULL,"
                    + "total DECIMAL(6,2) NOT NULL,"
                    + "dni_cliente VARCHAR(9) NOT NULL,"
                    + "id_servicio INTEGER NOT NULL,"
                    + "id_productos INTEGER NOT NULL,"
                    + "CONSTRAINT fk_dni_cliente FOREIGN KEY (dni_cliente) REFERENCES Clientes(dni) ON DELETE NO ACTION,"
                    + "CONSTRAINT fk_id_servicio FOREIGN KEY (id_servicio) REFERENCES Servicios(id) ON DELETE NO ACTION,"
                    + "CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES Productos(dni) ON DELETE NO ACTION,"
                    + "CONSTRAINT fk_dni_empleado FOREIGN KEY (dni_empleado) REFERENCES Empleados(dni) ON DELETE NO ACTION,"
                    + "CONSTRAINT pk_Facturas PRIMARY KEY (dni) )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Productos ("
                    + "id INTEGER NOT NULL,"
                    + "nombre VARCHAR(20) NOT NULL,"
                    + "precio DECIMAL(6,2) NOT NULL,"
                    + "stock INTEGER NOT NULL,"
                    + "dni_empleado VARCHAR(9) NOT NULL,"
                    + "CONSTRAINT pk_Productos PRIMARY KEY (id) ,"
                    + "CONSTRAINT chk_Productos CHECK (stock >= 0) ,"
                    + "CONSTRAINT fk_Productos FOREIGN KEY (dni_empleado) REFERENCES Empleados (dni)  ON DELETE CASCADE)";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Servicios ("
                    + "id INTEGER PRIMARY KEY,"
                    + "nombre VARCHAR(30) NOT NULL,"
                    + "descripion VARCHAR(30),"
                    + "precio DECIMAL(6,2) NOT NULL,"
                    + "tipo VARCHAR(20) NOT NULL, "
                    + "CONSTRAINT chK_Servicios CHECK(precio >0),"
                    + "CONSTRAINT pk_Servicios PRIMARY KEY (id) )";
            st.executeUpdate(sql);
        } catch (SQLException e) {
            throw new Exception("Error crearTablas()!!", e);
        }
    }

    public static void abrirConexion() throws Exception {
        try (FileInputStream fis = new FileInputStream("dbproperties.txt")) {
            Properties props = new Properties();
            props.load(fis);
            conn = DriverManager.getConnection(
                    props.getProperty("mysql.url"),
                    props.getProperty("mysql.username"),
                    props.getProperty("mysql.password"));
            crearTablas();
        } catch (IOException e) {
            throw new Exception("Error abrirConexion()!! Propiedades!!", e);
        } catch (SQLException e) {
            throw new Exception("Error abrirConexion()!!", e);
        }
    }

    public static void cerrarConexion() throws Exception {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new Exception("Error cerrarConexion()!!", e);
        }
    }
}
