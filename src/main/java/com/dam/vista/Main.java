package com.dam.vista;

import com.dam.modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Proyecto de gestión de una barbería
 * Smart-Barber
 * @author María Magdalena Cano
 * @author Sergio Torres Llamas
 * @author Juan de Dios Valero Rodríguez
 * @since 27/05/2026
 * @version 12.0
 */

public class Main {

    /**
     * Muestra un menú de opciones
     * @return Opción seleccionada
     */

    private static int mostrarMenu(Scanner sc) {
        System.out.println("MENÚ");
        System.out.println("----");
        System.out.println();
        System.out.println("01.- Alta de cliente.");
        System.out.println("02.- Baja de cliente.");
        System.out.println("03.- Alta de empleado.");
        System.out.println("04.- Baja de empleado.");
        System.out.println("05.- Alta de servicio.");
        System.out.println("06.- Baja de servicio.");
        System.out.println("07.- Alta de producto.");
        System.out.println("08.- Baja de producto.");
        System.out.println("09.- Alta factura.");
        System.out.println("10.- Listado de clientes.");
        System.out.println("11.- Listado de empleados.");
        System.out.println("12.- Listado de servicios.");
        System.out.println("13.- Listado de productos.");
        System.out.println("14.- Listado de compras.");
        System.out.println("15.- Guardar información en base de datos.");
        System.out.println("00.- Salir.");
        System.out.println();
        System.out.print("Opción? ");

        int op = sc.nextInt();
        sc.nextLine(); // Limpia el salto de línea del búfer
        System.out.println();
        return op;
    }

    /**
     * Método principal del proyecto
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int op;

        List<Cliente> clientes = new ArrayList<>();
        List<Empleado> empleados = new ArrayList<>();
        List<Factura> facturas = new ArrayList<>();
        List<Servicio> servicios = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();

        /* ABRIR CONEXIÓN BD **************************************************/
        try {
            System.out.println("Abriendo conexión BD...");
            ConexionBD.abrirConexion();
            System.out.println("Conexión abierta correctamente.");

            System.out.println("--------------------------------------------------");
            Auxiliar.preCarga(clientes, empleados, productos, servicios);
            System.out.println("--------------------------------------------------");

        } catch (Exception e) {
            System.out.println("Error!!\n" + e.getMessage());
        }
        /* ********************************************************************/

