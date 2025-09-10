package Clases.concret;

import Interfaces.CategoriaComida;

public class Comida {
    protected String nombre;
    protected double precio;
    protected CategoriaComida categoria;
    protected String disponibilidad;

    public Comida(String nombre, double precio, CategoriaComida categoria,String disponibilidad) {
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
    public CategoriaComida getCategoria() {
        return categoria;
    }
    public String getDisponibilidad() {
        return disponibilidad;
    }
}
