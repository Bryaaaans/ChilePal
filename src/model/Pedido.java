package model;
import java.io.Serializable;

public class Pedido  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Cliente cliente;
    private Producto producto;
    private int cantidad;
    private int total;
    private EstadoPedido estado;

    public Pedido(int id, Cliente cliente, Producto producto, int cantidad) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = producto.getPrecioNeto() * cantidad;
        this.estado = EstadoPedido.REGISTRADO; // estado inicial
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public int getTotal() { return total; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente.getNombre() +
                ", producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                ", total=" + total +
                ", estado=" + estado +
                '}';
    }
}
