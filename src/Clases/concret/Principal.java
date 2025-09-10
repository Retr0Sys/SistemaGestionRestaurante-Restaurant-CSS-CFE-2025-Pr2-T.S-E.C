package Clases.concret;

import Interfaces.CategoriaComida;

public class Principal implements CategoriaComida {
    protected String nombre;

    public Principal() {
        this.nombre = "Principal";
    }

    public String getNombre(){
        return nombre;
    }
}
