package com.dam.vista;

import com.dam.modelo.*;
import java.util.List;

public class Auxiliar {

    // PRECARGA DE DATOS

    public static void preCarga(List<Cliente> clientes, List<Empleado> empleados, List<Producto> productos, List<Servicio> servicios) {

        System.out.println("Iniciando precarga de datos en la Base de Datos...");

        // --- PRECARGA DE CLIENTES ---
        try {
            Cliente cliente = new Cliente();
            cliente.setDni("97602871W");
            cliente.setNombre("Eduardo");
            cliente.setApellidos("Cano");
            cliente.setTelefono("612345678");
            cliente.setEmail("edu@gmail.com");

            cliente.altaCliente(cliente);
        } catch (Exception e) {
            System.out.println("Error al registrar el cliente 1: " + e.getMessage());
        }

        try {
            Cliente cliente2 = new Cliente();
            cliente2.setDni("85109842K");
            cliente2.setNombre("Sergio");
            cliente2.setApellidos("Valera");
            cliente2.setTelefono("623456789");
            cliente2.setEmail("sergio@gmail.com");

            cliente2.altaCliente(cliente2);
        } catch (Exception e) {
            System.out.println("Error al registrar el cliente 2: " + e.getMessage());
        }

        // --- PRECARGA DE EMPLEADOS ---
        try {
            Empleado empleado1 = new Empleado();
            empleado1.setDni("25170503Q");
            empleado1.setNombre("Jose");
            empleado1.setApellidos("Lapida");
            empleado1.setTelefono("624994001");

            empleado1.altaEmpleado(empleado1);
        } catch (Exception e) {
            System.out.println("Error al registrar el empleado 1: " + e.getMessage());
        }

        try {
            Empleado empleado2 = new Empleado();
            empleado2.setDni("30361374V");
            empleado2.setNombre("Vick");
            empleado2.setApellidos("Barcelo");
            empleado2.setTelefono("659118813");

            empleado2.altaEmpleado(empleado2);
        } catch (Exception e) {
            System.out.println("Error al registrar el empleado 2: " + e.getMessage());
        }

        try {
            Empleado empleado3 = new Empleado();
            empleado3.setDni("58247711G");
            empleado3.setNombre("Leo");
            empleado3.setApellidos("Vera");
            empleado3.setTelefono("649126813");

            empleado3.altaEmpleado(empleado3);
        } catch (Exception e) {
            System.out.println("Error al registrar el empleado 3: " + e.getMessage());
        }

        // --- PRECARGA DE PRODUCTOS ---
        try {
            Producto producto1 = new Producto();
            producto1.setId(1);
            producto1.setNombre("Crema para barba");
            producto1.setPrecio(2.13);
            producto1.setStock(10);

            producto1.altaProducto(producto1);
        } catch (Exception e) {
            System.out.println("Error al registrar el producto 1: " + e.getMessage());
        }

        try {
            Producto producto2 = new Producto();
            producto2.setId(2);
            producto2.setNombre("Champú");
            producto2.setPrecio(5.10);
            producto2.setStock(10);

            producto2.altaProducto(producto2);
        } catch (Exception e) {
            System.out.println("Error al registrar el producto 2: " + e.getMessage());
        }

        // --- PRECARGA DE SERVICIOS ---
        try {
            Servicio servicio1 = new Servicio();
            servicio1.setId(1);
            servicio1.setNombre("Corte");
            servicio1.setDescripcion("Corte de calidad");
            servicio1.setPrecio(10.0);

            servicio1.altaServicio(servicio1);
        } catch (Exception e) {
            System.out.println("Error al registrar el servicio 1: " + e.getMessage());
        }

        try {
            Servicio servicio2 = new Servicio();
            servicio2.setId(2);
            servicio2.setNombre("Afeitado");
            servicio2.setDescripcion("Afeitado de gran calidad");
            servicio2.setPrecio(12.0);

            servicio2.altaServicio(servicio2);
        } catch (Exception e) {
            System.out.println("Error al registrar el servicio 2: " + e.getMessage());
        }

        try {
            Servicio servicio3 = new Servicio();
            servicio3.setId(3);
            servicio3.setNombre("Afeitado + corte");
            servicio3.setDescripcion("Opción para ahorrar un poco mas pero con las misma calidad");
            servicio3.setPrecio(16.0);

            servicio3.altaServicio(servicio3);
        } catch (Exception e) {
            System.out.println("Error al registrar el servicio 3: " + e.getMessage());
        }

        System.out.println("Precarga finalizada.");
    }

