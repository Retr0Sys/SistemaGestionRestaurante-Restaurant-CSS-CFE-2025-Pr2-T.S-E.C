package Clases.concret;

import Interfaces.CategoriaComida;

public class Bebida implements CategoriaComida {
    protected String nombre;

    public Bebida() {
        this.nombre = "Bebida";
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
