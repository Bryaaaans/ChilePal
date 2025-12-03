package model;

public class Producto {

    private int id;
    private String nombre;
    private int precioNeto;
    private int stock;

    public Producto(int id, String nombre, int precioNeto, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precioNeto = precioNeto;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrecioNeto() {
        return precioNeto;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "Producto{id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precioNeto=" + precioNeto +
                ", stock=" + stock +
                '}';
    }
}
