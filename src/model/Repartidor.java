package model;
import java.io.Serializable;

public class Repartidor  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;

    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }


    public void entregarPedido(OrdenDespacho orden) {
        if (orden != null) {
            orden.marcarEntregada();
        }
    }

    @Override
    public String toString() {
        return "Repartidor{id=" + id + ", nombre='" + nombre + "'}";
    }
}
