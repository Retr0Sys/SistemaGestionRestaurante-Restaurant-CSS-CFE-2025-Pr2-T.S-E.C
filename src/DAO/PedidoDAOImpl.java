package DAO;

import Clases.concret.Pedido;
import Interfaces.IPedidoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements IPedidoDAO {

    @Override
    public void agregarPedido(int idCuenta, int idProducto, int cantidad) throws SQLException {
        PedidoDAO.agregarPedido(idCuenta, idProducto, cantidad);
    }

    @Override
    public List<Pedido> listarPorCuenta(int idCuenta) throws SQLException {
        return PedidoDAO.listarPorCuenta(idCuenta);
    }

    @Override
    public List<Pedido> listar() throws SQLException {
        return PedidoDAO.listar();
    }

    @Override
    public double calcularTotalCuenta(int idCuenta) throws SQLException {
        return PedidoDAO.calcularTotalCuenta(idCuenta);
    }

    @Override
    public List<Pedido> listarPorCuentaConNombre(int idCuenta) throws SQLException {
        return new PedidoDAO().listarPorCuentaConNombre(idCuenta);
    }

    // === Nuevos métodos específicos para Cocina ===
    @Override
    public List<Pedido> listarPendientes() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();

        String sql = """
            SELECT p.idPedido, p.idCuenta, p.idProducto, p.cantidad, p.fechaHora,
                   cP.nombre AS nombreProducto, cP.precio AS precioProducto
            FROM pedido p
            JOIN cuenta c ON p.idCuenta = c.idCuenta
            JOIN mesa m ON c.idMesa = m.idMesa
            JOIN CatalogoProducto cP ON p.idProducto = cP.IdCatalogoProducto
            WHERE p.estado NOT IN ('Servido', 'Cancelado')
            ORDER BY p.fechaHora ASC
        """;

        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("idPedido"),
                        rs.getInt("idCuenta"),
                        rs.getInt("idProducto"),
                        rs.getInt("cantidad"),
                        rs.getTimestamp("fechaHora"),
                        rs.getString("nombreProducto"),
                        rs.getDouble("precioProducto")
                ));
            }
        }
        return pedidos;
    }

    @Override
    public void actualizarEstado(int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE pedido SET estado = ? WHERE idPedido = ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }
}
