package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Clases.concret.Cuenta;

public class CuentaDAO
{
    // Crear una nueva cuenta con estado abierto (1)
    public static void abrirCuenta(int idMesa) throws SQLException
    {
        String sql = "INSERT INTO cuenta (idMesa, fechaApertura, estado) VALUES (?, NOW(), 1)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, idMesa);
            stmt.executeUpdate();
        }
    }

    // Cerrar cuenta (estado cerrado 0 y fechaCierre)
    public static void cerrarCuenta(int idCuenta) throws SQLException
    {
        String sql = "UPDATE cuenta SET estado = 0, fechaCierre = NOW() WHERE idCuenta = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, idCuenta);
            stmt.executeUpdate();
        }
    }

    // Obtener la cuenta abierta de una mesa
    public static Cuenta obtenerCuentaAbierta(int idMesa) throws SQLException
    {
        String sql = "SELECT * FROM cuenta WHERE idMesa = ? AND estado = 1 LIMIT 1";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, idMesa);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    return new Cuenta(
                            rs.getInt("idCuenta"),
                            rs.getInt("idMesa"),
                            rs.getTimestamp("fechaApertura"),
                            rs.getTimestamp("fechaCierre"),
                            rs.getInt("estado")
                    );
                }
            }
        }
        return null;
    }

    // Listar todas las cuentas
    public static List<Cuenta> listar() throws SQLException
    {
        List<Cuenta> lista = new ArrayList<>();
        String sql = "SELECT * FROM cuenta";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                lista.add(new Cuenta(
                        rs.getInt("idCuenta"),
                        rs.getInt("idMesa"),
                        rs.getTimestamp("fechaApertura"),
                        rs.getTimestamp("fechaCierre"),
                        rs.getInt("estado")
                ));
            }
        }
        return lista;
    }
}
