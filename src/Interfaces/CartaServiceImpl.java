package Interfaces;

import Clases.abstractas.Producto;
import DAO.ProductoDAO;

import java.sql.SQLException;
import java.util.List;

public class CartaServiceImpl implements CartaService {

    @Override
    public List<Producto> listarTodos() throws SQLException {
        return ProductoDAO.listar();
    }

    @Override
    public List<Producto> listarDisponibles() throws SQLException {
        return ProductoDAO.listarDisponibles();
    }

    @Override
    public void actualizarProducto(int idProducto, double nuevoPrecio, int estado) throws SQLException {
        ProductoDAO.actualizarProducto(idProducto, nuevoPrecio, estado);
    }

    @Override
    public void crearProducto(Producto producto) throws SQLException {
        ProductoDAO.crearProducto(producto);
    }

    @Override
    public Producto buscarPorNombre(String nombre) throws SQLException {
        for (Producto p : ProductoDAO.listar()) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }
}
