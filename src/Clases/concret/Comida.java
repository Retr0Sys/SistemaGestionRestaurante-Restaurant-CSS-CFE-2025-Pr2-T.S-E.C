package Clases.concret;

public class Comida {
    protected String nombre;
    protected double precio;
    protected String categoria;

    public Comida(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = "Clases.concret.Comida";
    }
}
