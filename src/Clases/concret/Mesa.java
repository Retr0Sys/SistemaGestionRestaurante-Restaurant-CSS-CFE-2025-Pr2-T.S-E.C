package Clases.concret;

public class Mesa {
    protected int numero;
    protected int estado;
    protected String mesero;

    public Mesa(int numero, int estado, String mesero) {
        this.numero = numero;
        this.estado = estado; // Por ejemplo  0: libre, 1: ocupada, 2: reservada
        this.mesero = mesero;
    }
}
