import com.dam.modelo.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoTest {

    private Empleado empleadoPrueba;

    @BeforeAll
    public static void setUpClass() throws Exception {
        System.out.println("Iniciando entorno de pruebas de Empleado: Abriendo conexión...");
        ConexionBD.abrirConexion();
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
        System.out.println("Cerrando entorno de pruebas de Empleado: Cerrando conexión...");
        if (ConexionBD.getConn() != null && !ConexionBD.getConn().isClosed()) {
            ConexionBD.getConn().close();
        }
    }

    @BeforeEach
    public void setUp() {
        empleadoPrueba = new Empleado();
        empleadoPrueba.setDni("87654321Z");
        empleadoPrueba.setNombre("Barbero");
        empleadoPrueba.setApellidos("Test");
        empleadoPrueba.setTelefono("611987654");
    }

    @AfterEach
    public void tearDown() {
        try {
            // Limpieza preventiva: eliminamos el registro de test pase lo que pase
            Empleado e = new Empleado();
            e.setDni("87654321Z");
            String sql = "DELETE FROM Empleados WHERE dni=?";
            try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
                pst.setString(1, e.getDni());
                pst.executeUpdate();
            }
        } catch (Exception ignored) {
        }
    }

    //test  existeEmpleado

    @Test
    public void testExisteEmpleado_NoExiste() throws Exception {
        System.out.println("Caso: existeEmpleado -> Debe devolver false si no existe");
        boolean resultado = empleadoPrueba.existeEmpleado(empleadoPrueba);
        assertFalse(resultado);
    }

    @Test
    public void testExisteEmpleado_Existe() throws Exception {
        System.out.println("Caso: existeEmpleado -> Debe devolver true si existe");
        empleadoPrueba.altaEmpleado(empleadoPrueba);

        boolean resultado = empleadoPrueba.existeEmpleado(empleadoPrueba);
        assertTrue(resultado);
    }


    // test altaEmpleado

    @Test
    public void testAltaEmpleado_Correcto() throws Exception {
        System.out.println("Caso: altaEmpleado -> Flujo correcto");
        assertDoesNotThrow(() -> {
            empleadoPrueba.altaEmpleado(empleadoPrueba);
        });
        assertTrue(empleadoPrueba.existeEmpleado(empleadoPrueba));
    }

    @Test
    public void testAltaEmpleado_Duplicado_LanzaExcepcion() throws Exception {
        System.out.println("Caso: altaEmpleado -> Registrar duplicado lanza error");
        empleadoPrueba.altaEmpleado(empleadoPrueba);

        Exception exception = assertThrows(Exception.class, () -> {
            empleadoPrueba.altaEmpleado(empleadoPrueba);
        });
        assertTrue(exception.getMessage().contains("Ya existe un empleado"));
    }


    // test bajaEmpleado

    @Test
    public void testBajaEmpleado_Correcto() throws Exception {
        System.out.println("Caso: bajaEmpleado -> Flujo correcto");
        empleadoPrueba.altaEmpleado(empleadoPrueba);

        assertDoesNotThrow(() -> {
            empleadoPrueba.bajaEmpleado(empleadoPrueba);
        });
        assertFalse(empleadoPrueba.existeEmpleado(empleadoPrueba));
    }

    @Test
    public void testBajaEmpleado_NoExiste_LanzaExcepcion() {
        System.out.println("Caso: bajaEmpleado -> Eliminar inexistente lanza error");
        Exception exception = assertThrows(Exception.class, () -> {
            empleadoPrueba.bajaEmpleado(empleadoPrueba);
        });
        assertTrue(exception.getMessage().contains("No existe ningún empleado"));
    }

}