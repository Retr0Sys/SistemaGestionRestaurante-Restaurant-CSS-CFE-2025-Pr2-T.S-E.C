package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.concret.Mesa;

public class MesaDAO
{
    private static final String SQL_INSERT =
            "INSERT INTO mesa (idMesa, capacidad, estado) VALUES (?,?,?)";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM mesa";

    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM mesa WHERE idMesa=?";

    private static final String SQL_UPDATE =
            "UPDATE mesa SET capacidad=?, estado=? WHERE idMesa=?";

    private static final String SQL_DELETE =
            "DELETE FROM mesa WHERE idMesa=?";

    public void insertar(Mesa m) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT))
        {
            ps.setInt(1, m.getIdMesa());
            ps.setInt(2, m.getCapacidad());
            ps.setString(3, m.getEstado());
            ps.executeUpdate();
        }
    }

    public List<Mesa> listar() throws SQLException
    {
        List<Mesa> mesas = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL))
        {
            while (rs.next())
            {
                Mesa m = new Mesa(
                        rs.getInt("idMesa"),
                        rs.getInt("capacidad"),
                        rs.getString("estado")
                );
                mesas.add(m);
            }
        }
        return mesas;
    }

    public Mesa buscarPorId(int idMesa) throws SQLException
    {
        Mesa mesa = null;
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_BY_ID))
        {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    mesa = new Mesa(
                            rs.getInt("idMesa"),
                            rs.getInt("capacidad"),
                            rs.getString("estado")
                    );
                }
            }
        }
        return mesa;
    }

    public void actualizar(Mesa m) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE))
        {
            ps.setInt(1, m.getCapacidad());
            ps.setString(2, m.getEstado());
            ps.setInt(3, m.getIdMesa());
            ps.executeUpdate();
        }
    }

    public void eliminar(int idMesa) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_DELETE))
        {
            ps.setInt(1, idMesa);
            ps.executeUpdate();
        }
    }
    public void asignarMesero(int idMesa, int idMesero) throws SQLException
    {
        String sql = "UPDATE mesa SET idMesero = ? WHERE idMesa = ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setInt(1, idMesero);
            ps.setInt(2, idMesa);
            ps.executeUpdate();
        }
    }

    public void desasignarMesero(int idMesa) throws SQLException
    {
        String sql = "UPDATE mesa SET idMesero = NULL WHERE idMesa = ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setInt(1, idMesa);
            ps.executeUpdate();
        }
    }
    public String obtenerNombreMesero(int idMesa) throws SQLException
    {
        String sql = "SELECT CONCAT(m.nombre, ' ', m.apellido) AS mesero " +
                "FROM mesa me " +
                "LEFT JOIN mesero m ON me.idMesero = m.idMesero " +
                "WHERE me.idMesa = ?";

        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql))
        {
            ps.setInt(1, idMesa);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return rs.getString("mesero");
        }
        return null;
    }

}
