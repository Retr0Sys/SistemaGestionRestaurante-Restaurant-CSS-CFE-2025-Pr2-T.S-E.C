package Clases.concret;


public class Comida {
    protected String nombre;
    protected double precio;
    protected String categoria;
    protected String disponibilidad;

    public Comida(String nombre, double precio, String categoria,String disponibilidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.disponibilidad = disponibilidad;
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
    public String getDisponibilidad() {
        return disponibilidad;
    }
}
