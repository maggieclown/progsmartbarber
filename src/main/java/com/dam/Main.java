package com.dam.vista;

import com.dam.modelo.*;
import com.dam.vista.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void main() {

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
            System.out.println("00.- Salir.");
            System.out.println();
            System.out.print("Opción? ");
            op = sc.nextInt();
            System.out.println();
            return op;
        }

        static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int op;

            List<Cliente> clientes = new ArrayList<>();
            List<Empleado> empleados = new ArrayList<>();
            List<Factura> facturas = new ArrayList<>();
            List<Servicio> servicios = new ArrayList<>();
            List<Producto> productos = new ArrayList<>();

            do {
                System.out.println();
                op = mostrarMenu();
                switch (op) {
                    case 1: //...Alta de cliente...
                    {
                        Cliente cliente = new Cliente();

                        break;
                    }
                    case 2: //...Baja de cliente...
                    {
                        Cliente cliente = new Cliente();

                        break;
                    }
                    case 3: //...Alta de empleado...
                    {
                        Empleado empleado = new Empleado();

                        break;
                    }
                    case 4: //...Baja de empleado...
                    {

                        Empleado empleado = new Empleado();

                        break;
                    }
                    case 5: //...Alta de servicio...
                    {
                        Servicio servicio = new Servicio();

                        break;
                    }
                    case 6: //...Baja de servicio...
                    {
                        Servicio servicio = new Servicio();

                        break;
                    }
                    case 7: //...Alta de producto...
                    {
                        Producto producto = new Producto();

                        break;
                    }
                    case 8: //...Baja de producto...
                    {
                        Producto producto = new Producto();

                        break;
                    }
                    case 9: //...Alta factura...
                    {
                        Factura factura = new Factura();

                        break;
                    }
                    case 10: //...Listado de clientes...
                    {
                        break;
                    }
                    case 11: //...Listado de empleados...
                    {
                        break;
                    }
                    case 12: //...Listado de servicios...
                    {
                        break;
                    }
                    case 13: //...Listado de producto...
                    {
                        break;
                    }
                    case 14: //...Listado de factura...
                    {
                        break;
                    }
                    case 0: //...Salir...
                    {
                        break;
                    }
                    default: {
                        System.out.println("Opción incorrecta!!");
                        System.out.println();
                    }
                }
            } while (op != 0);
            System.out.println();
        }
    }