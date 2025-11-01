package Interfaces;

import Clases.concret.Pedido;
import java.sql.SQLException;
import java.util.List;

public interface IPedidoService {

    List<Pedido> listarPendientes() throws SQLException;

    void actualizarEstado(int idPedido, String nuevoEstado) throws SQLException;
}