        do {
            System.out.println();
            op = mostrarMenu(sc);
            try {
                switch (op) {
                    case 1: //...Alta de cliente...
                    {
                        Cliente cliente = new Cliente();
                        System.out.print("DNI: ");
                        cliente.setDni(sc.nextLine());
                        System.out.print("Nombre: ");
                        cliente.setNombre(sc.nextLine());
                        System.out.print("Apellidos: ");
                        cliente.setApellidos(sc.nextLine());
                        System.out.print("Teléfono: ");
                        cliente.setTelefono(sc.nextLine());
                        System.out.print("Email: ");
                        cliente.setEmail(sc.nextLine());

                        cliente.altaCliente(cliente);
                        clientes.add(cliente);
                        System.out.println("Cliente dado de alta correctamente.");
                        break;
                    }
                    case 2: //...Baja de cliente...
                    {
                        Cliente cliente = new Cliente();
                        System.out.print("Introduce el DNI del cliente a borrar: ");
                        cliente.setDni(sc.nextLine());

                        cliente.bajaCliente(cliente);
                        clientes.removeIf(c -> c.getDni().equalsIgnoreCase(cliente.getDni()));
                        System.out.println("Cliente eliminado correctamente.");
                        break;
                    }
                    case 3: //...Alta de empleado...
                    {
                        Empleado empleado = new Empleado();
                        System.out.print("DNI: ");
                        empleado.setDni(sc.nextLine());
                        System.out.print("Nombre: ");
                        empleado.setNombre(sc.nextLine());
                        System.out.print("Apellidos: ");
                        empleado.setApellidos(sc.nextLine());
                        System.out.print("Teléfono: ");
                        empleado.setTelefono(sc.nextLine());

                        empleado.altaEmpleado(empleado);
                        empleados.add(empleado);
                        System.out.println("Empleado dado de alta correctamente.");
                        break;
                    }
                    case 4: //...Baja de empleado...
                    {
                        Empleado empleado = new Empleado();
                        System.out.print("Introduce el DNI del empleado a borrar: ");
                        empleado.setDni(sc.nextLine());

                        empleado.bajaEmpleado(empleado);
                        empleados.removeIf(e -> e.getDni().equalsIgnoreCase(empleado.getDni()));
                        System.out.println("Empleado eliminado correctamente.");
                        break;
                    }
                    case 5: //...Alta de servicio...
                    {
                        Servicio servicio = new Servicio();
                        System.out.print("ID Servicio (Numérico): ");
                        servicio.setId(sc.nextInt());
                        sc.nextLine();
                        System.out.print("Nombre: ");
                        servicio.setNombre(sc.nextLine());
                        System.out.print("Descripción: ");
                        servicio.setDescripcion(sc.nextLine());
                        System.out.print("Precio: ");
                        servicio.setPrecio(sc.nextDouble());

                        System.out.println("Selecciona Tipo (1:Corte, 2:Barba, 3:Tinte, 4:Mechas): ");
                        int tipoOp = sc.nextInt();
                        sc.nextLine();
                        switch (tipoOp) {
                            case 1:
                                servicio.setTipos(Servicio.TIPOS.Corte);
                                break;
                            case 2:
                                servicio.setTipos(Servicio.TIPOS.Barba);
                                break;
                            case 3:
                                servicio.setTipos(Servicio.TIPOS.Tinte);
                                break;
                            case 4:
                                servicio.setTipos(Servicio.TIPOS.Mechas);
                                break;
                            default:
                                System.out.println("Tipo no válido, se asignará vacío.");
                        }

                        servicio.altaServicio(servicio);
                        servicios.add(servicio);
                        System.out.println("Servicio dado de alta correctamente.");
                        break;
                    }
                    case 6: //...Baja de servicio...
                    {
                        Servicio servicio = new Servicio();
                        System.out.print("Introduce el ID del servicio a borrar: ");
                        servicio.setId(sc.nextInt());
                        sc.nextLine();

                        servicio.bajaServicio(servicio);
                        servicios.removeIf(s -> s.getId() == servicio.getId());
                        System.out.println("Servicio eliminado correctamente.");
                        break;
                    }
                    case 7: //...Alta de producto...
                    {
                        Producto producto = new Producto();
                        System.out.print("ID Producto (Numérico): ");
                        producto.setId(sc.nextInt());
                        sc.nextLine();
                        System.out.print("Nombre: ");
                        producto.setNombre(sc.nextLine());
                        System.out.print("Precio: ");
                        producto.setPrecio(sc.nextDouble());
                        System.out.print("Stock inicial: ");
                        producto.setStock(sc.nextInt());
                        sc.nextLine();
                        System.out.print("DNI de Empleado responsable: ");
                        producto.setDniEmpleado(sc.nextLine());

                        producto.altaProducto(producto);
                        productos.add(producto);
                        System.out.println("Producto dado de alta correctamente.");
                        break;
                    }
                    case 8: //...Baja de producto...
                    {
                        Producto producto = new Producto();
                        System.out.print("Introduce el ID del producto a borrar: ");
                        producto.setId(sc.nextInt());
                        sc.nextLine();

                        producto.bajaProducto(producto);
                        productos.removeIf(p -> p.getId() == producto.getId());
                        System.out.println("Producto eliminado correctamente.");
                        break;
                    }
                    case 9: //...Alta factura...
                    {
                        Factura factura = new Factura();
                        System.out.print("ID Factura (Numérico): ");
                        factura.setId(sc.nextInt());
                        sc.nextLine();

                        factura.setFecha(LocalDate.now());

                        System.out.print("DNI Cliente: ");
                        factura.setDniCliente(sc.nextLine());
                        System.out.print("DNI Empleado: ");
                        factura.setDniEmpleado(sc.nextLine());
                        System.out.print("ID Servicio asociado: ");
                        factura.setIdServicio(sc.nextInt());
                        System.out.print("ID Producto asociado: ");
                        factura.setIdProducto(sc.nextInt());
                        System.out.print("Total Factura (€): ");
                        factura.setTotal(sc.nextDouble());
                        sc.nextLine();

                        factura.altaFactura(factura);
                        facturas.add(factura);
                        System.out.println("Factura creada y registrada correctamente.");
                        break;
                    }
                    case 10: //...Listado de clientes...
                    {
                        clientes.clear();
                        Cliente.listadoClientes(clientes);
                        System.out.println("\n--- LISTADO DE CLIENTES ---");
                        for (Cliente c : clientes) {
                            System.out.println(c.getDni() + " - " + c.getNombre() + " " + c.getApellidos() + " [" + c.getEmail() + "]");
                        }
                        break;
                    }
                    case 11: //...Listado de empleados...
                    {
                        empleados.clear();
                        Empleado.listadoEmpleados(empleados);
                        System.out.println("\n--- LISTADO DE EMPLEADOS ---");
                        for (Empleado e : empleados) {
                            System.out.println(e.getDni() + " - " + e.getNombre() + " " + e.getApellidos());
                        }
                        break;
                    }
                    case 12: //...Listado de servicios...
                    {
                        servicios.clear();
                        Servicio.listadoServicios(servicios);
                        System.out.println("\n--- LISTADO DE SERVICIOS ---");
                        for (Servicio s : servicios) {
                            System.out.println(s.getId() + " - " + s.getNombre() + " (" + s.getTiposString() + ") - Precio: " + s.getPrecio() + "€");
                        }
                        break;
                    }
                    case 13: //...Listado de productos...
                    {
                        productos.clear();
                        Producto.listadoProductos(productos);
                        System.out.println("\n--- LISTADO DE PRODUCTOS ---");
                        for (Producto p : productos) {
                            System.out.println(p.getId() + " - " + p.getNombre() + " - Precio: " + p.getPrecio() + "€ - Stock: " + p.getStock() + " - Emp Resp: " + p.getDniEmpleado());
                        }
                        break;
                    }
                    case 14: //...Listado de compras...
                    {
                        facturas.clear();
                        Factura.listadoFacturas(facturas);
                        System.out.println("\n--- LISTADO DE FACTURAS ---");
                        for (Factura f : facturas) {
                            System.out.println("Factura Nº: " + f.getId() + " | Fecha: " + f.getFecha() + " | Total: " + f.getTotal() + "€" +
                                    " | Cliente: " + f.getDniCliente() + " | Empleado: " + f.getDniEmpleado() +
                                    " | Serv ID: " + f.getIdServicio() + " | Prod ID: " + f.getIdProducto());
                        }
                        break;
                    }
                    case 15: //...Guardar información en base de datos...
                    {
                        System.out.println("Información ya sincronizada automáticamente con la Base de Datos.");
                        break;
                    }
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("¡ERROR!: " + e.getMessage());
            }
        } while (op != 0);

        sc.close();

        try {
            ConexionBD.cerrarConexion();
        } catch (Exception ignored) {
        }
    }
}