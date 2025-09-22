package Clases.concret;

import Clases.abstractas.Producto;

public class Bebida extends Producto
{

    public Bebida(int id, String nombre, double precio, int estado)
    {
        super(id, nombre, precio, estado);
    }

    @Override
    public void calcularImpuesto()
    {
        this.precio = this.precio * 1.22; // 22%
    }

    public String getCategoria()
    {
        return "bebida";
    }
}