    // MÉTODOS DE VALIDACIÓN (ABAJO)

    /**
     * Valida el DNI.
     */
    public static void validarDni(String dni) throws Exception {
        if (dni == null || dni.trim().isEmpty()) {
            throw new Exception("El DNI es un campo obligatorio y no puede estar vacío.");
        }

        String texto = dni.trim();

        // 1. Comprobar longitud exacta
        if (texto.length() != 9) {
            throw new Exception("El formato del DNI '" + dni + "' no es válido (Debe tener 8 números y 1 letra).");
        }

        // 2. Comprobar que los primeros 8 caracteres sean dígitos
        for (int i = 0; i < 8; i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                throw new Exception("El formato del DNI no es válido (Los 8 primeros caracteres deben ser números).");
            }
        }

        // 3. Comprobar que el último carácter sea una letra
        char ultimaLetra = texto.charAt(8);
        if (!Character.isLetter(ultimaLetra)) {
            throw new Exception("El formato del DNI no es válido (El último carácter debe ser una letra).");
        }
    }

    /**
     * Valida que el teléfono tenga exactamente 9 dígitos numéricos.
     */
    public static void validarTelefono(String telefono) throws Exception {
        if (telefono != null && !telefono.trim().isEmpty()) {
            String texto = telefono.trim();

            // 1. Comprobar longitud exacta
            if (texto.length() != 9) {
                throw new Exception("El teléfono '" + telefono + "' debe tener exactamente 9 dígitos numéricos.");
            }

            // 2. Comprobar que todos los caracteres sean números
            for (int i = 0; i < texto.length(); i++) {
                if (!Character.isDigit(texto.charAt(i))) {
                    throw new Exception("El teléfono '" + telefono + "' solo puede contener caracteres numéricos.");
                }
            }
        }
    }

    /**
     * Valida el formato del correo (debe contener '@' y un '.' después).
     */
    public static void validarEmail(String email) throws Exception {
        if (email != null && !email.trim().isEmpty()) {
            String texto = email.trim();

            // Buscar la posición de la arroba y del punto
            int posicionArroba = texto.indexOf('@');
            int posicionUltimoPunto = texto.lastIndexOf('.');

            // Un email básico válido debe tener arroba, no estar al principio,
            // tener un punto después de la arroba y que el punto no sea el último carácter.
            if (posicionArroba <= 0 || posicionUltimoPunto <= posicionArroba + 1 || posicionUltimoPunto == texto.length() - 1) {
                throw new Exception("El formato del correo electrónico '" + email + "' no es válido.");
            }
        }
    }

    /**
     * Valida si un texto obligatorio está vacío o es nulo.
     */
    public static void validarTextoObligatorio(String texto, String nombreCampo) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("El campo '" + nombreCampo + "' es obligatorio y no puede estar vacío.");
        }
    }

    /**
     * Valida que una ID numérica sea mayor que cero.
     */
    public static void validarId(int id, String nombreCampo) throws Exception {
        if (id <= 0) {
            throw new Exception("El campo '" + nombreCampo + "' debe ser un número entero mayor que 0.");
        }
    }

    /**
     * Valida que un precio no sea un valor negativo.
     */
    public static void validarPrecio(double precio) throws Exception {
        if (precio < 0) {
            throw new Exception("El precio del servicio/producto no puede ser un valor negativo.");
        }
    }

    /**
     * Valida que el stock disponible no sea negativo.
     */
    public static void validarStock(double stock) throws Exception {
        if (stock < 0) {
            throw new Exception("El stock disponible no puede ser un valor negativo.");
        }
    }

    /**
     * Valida el tipo de servicio. Si viene vacío o nulo (por descarte del default),
     * no frena la ejecución, permitiendo guardar el servicio con estado en blanco.
     */
    public static void validarTipoServicio(Servicio.TIPOS tipo) throws Exception {
        if (tipo == null) {
            System.out.println("Aviso: Tipo de servicio no especificado o inválido. Se guardará como vacío.");
        }
    }

    /**
     * Valida que la fecha de la factura exista y no sea del futuro.
     */
    public static void validarFechaFactura(java.time.LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha de la factura es un campo obligatorio.");
        }
        if (fecha.isAfter(java.time.LocalDate.now())) {
            throw new Exception("La fecha introducida no puede ser posterior al día de hoy.");
        }
    }
}