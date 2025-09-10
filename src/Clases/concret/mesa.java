package Clases.concret;

public class mesa {
    protected int numero;
    public String estado;
    protected String mesero;

    public mesa(int numero, String estado, String mesero) {
        this.numero = numero;
        this.estado = estado; // Por ejemplo  0: libre, 1: ocupada, 2: reservada
        this.mesero = mesero;

    }

    public int getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }

    public String getMesero() {
        return mesero;
    }

}
