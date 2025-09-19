package Clases.concret;

import Clases.abstractas.Producto;

public class Postre extends Producto
{

    public Postre(int id, String nombre, double precio)
    {
        super(id, nombre, precio);
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
