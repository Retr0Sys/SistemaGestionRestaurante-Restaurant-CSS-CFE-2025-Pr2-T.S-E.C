package Clases.concret;

import Clases.abstractas.Producto;

public class Comida extends Producto {

    public Comida(int id, String nombre, double precio, int estado) {
        super(id, nombre, precio, estado);
    }

    @Override
    public void calcularImpuesto() {
        this.precio = this.precio * 1.10; // 10%
    }

    public String getCategoria() {
        return "comida";
    }
}
