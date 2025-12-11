package controller;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChilePalController implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------- LISTAS PRINCIPALES -----------
    private List<Cliente> clientes = new ArrayList<>();
    private List<Producto> productos = new ArrayList<>();
    private List<Pedido> pedidos = new ArrayList<>();
    private List<OrdenDespacho> ordenesDespacho = new ArrayList<>();
    private List<Pago> pagos = new ArrayList<>();
    private List<Repartidor> repartidores = new ArrayList<>();

    // ----------- CONTADORES -----------
    private int nextIdCliente = 1;
    private int nextIdProducto = 1;
    private int nextIdPedido = 1;
    private int nextIdOrdenDespacho = 1;
    private int nextIdPago = 1;
    private int nextIdRepartidor = 1;

    // ----------- CONSTRUCTOR -----------
    public ChilePalController() {
        repartidores.add(new Repartidor(nextIdRepartidor++, "Repartidor 1"));
    }

    // =======================================================
    //                PERSISTENCIA BINARIA
    // =======================================================

    public static ChilePalController cargarDesdeArchivo(String ruta) {
        File archivo = new File(ruta);

        if (!archivo.exists()) {
            ChilePalController c = new ChilePalController();
            c.cargarDatosIniciales();
            return c;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            System.out.println("Datos cargados desde archivo.");
            return (ChilePalController) ois.readObject();
        } catch (Exception e) {
            System.out.println("ERROR al cargar archivo. Se iniciará vacío.");
            return new ChilePalController();
        }
    }

    public void guardarEnArchivo(String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(this);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("ERROR al guardar datos.");
        }
    }

    // =======================================================
    //         DATOS INICIALES
    // =======================================================

    private void cargarDatosIniciales() {

        registrarProducto("Palta Ester (kg)", 3000, 50);
        registrarProducto("Palta Negra de la Cruz (kg)", 2500, 20);
        registrarProducto("Palta California (kg)", 4500, 15);
        registrarProducto("Palta Hass (kg)", 3600, 100);

        System.out.println("Datos iniciales cargados correctamente.");
    }

    // =======================================================
    //                 CLIENTES
    // =======================================================

    public Cliente registrarCliente(String nombre, String rut, String telefono, String direccion) {
        Cliente c = new Cliente(nextIdCliente++, nombre, rut, telefono, direccion);
        clientes.add(c);
        return c;
    }

    public List<Cliente> obtenerClientes() {
        return clientes;
    }


    // =======================================================
    //                 PRODUCTOS
    // =======================================================

    public Producto registrarProducto(String nombre, int precioNeto, int stock) {
        Producto p = new Producto(nextIdProducto++, nombre, precioNeto, stock);
        productos.add(p);
        return p;
    }

    public List<Producto> obtenerProductos() {
        return productos;
    }


    // =======================================================
    //                 PEDIDOS
    // =======================================================

    public Pedido registrarPedido(Cliente cliente, Producto producto, int cantidad) {
        if (cantidad <= 0) return null;
        if (producto.getStock() < cantidad) return null;

        producto.reducirStock(cantidad);

        Pedido pedido = new Pedido(nextIdPedido++, cliente, producto, cantidad);
        pedidos.add(pedido);
        return pedido;
    }

    public List<Pedido> obtenerPedidos() {
        return pedidos;
    }

    private Pedido buscarPedidoPorId(int idPedido) {
        for (Pedido p : pedidos) {
            if (p.getId() == idPedido) return p;
        }
        return null;
    }

    // =======================================================
    //               ORDEN DE DESPACHO
    // =======================================================

    public OrdenDespacho generarOrdenDespacho(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) return null;

        if (pedido.getEstado() != EstadoPedido.REGISTRADO) return null;

        pedido.setEstado(EstadoPedido.DESPACHADO);

        OrdenDespacho orden = new OrdenDespacho(nextIdOrdenDespacho++, pedido);
        ordenesDespacho.add(orden);
        return orden;
    }

    public List<OrdenDespacho> obtenerOrdenesDespacho() {
        return ordenesDespacho;
    }

    public OrdenDespacho buscarOrdenPorId(int idOrden) {
        for (OrdenDespacho o : ordenesDespacho) {
            if (o.getId() == idOrden) return o;
        }
        return null;
    }

    // =======================================================
    //                 ENTREGA
    // =======================================================

    public List<Repartidor> obtenerRepartidores() {
        return repartidores;
    }

    public boolean registrarEntrega(int idOrden, int idRepartidor) {
        OrdenDespacho orden = buscarOrdenPorId(idOrden);
        if (orden == null) return false;

        Pedido pedido = orden.getPedido();

        if (pedido.getEstado() != EstadoPedido.DESPACHADO) return false;

        Repartidor rep = null;
        for (Repartidor r : repartidores) {
            if (r.getId() == idRepartidor)
                rep = r;
        }
        if (rep == null) return false;

        rep.entregarPedido(orden);
        pedido.setEstado(EstadoPedido.ENTREGADO);

        return true;
    }

    // =======================================================
    //                 PAGOS
    // =======================================================

    public Pago registrarPago(int idPedido) {
        Pedido pedido = buscarPedidoPorId(idPedido);
        if (pedido == null) return null;

        if (pedido.getEstado() == EstadoPedido.REGISTRADO ||
                pedido.getEstado() == EstadoPedido.DESPACHADO)
            return null;

        if (pedido.getEstado() == EstadoPedido.PAGADO)
            return null;

        Pago pago = new Pago(nextIdPago++, pedido, pedido.getTotal());
        pago.marcarComoPagado();
        pagos.add(pago);

        pedido.setEstado(EstadoPedido.PAGADO);

        return pago;
    }

    public List<Pago> obtenerPagos() {
        return pagos;
    }
}
