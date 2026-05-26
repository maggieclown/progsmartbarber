import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.dam.modelo.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente clientePrueba;

    @BeforeAll
    public static void setUpClass() throws Exception {
        System.out.println("Iniciando entorno de pruebas: Abriendo conexión...");
        ConexionBD.abrirConexion();
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
        System.out.println("Cerrando entorno de pruebas: Cerrando conexión...");
        if (ConexionBD.getConn() != null && !ConexionBD.getConn().isClosed()) {
            ConexionBD.getConn().close();
        }
    }

    @BeforeEach
    public void setUp() {
        clientePrueba = new Cliente();
        clientePrueba.setDni("12345678X");
        clientePrueba.setNombre("Test");
        clientePrueba.setApellidos("User");
        clientePrueba.setTelefono("600123456");
        clientePrueba.setEmail("test@dam.com");
    }

    @AfterEach
    public void tearDown() {
        try {
            Cliente c = new Cliente();
            c.setDni("12345678X");
            String sql = "DELETE FROM Clientes WHERE dni=?";
            try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
                pst.setString(1, c.getDni());
                pst.executeUpdate();
            }
        } catch (Exception ignored) {
        }
    }


    // test existeCliente

    @Test
    public void testExisteCliente_NoExiste() throws Exception {
        System.out.println("Caso: existeCliente -> Debe devolver false si no existe");

        boolean resultado = clientePrueba.existeCliente(clientePrueba);

        assertFalse(resultado, "Debería devolver false para un cliente que no está en la BD");
    }

    @Test
    public void testExisteCliente_Existe() throws Exception {
        System.out.println("Caso: existeCliente -> Debe devolver true si existe");

        clientePrueba.altaCliente(clientePrueba);

        boolean resultado = clientePrueba.existeCliente(clientePrueba);

        assertTrue(resultado, "Debería devolver true para un cliente que sí está en la BD");
    }

    @Test
    public void testExisteCliente_DniVacio_DevuelveFalse() throws Exception {
        System.out.println("Caso: existeCliente -> DNI vacío devuelve false directamente");
        Cliente cVacio = new Cliente(); // DNI es ""

        boolean resultado = cVacio.existeCliente(cVacio);

        assertFalse(resultado);
    }


    // test altaCliente


    @Test
    public void testAltaCliente_Correcto() throws Exception {
        System.out.println("Caso: altaCliente -> Flujo correcto");

        assertDoesNotThrow(() -> {
            clientePrueba.altaCliente(clientePrueba);
        });


        assertTrue(clientePrueba.existeCliente(clientePrueba));
    }

    @Test
    public void testAltaCliente_Duplicado_LanzaExcepcion() throws Exception {
        System.out.println("Caso: altaCliente -> Registrar DNI duplicado lanza error");


        clientePrueba.altaCliente(clientePrueba);

        Exception exception = assertThrows(Exception.class, () -> {
            clientePrueba.altaCliente(clientePrueba);
        });


        assertTrue(exception.getMessage().contains("No se puede registrar: Ya existe"));
    }

    @Test
    public void testAltaCliente_ObjetoNulo_LanzaExcepcion() {
        System.out.println("Caso: altaCliente -> Objeto null lanza error");

        Exception exception = assertThrows(Exception.class, () -> {
            clientePrueba.altaCliente(null);
        });

        assertEquals("No se han proporcionado datos del cliente.", exception.getMessage());
    }


    // test bajaCliente

    @Test
    public void testBajaCliente_Correcto() throws Exception {
        System.out.println("Caso: bajaCliente -> Flujo correcto");


        clientePrueba.altaCliente(clientePrueba);


        assertDoesNotThrow(() -> {
            clientePrueba.bajaCliente(clientePrueba);
        });

        assertFalse(clientePrueba.existeCliente(clientePrueba));
    }

    @Test
    public void testBajaCliente_NoExiste_LanzaExcepcion() {
        System.out.println("Caso: bajaCliente -> Intentar borrar alguien que no existe lanza error");

        // No lo insertamos. Intentar borrarlo directamente debe fallar
        Exception exception = assertThrows(Exception.class, () -> {
            clientePrueba.bajaCliente(clientePrueba);
        });

        assertTrue(exception.getMessage().contains("No se puede eliminar: No existe ningún cliente"));
    }
}