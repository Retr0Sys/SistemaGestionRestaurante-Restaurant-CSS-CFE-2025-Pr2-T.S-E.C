package Clases.concret;

import Clases.abstractas.Producto;

public class Postre extends Producto
{

    public Postre(int id, String nombre, double precio, int estado)
    {
        super(id, nombre, precio, estado);
    }

    @Override
    public void calcularImpuesto()
    {
        this.precio = this.precio * 1.15; // 15%
    }

    public String getCategoria()
    {
        return "postre";
    }
}
