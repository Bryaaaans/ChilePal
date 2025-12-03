package controller;

import model.Cliente;
import model.Producto;
import model.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ChilePalController {

    private List<Cliente> clientes=new ArrayList<>();
    private List<Producto> productos=new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();


    private int nextIdCliente=1;
    private int nextIdProducto=1;
    private int nextIdPedido = 1;

    public ChilePalController(){
    }

    // ---------- CLIENTES ----------
    public Cliente registrarCliente(String nombre, String rut, String telefono, String direccion) {
        Cliente c=new Cliente(nextIdCliente++,nombre,rut,telefono,direccion);
        clientes.add(c);
        return c;
    }

    public List<Cliente> obtenerClientes() {
        return clientes;
    }

    // ---------- PRODUCTOS ----------
    public Producto registrarProducto(String nombre, int precioNeto, int stock) {
        Producto p=new Producto(nextIdProducto++,nombre,precioNeto,stock);
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

        Pedido pedido = new Pedido(nextIdPedido++, cliente, producto, cantidad);
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
