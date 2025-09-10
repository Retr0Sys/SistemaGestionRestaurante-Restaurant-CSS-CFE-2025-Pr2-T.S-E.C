package Clases.concret;

public class Pedido {
    String nombre;
    double precio;
    String categoria;
    int cantidad;

    public Pedido(String nombre, double precio, String categoria, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }
    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }
    public int getCantidad() {
        return cantidad;
    }

}
