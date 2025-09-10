package Interfaces;

import Exepciones.MesaNoDisponibleException;
import Exepciones.StockInsuficienteException;

public class PedidoServiceImpl implements PedidoService {

    @Override
    public String crearPedido(int mesaID) throws MesaNoDisponibleException {
        return "";
    }

    @Override
    public void agregarItem(String pedidoID, String productoId, int cantidad) throws StockInsuficienteException {

    }

    @Override
    public void quitarItem(String pedidoId, String productoId) {

    }

    @Override
    public double calcularTotal(String pedidoId) {
        return 0;
    }

    @Override
    public void cerrarPedido(String pedidoId) throws IllegalStateException {

    }
}
