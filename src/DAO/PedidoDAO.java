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
        String sql = "INSERT INTO pedido (idCuenta, idProducto, cantidad) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);

            ps.executeUpdate();
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
    }
