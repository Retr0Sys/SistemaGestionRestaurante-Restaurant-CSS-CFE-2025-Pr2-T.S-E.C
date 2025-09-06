package Clases.concret;

public class LineaPedido {
    protected String producto;
    protected int cantidad;
    protected double subTotal;
    protected int linea;

    public LineaPedido(String producto, int cantidad, double subTotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public LineaPedido(int linea) {
        this.linea = linea;
    }
}
