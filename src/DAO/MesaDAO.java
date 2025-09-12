package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.concret.Mesa;

public class MesaDAO
{

    private static final String SQL_INSERT ="INSERT INTO mesa (idMesa, capacidad, idEstadoMesa, idPersonal) VALUES (?,?,?,?)";

    private static final String SQL_SELECT_ALL ="SELECT * FROM mesa";

    private static final String SQL_UPDATE = "UPDATE mesa SET capacidad=?, idEstadoMesa=?, idPersonal=? WHERE idMesa=?";

    private static final String SQL_DELETE ="DELETE FROM mesa WHERE idMesa=?";

    public void insertar(Mesa m) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT))
        {
            ps.setInt(1, m.getIdMesa());
            ps.setInt(2, m.getCapacidad());
            ps.setInt(3, m.getIdEstadoMesa());
            ps.setInt(4, m.getIdPersonal());
            ps.executeUpdate();
        }
    }

    public List<Mesa> listar() throws SQLException
    {
        List<Mesa> mesas = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL)) {
            while (rs.next())
            {
                Mesa m = new Mesa(
                        rs.getInt("idMesa"),
                        rs.getInt("capacidad"),
                        rs.getInt("idEstadoMesa"),
                        rs.getInt("idPersonal")
                );
                mesas.add(m);
            }
        }
        return mesas;
    }

    public void actualizar(Mesa m) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_UPDATE))
        {
            ps.setInt(1, m.getCapacidad());
            ps.setInt(2, m.getIdEstadoMesa());
            ps.setInt(3, m.getIdPersonal());
            ps.setInt(4, m.getIdMesa());
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
}
