package view;

import controller.ChilePalController;
import model.Cliente;
import model.Producto;

import java.util.List;
import java.util.Scanner;

public class ConsolaView {

    private ChilePalController controller;
    private Scanner scanner = new Scanner(System.in);

    public ConsolaView(ChilePalController controller) {
        this.controller = controller;
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> menuClientes();
                case 2 -> menuProductos();
                case 3 -> menuPedidos();
                case 4 -> System.out.println("Saliendo de ChilePal...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 4);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n--- ChilePal - Menu Principal ---");
        System.out.println("1. Clientes");
        System.out.println("2. Productos");
        System.out.println("3. Pedidos");
        System.out.println("4. Salir");
    }

    // CLIENTES

    private void menuClientes() {
        int opcion;
        do {
            System.out.println("\n--- Menu Clientes ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Volver");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarCliente();
                case 2 -> listarClientes();
                case 3 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 3);
    }

    private void registrarCliente() {
        System.out.println("\n-- Registrar Cliente --");

        System.out.print("Nombre: ");
        String nombre = scanner.next();

        System.out.print("RUT: ");
        String rut = scanner.next();

        System.out.print("Teléfono: ");
        String telefono = scanner.next();

        System.out.print("Direccion: ");
        String direccion = scanner.next();

        Cliente c = controller.registrarCliente(nombre, rut, telefono, direccion);
        System.out.println("Cliente creado: " + c);
    }

    private void listarClientes() {
        System.out.println("\n-- Lista de Clientes --");
        List<Cliente> clientes = controller.obtenerClientes();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    //  PRODUCTOS

    private void menuProductos() {
        int opcion;
        do {
            System.out.println("\n--- Menú Productos ---");
            System.out.println("1. Registrar producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Volver");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> listarProductos();
                case 3 -> System.out.println("Volviendo...");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 3);
    }

    private void registrarProducto() {
        System.out.println("\n-- Registrar Producto --");

        System.out.print("Nombre: ");
        String nombre = scanner.next();

        System.out.print("Precio neto: ");
        int precio = scanner.nextInt();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        Producto p = controller.registrarProducto(nombre, precio, stock);
        System.out.println("Producto creado: " + p);
    }

    private void listarProductos() {
        System.out.println("\n-- Lista de Productos --");
        List<Producto> productos = controller.obtenerProductos();

        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }

        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    // PEDIDOS.

    private void menuPedidos() {
        System.out.println("\n--- Menú Pedidos ---");
    }
}
