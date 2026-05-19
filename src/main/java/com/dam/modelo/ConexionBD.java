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
            sql = "CREATE TABLE IF NOT EXISTS Clientes ("
                    + "dni VARCHAR(10) NOT NULL,"
                    + "nombre VARCHAR(50) NOT NULL,"
                    + "CONSTRAINT pk_Profesores PRIMARY KEY (dni) )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Empleados ("
                    + "id INTEGER NOT NULL,"
                    + "titulo VARCHAR(50) NOT NULL,"
                    + "horas DECIMAL(6,2) NOT NULL,"
                    + "dniProfesor VARCHAR(10) NULL,"
                    + "fecIni DATE NULL,"
                    + "fecFin DATE NULL,"
                    + "modalidad CHAR(1) NOT NULL,"
                    + "estado VARCHAR(12) NULL,"
                    + "CONSTRAINT pk_Cursos PRIMARY KEY (id),"
                    + "CONSTRAINT fk_Cursos_Profesores FOREIGN KEY (dniProfesor)"
                    + " REFERENCES Profesores (dni) ON DELETE SET NULL )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Facturas ("
                    + "dni VARCHAR(10) NOT NULL,"
                    + "nombre VARCHAR(50) NOT NULL,"
                    + "mayorEdad BOOLEAN NOT NULL,"
                    + "CONSTRAINT pk_Alumnos PRIMARY KEY (dni) )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Productos ("
                    + "idCurso INTEGER NOT NULL,"
                    + "dniAlumno VARCHAR(10) NOT NULL,"
                    + "CONSTRAINT pk_Matriculas PRIMARY KEY (idCurso,dniAlumno),"
                    + "CONSTRAINT fk_Matriculas_Cursos FOREIGN KEY (idCurso)"
                    + " REFERENCES Cursos (id) ON DELETE CASCADE,"
                    + "CONSTRAINT fk_Matriculas_Alumnos FOREIGN KEY (dniAlumno)"
                    + " REFERENCES Alumnos (dni) ON DELETE CASCADE )";
            st.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS Servicios ("
                    + "dni VARCHAR(10) NOT NULL,"
                    + "nombre VARCHAR(50) NOT NULL,"
                    + "CONSTRAINT pk_Profesores PRIMARY KEY (dni) )";
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