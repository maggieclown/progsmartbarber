package com.dam.vista;

import com.dam.modelo.*;

package com.dam.vista;

import com.dam.modelo.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        int op;
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
        op = sc.nextInt();
        System.out.println();
        return op;
    }

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
            op = mostrarMenu();
            try {
                switch (op) {
                    case 1: //...Alta de cliente...
                    {
                        Cliente cliente = new Cliente();
                        System.out.print("DNI: "); cliente.setDni(sc.next());
                        System.out.print("Nombre: "); cliente.setNombre(sc.next());
                        System.out.print("Apellidos: "); cliente.setApellidos(sc.next());
                        System.out.print("Teléfono: "); cliente.setTelefono(sc.next());
                        System.out.print("Email: "); cliente.setEmail(sc.next());

                        cliente.altaCliente(cliente);
                        System.out.println("Cliente dado de alta correctamente.");
                        break;
                    }
                    case 2: //...Baja de cliente...
                    {
                        Cliente cliente = new Cliente();
                        System.out.print("Introduce el DNI del cliente a borrar: ");
                        cliente.setDni(sc.next());

                        cliente.bajaCliente(cliente);
                        System.out.println("Cliente eliminado correctamente.");
                        break;
                    }
                    case 3: //...Alta de empleado...
                    {
                        Empleado empleado = new Empleado();
                        System.out.print("DNI: "); empleado.setDni(sc.next());
                        System.out.print("Nombre: "); empleado.setNombre(sc.next());
                        System.out.print("Apellidos: "); empleado.setApellidos(sc.next());
                        System.out.print("Teléfono: "); empleado.setTelefono(sc.next());

                        empleado.altaEmpleado(empleado);
                        System.out.println("Empleado dado de alta correctamente.");
                        break;
                    }
                    case 4: //...Baja de empleado...
                    {
                        Empleado empleado = new Empleado();
                        System.out.print("Introduce el DNI del empleado a borrar: ");
                        empleado.setDni(sc.next());

                        empleado.bajaEmpleado(empleado);
                        System.out.println("Empleado eliminado correctamente.");
                        break;
                    }
                    case 5: //...Alta de servicio...
                    {
                        Servicio servicio = new Servicio();
                        System.out.print("ID Servicio (Numérico): "); servicio.setId(sc.nextInt());
                        System.out.print("Nombre: "); sc.nextLine();
                        servicio.setNombre(sc.nextLine());
                        System.out.print("Descripción: "); servicio.setDescripcion(sc.nextLine());
                        System.out.print("Precio: "); servicio.setPrecio(sc.nextDouble());

                        System.out.println("Selecciona Tipo (1:Corte, 2:Barba, 3:Tinte, 4:Mechas): ");
                        int tipoOp = sc.nextInt();
                        switch(tipoOp) {
                            case 1: servicio.setTipos(Servicio.TIPOS.Corte); break;
                            case 2: servicio.setTipos(Servicio.TIPOS.Barba); break;
                            case 3: servicio.setTipos(Servicio.TIPOS.Tinte); break;
                            case 4: servicio.setTipos(Servicio.TIPOS.Mechas); break;
                            default: System.out.println("Tipo no válido, se asignará vacío.");
                        }

                        servicio.altaServicio(servicio);
                        System.out.println("Servicio dado de alta correctamente.");
                        break;
                    }
                    case 6: //...Baja de servicio...
                    {
                        Servicio servicio = new Servicio();
                        System.out.print("Introduce el ID del servicio a borrar: ");
                        servicio.setId(sc.nextInt());

                        servicio.bajaServicio(servicio);
                        System.out.println("Servicio eliminado correctamente.");
                        break;
                    }
                    case 7: //...Alta de producto...
                    {
                        Producto producto = new Producto();
                        System.out.print("ID Producto (Numérico): "); producto.setId(sc.nextInt());
                        System.out.print("Nombre: "); sc.nextLine();
                        producto.setNombre(sc.nextLine());
                        System.out.print("Precio: "); producto.setPrecio(sc.nextDouble());
                        System.out.print("Stock inicial: "); producto.setStock(sc.nextDouble());

                        producto.altaProducto(producto);
                        System.out.println("Producto dado de alta correctamente.");
                        break;
                    }
                    case 8: //...Baja de producto...
                    {
                        Producto producto = new Producto();
                        System.out.print("Introduce el ID del producto a borrar: ");
                        producto.setId(sc.nextInt());

                        producto.bajaProducto(producto);
                        System.out.println("Producto eliminado correctamente.");
                        break;
                    }
                    case 9: //...Alta factura...
                    {
                        Factura factura = new Factura();
                        System.out.print("ID Factura (Numérico): "); factura.setId(sc.nextInt());

                        // Asigna la fecha actual del sistema automáticamente
                        factura.setFecha(LocalDate.now());

                        System.out.print("DNI Cliente: "); factura.setDniCliente(sc.next());
                        System.out.print("DNI Empleado: "); factura.setDniEmpleado(sc.next());
                        System.out.print("ID Servicio asociado: "); factura.setIdServicio(sc.nextInt());
                        System.out.print("ID Producto asociado: "); factura.setIdProducto(sc.nextInt());

                        factura.altaFactura(factura);
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
                            System.out.println(p.getId() + " - " + p.getNombre() + " - Precio: " + p.getPrecio() + "€ - Stock: " + p.getStock());
                        }
                        break;
                    }
                    case 14: //...Listado de compras...
                    {
                        facturas.clear();
                        Factura.listadoFacturas(facturas);
                        System.out.println("\n--- LISTADO DE FACTURAS / COMPRAS ---");
                        for (Factura f : facturas) {
                            System.out.println("Factura Nº: " + f.getId() + " | Fecha: " + f.getFecha() +
                                    " | Cliente: " + f.getDniCliente() + " | Empleado: " + f.getDniEmpleado() +
                                    " | Serv ID: " + f.getIdServicio() + " | Prod ID: " + f.getIdProducto());
                        }
                        break;
                    }
                    case 15: //...Guardar información en base de datos...
                    {
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

        try {
            if (ConexionBD.getConn() != null && !ConexionBD.getConn().isClosed()) {
                ConexionBD.getConn().close();
            }
        } catch (Exception ignored) {}
    }
}
