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
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);

        setTitle("Gestión de Cocina");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalTextPosition(JLabel.CENTER);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);

        // Estados posibles
        cboxEstadoPedido.addItem("Pendiente");
        cboxEstadoPedido.addItem("En preparación");
        cboxEstadoPedido.addItem("Servido");
        cboxEstadoPedido.addItem("Cancelado");

        // Modelo de la tabla
        String[] columnas = {"ID", "Nombre", "Cantidad", "Notas", "Mesa", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);
        tblTablaCocina.setModel(modelo);

        // Cargar datos desde la BD (solo los que no están servidos)
        cargarPedidos();

        // Acción del botón actualizar
        btnActualizar.addActionListener(e -> {
            int filaSeleccionada = tblTablaCocina.getSelectedRow();
            if (filaSeleccionada != -1)
            {
                String nuevoEstado = (String) cboxEstadoPedido.getSelectedItem();
                int idPedido = (int) tblTablaCocina.getValueAt(filaSeleccionada, 0);

                try (Connection cn = ConexionDB.getConnection();
                     PreparedStatement ps = cn.prepareStatement(
                             "UPDATE Cocina SET estado = ? WHERE idPedidoCocina = ?"))
                {
                    ps.setString(1, nuevoEstado);
                    ps.setInt(2, idPedido);
                    ps.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");

                    // Refrescar la tabla mostrando solo los pedidos no servidos
                    cargarPedidos();
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(this, "Error al actualizar estado: " + ex.getMessage());
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.");
            }
        });

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

    private void cargarPedidos()
    {
        modelo.setRowCount(0); // limpiar tabla
        try (Connection cn = ConexionDB.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Cocina WHERE estado != 'Servido'"))
        {
            while (rs.next())
            {
                int id = rs.getInt("idPedidoCocina");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                String notas = rs.getString("notas");
                String mesa = rs.getString("mesa");
                String estado = rs.getString("estado");

                modelo.addRow(new Object[]{id, nombre, cantidad, notas, mesa, estado});
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error cargando pedidos: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            Cocina ventana = new Cocina();
            ventana.setContentPane(ventana.ventanaCocina);
            ventana.setBounds(300, 200, 600, 400);
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
