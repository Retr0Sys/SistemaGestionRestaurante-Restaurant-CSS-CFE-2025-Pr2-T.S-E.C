package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.concret.Pedido;

public class PedidoDAO {

    // =====================================
    // Insertar un nuevo pedido
    // =====================================
    public static void agregarPedido(int idCuenta, int idProducto, int cantidad) throws SQLException {
        String sqlInsert = "INSERT INTO pedido (idCuenta, idProducto, cantidad) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE CatalogoProducto SET stock = stock - ? WHERE IdCatalogoProducto = ? AND stock >= ?";

        try (Connection con = ConexionDB.getConnection()) {
            try {
                con.setAutoCommit(false); // Inicia transacción

                // 1️⃣ Insertar pedido
                try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                    ps.setInt(1, idCuenta);
                    ps.setInt(2, idProducto);
                    ps.setInt(3, cantidad);
                    ps.executeUpdate();
                }

                // 2️⃣ Actualizar stock
                try (PreparedStatement ps2 = con.prepareStatement(sqlUpdateStock)) {
                    ps2.setInt(1, cantidad);
                    ps2.setInt(2, idProducto);
                    ps2.setInt(3, cantidad); // evita que quede negativo
                    int filas = ps2.executeUpdate();

                    if (filas == 0) {
                        con.rollback();
                        throw new SQLException("Stock insuficiente para este producto.");
                    }
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }


    // =====================================
    // Listar pedidos de una cuenta específica
    // Incluye datos del producto
    // =====================================
    public static List<Pedido> listarPorCuenta(int idCuenta) throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = """
            SELECT p.idPedido, p.idCuenta, p.idProducto, p.cantidad, p.fechaHora,
                   c.nombre AS nombreProducto, c.precio AS precioProducto
            FROM pedido p
            JOIN CatalogoProducto c ON p.idProducto = c.IdCatalogoProducto
            WHERE p.idCuenta = ?
            ORDER BY p.fechaHora DESC
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido(
                            rs.getInt("idPedido"),
                            rs.getInt("idCuenta"),
                            rs.getInt("idProducto"),
                            rs.getInt("cantidad"),
                            rs.getTimestamp("fechaHora"),
                            rs.getString("nombreProducto"),
                            rs.getDouble("precioProducto")
                    );
                    lista.add(pedido);
                }
            }
        }
        return lista;
    }

    // =====================================
    // Listar todos los pedidos (sin filtro)
    // =====================================
    public static List<Pedido> listar() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = """
            SELECT p.idPedido, p.idCuenta, p.idProducto, p.cantidad, p.fechaHora,
                   c.nombre AS nombreProducto, c.precio AS precioProducto
            FROM pedido p
            JOIN CatalogoProducto c ON p.idProducto = c.IdCatalogoProducto
            ORDER BY p.fechaHora DESC
        """;

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("idPedido"),
                        rs.getInt("idCuenta"),
                        rs.getInt("idProducto"),
                        rs.getInt("cantidad"),
                        rs.getTimestamp("fechaHora"),
                        rs.getString("nombreProducto"),
                        rs.getDouble("precioProducto")
                );
                lista.add(pedido);
            }
        }
        return lista;
    }

    // =====================================
    // Calcular el total de una cuenta
    // =====================================
    public static double calcularTotalCuenta(int idCuenta) throws SQLException {
        String sql = """
            SELECT SUM(c.precio * p.cantidad) AS total
            FROM pedido p
            JOIN CatalogoProducto c ON p.idProducto = c.IdCatalogoProducto
            WHERE p.idCuenta = ?
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCuenta);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }

    public List<Pedido> listarPorCuentaConNombre(int idCuenta) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.idPedido, p.idCuenta, p.idProducto, p.cantidad, p.fechaHora, " +
                "c.nombre, c.precio " +
                "FROM Pedido p " +
                "JOIN CatalogoProducto c ON p.idProducto = c.IdCatalogoProducto " +
                "WHERE p.idCuenta = ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("idPedido"),
                            rs.getInt("idCuenta"),
                            rs.getInt("idProducto"),
                            rs.getInt("cantidad"),
                            rs.getTimestamp("fechaHora"),
                            rs.getString("nombre"),
                            rs.getDouble("precio")
                    ));
                }
            }
        }
        return pedidos;
    }
    // === Nuevos métodos específicos para Cocina ===
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
