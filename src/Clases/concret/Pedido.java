package Clases.concret;

public class Pedido {
    protected Mesa mesa;
    protected LineaPedido pedido;
    public Pedido(Mesa mesa, int linea) {
        this.mesa = mesa;
        this.pedido = newLineaPedido(linea);
    }

    private LineaPedido newLineaPedido(int linea) {
        return new LineaPedido(linea);
    }
}
