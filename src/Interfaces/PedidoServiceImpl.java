package Interfaces;

import Clases.concret.Pedido;
import DAO.PedidoDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class PedidoServiceImpl implements IPedidoService {

    private final PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();

    @Override
    public List<Pedido> listarPendientes() throws SQLException {
        return pedidoDAO.listarPendientes();
    }

    @Override
    public void actualizarEstado(int idPedido, String nuevoEstado) throws SQLException {
        pedidoDAO.actualizarEstado(idPedido, nuevoEstado);
    }
}
