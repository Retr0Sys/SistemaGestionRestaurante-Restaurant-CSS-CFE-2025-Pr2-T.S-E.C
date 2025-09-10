package Clases.concret;

import Interfaces.CategoriaComida;

public class Postre implements CategoriaComida {
    protected String nombre;

    public Postre() {
        this.nombre = "Postre";
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}

