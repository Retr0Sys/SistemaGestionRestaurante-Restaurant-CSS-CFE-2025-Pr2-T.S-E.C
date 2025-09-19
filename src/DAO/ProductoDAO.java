package DAO;

import Clases.abstractas.Producto;
import Clases.concret.Comida;
import Clases.concret.Bebida;
import Clases.concret.Postre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO
{
    private static final String SQL_SELECT_ALL =
            "SELECT * FROM CatalogoProducto";

    public List<Producto> listar() throws SQLException
    {
        List<Producto> productos = new ArrayList<>();

        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL))
        {
            while (rs.next())
            {
                int id = rs.getInt("IdCatalogoProducto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String categoria = rs.getString("categoria");

                Producto p = null;

                if ("comida".equalsIgnoreCase(categoria))
                {
                    p = new Comida(id, nombre, precio);
                }
                else if ("bebida".equalsIgnoreCase(categoria))
                {
                    p = new Bebida(id, nombre, precio);
                }
                else if ("postre".equalsIgnoreCase(categoria))
                {
                    p = new Postre(id, nombre, precio);
                }

                if (p != null)
                {
                    productos.add(p);
                }
            }
        }

        return productos;
    }
}
