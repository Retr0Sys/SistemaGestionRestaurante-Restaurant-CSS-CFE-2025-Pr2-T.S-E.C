package Forms;

import DAO.ConexionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Cocina extends JFrame
{
    public JPanel ventanaCocina;
    private JLabel lblTitulo;
    private JTable tblTablaCocina;
    private JComboBox<String> cboxEstadoPedido;
    private JButton btnActualizar;
    private JLabel lblEstadoPedido;
    private JButton btnAtras;

    private DefaultTableModel modelo;

    public Cocina()
    {
        // Configurar ícono botón atrás
        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png")
                .getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);

        setTitle("Gestión de Cocina");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);

        // Estados posibles
        cboxEstadoPedido.addItem("Pendiente");
        cboxEstadoPedido.addItem("En preparación");
        cboxEstadoPedido.addItem("Servido");
        cboxEstadoPedido.addItem("Cancelado");

        // Modelo de la tabla
        String[] columnas = {"ID", "Producto", "Mesa", "Cantidad", "Estado"};
        modelo = new DefaultTableModel(columnas, 0)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        tblTablaCocina.setModel(modelo);

        // Cargar datos
        cargarPedidos();

        // Acción del botón actualizar
        btnActualizar.addActionListener(e -> actualizarEstadoPedido());

        // Botón Atrás
        btnAtras.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCocina);
            topFrame.dispose();
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });
    }

    /**
     * Carga los pedidos activos desde la base de datos
     */
    private void cargarPedidos()
    {
        modelo.setRowCount(0);
        String sql = """
            SELECT p.idPedido, cP.nombre AS producto, m.idMesa AS mesa, 
                   p.cantidad, p.estado
            FROM pedido p
            JOIN cuenta c ON p.idCuenta = c.idCuenta
            JOIN mesa m ON c.idMesa = m.idMesa
            JOIN CatalogoProducto cP ON p.idProducto = cP.IdCatalogoProducto
            WHERE p.estado NOT IN ('Servido', 'Cancelado')
            ORDER BY p.fechaHora ASC
        """;

        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql))
        {
            while (rs.next())
            {
                int id = rs.getInt("idPedido");
                String producto = rs.getString("producto");
                String mesa = "Mesa " + rs.getInt("mesa");
                int cantidad = rs.getInt("cantidad");
                String estado = rs.getString("estado");

                modelo.addRow(new Object[]{id, producto, mesa, cantidad, estado});
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error cargando pedidos: " + e.getMessage());
        }
    }

    /**
     * Actualiza el estado del pedido seleccionado
     */
    private void actualizarEstadoPedido()
    {
        int fila = tblTablaCocina.getSelectedRow();
        if (fila != -1)
        {
            int idPedido = (int) tblTablaCocina.getValueAt(fila, 0);
            String nuevoEstado = (String) cboxEstadoPedido.getSelectedItem();

            String sql = "UPDATE pedido SET estado = ? WHERE idPedido = ?";

            try (Connection cn = ConexionDB.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql))
            {
                ps.setString(1, nuevoEstado);
                ps.setInt(2, idPedido);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
                cargarPedidos();
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(this, "Error al actualizar estado: " + e.getMessage());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.");
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            Cocina ventana = new Cocina();
            ventana.setContentPane(ventana.ventanaCocina);
            ventana.setBounds(300, 200, 700, 400);
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
