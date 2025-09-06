package Interfaces;

import Exepciones.MesaNoDisponibleException;
import Exepciones.StockInsuficienteException;

public interface PedidoService  {
    String crearPedido(int mesaID) throws MesaNoDisponibleException;
    void agregarItem(String pedidoID, String productoId, int cantidad) throws StockInsuficienteException;
    void quitarItem(String pedidoId, String productoId);
    double calcularTotal(String pedidoId);
    void cerrarPedido(String pedidoId) throws IllegalStateException;
}
