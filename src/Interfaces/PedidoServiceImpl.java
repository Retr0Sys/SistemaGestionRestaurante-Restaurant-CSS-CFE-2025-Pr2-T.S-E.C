package Interfaces;

import Exepciones.MesaNoDisponibleException;
import Exepciones.StockInsuficienteException;

public class PedidoServiceImpl implements PedidoService {

    @Override
    public String crearPedido(int mesaID) throws MesaNoDisponibleException {
        // Lógica para crear un pedido
        return "";
    }

    @Override
    public void agregarItem(String pedidoID, String productoId, int cantidad) throws StockInsuficienteException {
        // Lógica para agregar un ítem al pedido
    }

    @Override
    public void quitarItem(String pedidoId, String productoId) {
        // Lógica para quitar un ítem del pedido
    }

    @Override
    public double calcularTotal(String pedidoId) {
        // Lógica para calcular el total del pedido
        return 0;
    }


    @Override
    public void cerrarPedido(String pedidoId) throws IllegalStateException {
        // Lógica para cerrar el pedido

    }
}
