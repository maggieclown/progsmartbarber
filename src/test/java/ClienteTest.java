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

    // Se ejecuta una sola vez antes de todos los tests para abrir la conexión
    @BeforeAll
    public static void setUpClass() throws Exception {
        System.out.println("Iniciando entorno de pruebas: Abriendo conexión...");
        ConexionBD.abrirConexion();
    }

    // Se ejecuta una sola vez al final de todos los tests para cerrar la conexión
    @AfterAll
    public static void tearDownClass() throws Exception {
        System.out.println("Cerrando entorno de pruebas: Cerrando conexión...");
        if (ConexionBD.getConn() != null && !ConexionBD.getConn().isClosed()) {
            ConexionBD.getConn().close();
        }
    }

    // Se ejecuta ANTES de cada @Test. Prepara un cliente limpio para las pruebas.
    @BeforeEach
    public void setUp() {
        clientePrueba = new Cliente();
        clientePrueba.setDni("12345678X");
        clientePrueba.setNombre("Test");
        clientePrueba.setApellidos("User");
        clientePrueba.setTelefono("600123456");
        clientePrueba.setEmail("test@dam.com");
    }

    // Se ejecuta DESPUÉS de cada @Test.
    // Limpia la base de datos para que un test no altere el resultado del siguiente.
    @AfterEach
    public void tearDown() {
        try {
            // Intentamos borrar el cliente de prueba por si se ha quedado en la BD
            Cliente c = new Cliente();
            c.setDni("12345678X");
            String sql = "DELETE FROM Clientes WHERE dni=?";
            try (java.sql.PreparedStatement pst = ConexionBD.getConn().prepareStatement(sql)) {
                pst.setString(1, c.getDni());
                pst.executeUpdate();
            }
        } catch (Exception ignored) {
            // Si no existía, ignoramos el fallo
        }
    }

    // =========================================================================
    // TESTS PARA: existeCliente()
    // =========================================================================

    @Test
    public void testExisteCliente_NoExiste() throws Exception {
        System.out.println("Caso: existeCliente -> Debe devolver false si no existe");

        // El cliente de prueba no ha sido insertado aún
        boolean resultado = clientePrueba.existeCliente(clientePrueba);

        assertFalse(resultado, "Debería devolver false para un cliente que no está en la BD");
    }

    @Test
    public void testExisteCliente_Existe() throws Exception {
        System.out.println("Caso: existeCliente -> Debe devolver true si existe");

        // Forzamos la inserción manual en la BD primero
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

    // =========================================================================
    // TESTS PARA: altaCliente()
    // =========================================================================

    @Test
    public void testAltaCliente_Correcto() throws Exception {
        System.out.println("Caso: altaCliente -> Flujo correcto");

        // El método no debe lanzar ninguna excepción
        assertDoesNotThrow(() -> {
            clientePrueba.altaCliente(clientePrueba);
        });

        // Verificamos que realmente se guardó comprobando si existe
        assertTrue(clientePrueba.existeCliente(clientePrueba));
    }

    @Test
    public void testAltaCliente_Duplicado_LanzaExcepcion() throws Exception {
        System.out.println("Caso: altaCliente -> Registrar DNI duplicado lanza error");

        // Insertamos el cliente la primera vez
        clientePrueba.altaCliente(clientePrueba);

        // Intentar insertarlo una segunda vez debe fallar y lanzar Exception
        Exception exception = assertThrows(Exception.class, () -> {
            clientePrueba.altaCliente(clientePrueba);
        });

        // Comprobamos que el mensaje de error de la excepción contenga el texto esperado
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

    // =========================================================================
    // TESTS PARA: bajaCliente()
    // =========================================================================

    @Test
    public void testBajaCliente_Correcto() throws Exception {
        System.out.println("Caso: bajaCliente -> Flujo correcto");

        // Primero lo damos de alta para que exista
        clientePrueba.altaCliente(clientePrueba);

        // El método de borrar no debe lanzar excepciones
        assertDoesNotThrow(() -> {
            clientePrueba.bajaCliente(clientePrueba);
        });

        // Comprobamos que ya no existe en la base de datos
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