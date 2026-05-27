package com.dam.vista;

import com.dam.modelo.*;

import java.util.List;

/**
 * Clase Auxiliar (Métodos de validación y precarga de datos)
 *
 * @author María Magdalena Cano
 * @author Sergio Torres Llamas
 * @author Juan de Dios Valero Rodríguez
 * @version 12.0
 * @since 27/05/2026
 */

public class Auxiliar {

    /**
     * Precarga de datos de ejemplo en las colecciones de la aplicación.
     *
     * @param clientes  Colección de clientes donde introducir nuevos clientes
     * @param empleados Colección de empleados donde introducir nuevos empleados
     * @param productos Colección de productos donde introducir nuevos productos
     * @param servicios Colección de servicios donde introducir nuevos servicios
     * @see vista.Main#main(String[])
     */

    public static void preCarga(List<Cliente> clientes, List<Empleado> empleados, List<Producto> productos, List<Servicio> servicios) {
        System.out.println("Iniciando precarga de datos...");

        try {
            Empleado e1 = new Empleado();
            e1.setDni("25170503Q");
            e1.setNombre("Jose");
            e1.setApellidos("Lapida");
            e1.setTelefono("624994001");
            e1.altaEmpleado(e1);
            empleados.add(e1);

            Empleado e2 = new Empleado();
            e2.setDni("30361374V");
            e2.setNombre("Vick");
            e2.setApellidos("Barcelo");
            e2.setTelefono("659118813");
            e2.altaEmpleado(e2);
            empleados.add(e2);
        } catch (Exception e) {
            System.out.println("Aviso precarga empleados: " + e.getMessage());
        }

        try {
            Cliente c1 = new Cliente();
            c1.setDni("97602871W");
            c1.setNombre("Eduardo");
            c1.setApellidos("Cano");
            c1.setTelefono("612345678");
            c1.setEmail("edu@gmail.com");
            c1.altaCliente(c1);
            clientes.add(c1);

            Cliente c2 = new Cliente();
            c2.setDni("85109842K");
            c2.setNombre("Sergio");
            c2.setApellidos("Valera");
            c2.setTelefono("623456789");
            c2.setEmail("sergio@gmail.com");
            c2.altaCliente(c2);
            clientes.add(c2);
        } catch (Exception e) {
            System.out.println("Aviso precarga clientes: " + e.getMessage());
        }

        try {
            Producto p1 = new Producto();
            p1.setId(1);
            p1.setNombre("Crema para barba");
            p1.setPrecio(2.13);
            p1.setStock(10);
            p1.setDniEmpleado("25170503Q");
            p1.altaProducto(p1);
            productos.add(p1);

            Producto p2 = new Producto();
            p2.setId(2);
            p2.setNombre("Champú");
            p2.setPrecio(5.10);
            p2.setStock(10);
            p2.setDniEmpleado("25170503Q");
            p2.altaProducto(p2);
            productos.add(p2);
        } catch (Exception e) {
            System.out.println("Aviso precarga productos: " + e.getMessage());
        }

        try {
            Servicio s1 = new Servicio();
            s1.setId(1);
            s1.setNombre("Corte");
            s1.setDescripcion("Corte de calidad");
            s1.setPrecio(10.0);
            s1.setTipos(Servicio.TIPOS.Corte);
            s1.altaServicio(s1);
            servicios.add(s1);

            Servicio s2 = new Servicio();
            s2.setId(2);
            s2.setNombre("Afeitado");
            s2.setDescripcion("Afeitado de gran calidad");
            s2.setPrecio(12.0);
            s2.setTipos(Servicio.TIPOS.Barba);
            s2.altaServicio(s2);
            servicios.add(s2);
        } catch (Exception e) {
            System.out.println("Aviso precarga servicios: " + e.getMessage());
        }

        System.out.println("Precarga finalizada.");
    }

    //VALIDACIONES

    /**
     * Validación de DNI.
     *
     * @param dni DNI del cliente o empleado a validar
     * @throws Exception Si el DNI está vacío o el formato no es de 8 números y 1 letra
     */

    public static void validarDni(String dni) throws Exception {
        if (dni == null || dni.trim().isEmpty()) {
            throw new Exception("El DNI es obligatorio.");
        }
        if (!dni.trim().matches("^[0-9]{8}[A-Za-z]$")) {
            throw new Exception("El formato del DNI '" + dni + "' no es válido (8 números y 1 letra).");
        }
    }

    /**
     * Validación de Teléfono.
     *
     * @param telefono Teléfono del cliente o empleado a validar
     * @throws Exception Si contiene un formato que no sea exactamente de 9 números
     */

    public static void validarTelefono(String telefono) throws Exception {
        if (telefono != null && !telefono.trim().isEmpty()) {
            if (!telefono.trim().matches("^[0-9]{9}$")) {
                throw new Exception("El teléfono debe tener exactamente 9 números.");
            }
        }
    }


    /**
     * Validación de Email.
     *
     * @param email Email del cliente a validar
     * @throws Exception Si el formato del correo electrónico no es válido
     */

    public static void validarEmail(String email) throws Exception {
        if (email != null && !email.trim().isEmpty()) {
            if (!email.trim().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new Exception("El formato del correo electrónico no es válido.");
            }
        }
    }


    /**
     * Valida que un campo de texto obligatorio.
     *
     * @param texto       Contenido del texto a evaluar
     * @param nombreCampo Nombre del campo para personalizar el mensaje de error
     * @throws Exception Si el texto es nulo o está vacío
     */

    public static void validarTextoObligatorio(String texto, String nombreCampo) throws Exception {
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("El campo '" + nombreCampo + "' no puede estar vacío.");
        }
    }

    /**
     * Validación de Id.
     *
     * @param id          Id de la factura, producto o servicio
     * @param nombreCampo Nombre del campo para personalizar el mensaje de error
     * @throws Exception Si el id es menor o igual a 0
     */

    public static void validarId(int id, String nombreCampo) throws Exception {
        if (id <= 0) {
            throw new Exception("El campo '" + nombreCampo + "' debe ser un entero mayor que 0.");
        }
    }

    /**
     * Validación de Precio.
     *
     * @param precio Precio asignado al producto o servicio
     * @throws Exception Si el precio es menor que 0
     */

    public static void validarPrecio(double precio) throws Exception {
        if (precio < 0) {
            throw new Exception("El precio no puede ser negativo.");
        }
    }

    /**
     * Validación de Stock.
     *
     * @param stock Unidades disponibles del producto
     * @throws Exception Si el stock es un valor negativo
     */

    public static void validarStock(int stock) throws Exception {
        if (stock < 0) {
            throw new Exception("El stock no puede ser un valor negativo.");
        }
    }

    /**
     * Validación del Tipo de Servicio.
     *
     * @param tipo Enumerado con el tipo de servicio
     * @throws Exception Puede ser lanzada en futuras implementaciones (mantenida por consistencia de firma)
     */

    public static void validarTipoServicio(Servicio.TIPOS tipo) throws Exception {
        if (tipo == null) {
            System.out.println("Aviso: Tipo de servicio no especificado.");
        }
    }

    /**
     * Validación de la Fecha de la Factura.
     *
     * @param fecha Fecha en la que se emite la factura
     * @throws Exception Si la fecha es nula o posterior al día actual
     */

    public static void validarFechaFactura(java.time.LocalDate fecha) throws Exception {
        if (fecha == null) {
            throw new Exception("La fecha de la factura es obligatoria.");
        }
        if (fecha.isAfter(java.time.LocalDate.now())) {
            throw new Exception("La fecha no puede ser posterior al día de hoy.");
        }
    }
}