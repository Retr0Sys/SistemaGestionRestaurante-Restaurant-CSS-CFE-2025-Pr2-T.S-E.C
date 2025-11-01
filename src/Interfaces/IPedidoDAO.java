package Interfaces;

import Clases.concret.Pedido;
import java.sql.SQLException;
import java.util.List;

public interface IPedidoDAO {

    void agregarPedido(int idCuenta, int idProducto, int cantidad) throws SQLException;

    List<Pedido> listarPorCuenta(int idCuenta) throws SQLException;

    List<Pedido> listar() throws SQLException;

    double calcularTotalCuenta(int idCuenta) throws SQLException;

    List<Pedido> listarPorCuentaConNombre(int idCuenta) throws SQLException;

    List<Pedido> listarPendientes() throws SQLException; // Para la vista Cocina

    void actualizarEstado(int idPedido, String nuevoEstado) throws SQLException;
}
