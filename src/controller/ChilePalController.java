package controller;

import model.Cliente;
import model.Producto;
import model.Pedido;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChilePalController {
    private static final String ARCHIVO_DATOS = "archivo_chilepal.bin";

    private List<Cliente> clientes=new ArrayList<>();
    private List<Producto> productos=new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();


    private int nextIdCliente=1;
    private int nextIdProducto=1;
    private int nextIdPedido = 1;

    public ChilePalController(){
        cargarDatosPersistentes();
    }

    private void cargarDatosPersistentes(){
        File archivo = new File(ARCHIVO_DATOS);
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                productos = (List<Producto>) ois.readObject();
                clientes = (List<Cliente>) ois.readObject();
                pedidos = (List<Pedido>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                inicializarDatosPorDefecto();
            }
        } else {
            inicializarDatosPorDefecto();
        }
    }

    private void guardarDatosPersistentes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DATOS))) {
            oos.writeObject(productos);
            oos.writeObject(clientes);
            oos.writeObject(pedidos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializarDatosPorDefecto() {
        productos = new ArrayList<>();
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();

        productos.add(new Producto(101, "Palta Ester(kg)", 3000, 50));
        productos.add(new Producto(102, "Palta Negra de la cruz(kg)", 2500, 20));
        productos.add(new Producto(103, "Palta California(kg)", 4500, 15));
        productos.add(new Producto(104, "Palta Hass(kg)", 3600, 100));

        guardarDatosPersistentes();
    }

    private int busquedaIdDisponible() {
        int id = 1;

        while (true) {
            boolean disponible = true;

            for (Producto p : productos) {
                if (p.getId() == id) {
                    disponible = false;
                    break;
                }
            }

            if (disponible) return id;

            id++;
        }
    }


    // ---------- CLIENTES ----------
    public Cliente registrarCliente(String nombre, String rut, String telefono, String direccion) {
        Cliente c=new Cliente(busquedaIdDisponible(),nombre,rut,telefono,direccion);
        clientes.add(c);
        return c;
    }

    public List<Cliente> obtenerClientes() {
        return clientes;
    }

    // ---------- PRODUCTOS ----------
    public Producto registrarProducto(String nombre, int precioNeto, int stock) {
        Producto p=new Producto(busquedaIdDisponible(),nombre,precioNeto,stock);
        productos.add(p);
        return p;
    }


    public List<Producto> obtenerProductos() {
        return productos;
    }

    // ---------- PEDIDOS ----------

    public Pedido registrarPedido(Cliente cliente, Producto producto, int cantidad) {
        if (producto.getStock() < cantidad) {
            return null;
        }
        producto.reducirStock(cantidad);

        Pedido pedido = new Pedido(busquedaIdDisponible(), cliente, producto, cantidad);
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> obtenerPedidos() {
        return pedidos;
    }
    public Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    public Producto buscarProductoPorId(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
