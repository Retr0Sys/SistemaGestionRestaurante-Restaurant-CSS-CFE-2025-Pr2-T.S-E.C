package DAO;

import Clases.concret.Cuenta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO
{
    private static final String SQL_INSERT =
            "INSERT INTO cuenta (idMesa, estado) VALUES (?, ?)";

    private static final String SQL_SELECT_ABIERTA_COUNT =
            "SELECT COUNT(*) FROM cuenta WHERE idMesa = ? AND estado = 1";

    private static final String SQL_SELECT_ID_ABIERTA =
            "SELECT idCuenta FROM cuenta WHERE idMesa = ? AND estado = 1 LIMIT 1";

    private static final String SQL_CERRAR_CUENTA =
            "UPDATE cuenta SET estado = 0, fechaCierre = NOW() WHERE idMesa = ? AND estado = 1";

    private static final String SQL_SELECT_ABIERTA =
            "SELECT * FROM cuenta WHERE idMesa = ? AND estado = 1 LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM cuenta ORDER BY fechaApertura DESC";

    // Inserta una nueva cuenta. Si quieres recuperar el id generado, se setea en el objeto Cuenta.
    public void insertar(Cuenta c) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS))
        {
            ps.setInt(1, c.getIdMesa());
            ps.setInt(2, c.getEstado()); // normalmente 1 = abierta
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys())
            {
                if (keys.next())
                {
                    c.setIdCuenta(keys.getInt(1));
                }
            }
        }
    }

    // Devuelve true si la mesa tiene una cuenta con estado = 1 (abierta)
    public boolean tieneCuentaAbierta(int idMesa) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_ABIERTA_COUNT))
        {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Devuelve el id de la cuenta abierta para una mesa, o -1 si no existe
    public int obtenerIdCuentaAbierta(int idMesa) throws SQLException
    {
        try (Connection cn = ConexionDB.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_ID_ABIERTA))
        {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return rs.getInt("idCuenta");
                }
            }
        }
        return -1;
    }

    // Cierra la cuenta abierta de la mesa (pone estado = 0 y fechaCierre = NOW())
    public void cerrarCuenta(int idMesa) throws SQLException {
        String sql = "UPDATE cuenta SET estado = 0, fechaCierre = NOW() WHERE idMesa = ? AND estado = 1";
        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            ps.executeUpdate();
        }
    }


    // Lista todas las cuentas (historial)
    public List<Cuenta> listar() throws SQLException
    {
        List<Cuenta> lista = new ArrayList<>();
        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL))
        {
            while (rs.next())
            {
                Cuenta c = new Cuenta(
                        rs.getInt("idCuenta"),
                        rs.getInt("idMesa"),
                        rs.getTimestamp("fechaApertura"),
                        rs.getTimestamp("fechaCierre"),
                        rs.getInt("estado")
                );
                lista.add(c);
            }
        }
        return lista;
    }
}
