package model;

public class Cliente {
    private int id;
    private String nombre;
    private String rut;
    private String telefono;
    private String direccion;

    public Cliente(int id, String nombre, String rut, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRut() {
        return rut;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre='" + nombre + "', rut='" + rut + "'}";
    }
}
