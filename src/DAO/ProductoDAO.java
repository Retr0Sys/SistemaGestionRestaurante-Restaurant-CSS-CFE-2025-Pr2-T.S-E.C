package DAO;

import Clases.abstractas.Producto;
import Clases.concret.Comida;
import Clases.concret.Bebida;
import Clases.concret.Postre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM CatalogoProducto";

    private static final String SQL_SELECT_DISPONIBLES =
            "SELECT * FROM CatalogoProducto WHERE estado = 1";

    private static final String SQL_UPDATE_PRODUCTO =
            "UPDATE CatalogoProducto SET precio = ?, estado = ? WHERE IdCatalogoProducto = ?";

    // Devuelve TODOS los productos
    public static List<Producto> listar() throws SQLException {
        return obtenerProductos(SQL_SELECT_ALL);
    }

    // Devuelve SOLO los productos disponibles
    public static List<Producto> listarDisponibles() throws SQLException {
        return obtenerProductos(SQL_SELECT_DISPONIBLES);
    }

    // Actualiza un producto existente
    public static void actualizarProducto(int idProducto, double nuevoPrecio, int estado) throws SQLException {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE_PRODUCTO)) {

            ps.setDouble(1, nuevoPrecio);
            ps.setInt(2, estado);
            ps.setInt(3, idProducto);

            ps.executeUpdate();
        }
    }

    // Crea un nuevo producto (estado siempre 1 = disponible)
    public static void crearProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO CatalogoProducto (nombre, precio, categoria, estado) VALUES (?, ?, ?, ?)";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());

            // Determinar la categoría según la clase concreta
            String categoria = producto.getClass().getSimpleName().toLowerCase(); // "comida", "bebida", "postre"
            ps.setString(3, categoria);

            ps.setInt(4, 1); // Estado = 1 (disponible) por defecto

            ps.executeUpdate();
        }
    }

    // Obtiene productos según la consulta SQL
    private static List<Producto> obtenerProductos(String sql) throws SQLException {
        List<Producto> productos = new ArrayList<>();

        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("IdCatalogoProducto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String categoria = rs.getString("categoria");
                int estado = rs.getInt("estado");

                Producto p = null;

                if ("comida".equalsIgnoreCase(categoria)) {
                    p = new Comida(id, nombre, precio, estado);
                } else if ("bebida".equalsIgnoreCase(categoria)) {
                    p = new Bebida(id, nombre, precio, estado);
                } else if ("postre".equalsIgnoreCase(categoria)) {
                    p = new Postre(id, nombre, precio, estado);
                }

                if (p != null) {
                    productos.add(p);
                }
            }
        }

        return productos;
    }
}
