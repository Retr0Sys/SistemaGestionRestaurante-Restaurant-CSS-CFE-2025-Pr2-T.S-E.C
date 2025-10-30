package Forms;

import DAO.ConexionDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Cocina extends JFrame {
    public JPanel ventanaCocina;
    private JLabel lblTitulo;
    private JTable tblTablaCocina;
    private JComboBox<String> cboxEstadoPedido;
    private JButton btnActualizar;
    private JLabel lblEstadoPedido;
    private JButton btnAtras;

    private DefaultTableModel modelo;

    public Cocina() {
        // Paleta y fuentes
        Color fondo = new Color(245, 245, 245);
        Color acento = new Color(255, 159, 101);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);

        ventanaCocina = new JPanel(new BorderLayout());
        ventanaCocina.setBackground(fondo);

        lblTitulo = new JLabel("Gestión de Cocina");
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(new Color(60, 60, 60));

        tblTablaCocina = new JTable();
        cboxEstadoPedido = new JComboBox<>();
        btnActualizar = new JButton("Actualizar");
        lblEstadoPedido = new JLabel("Estado:");
        btnAtras = new JButton();

        // Botón atrás
        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png")
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);
        btnAtras.setPreferredSize(new Dimension(100, 100));
        btnAtras.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo estados
        cboxEstadoPedido.addItem("Pendiente");
        cboxEstadoPedido.addItem("En preparación");
        cboxEstadoPedido.addItem("Servido");
        cboxEstadoPedido.addItem("Cancelado");
        cboxEstadoPedido.setFont(fuenteGeneral);
        cboxEstadoPedido.setBorder(BorderFactory.createLineBorder(acento, 2));

        lblEstadoPedido.setFont(fuenteGeneral);
        btnActualizar.setFont(fuenteGeneral);
        btnActualizar.setBackground(acento);
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 35));
        btnActualizar.setBorder(BorderFactory.createLineBorder(acento, 2));

        // Tabla
        String[] columnas = {"ID", "Producto", "Mesa", "Cantidad", "Estado"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        //Visual a las tablas
        tblTablaCocina.setModel(modelo);
        tblTablaCocina.setFont(fuenteGeneral);
        tblTablaCocina.setRowHeight(28);
        tblTablaCocina.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTablaCocina.getTableHeader().setBackground(acento);
        tblTablaCocina.getTableHeader().setForeground(Color.WHITE);
        tblTablaCocina.setShowGrid(false);
        tblTablaCocina.setIntercellSpacing(new Dimension(0, 0));
        tblTablaCocina.setSelectionBackground(new Color(255, 230, 200));
        tblTablaCocina.setSelectionForeground(Color.BLACK);

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(255, 255, 255));
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        //Configuración de paneles para una mejor organización
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTitulo.setOpaque(false);
        panelTitulo.add(lblTitulo);

        panelSuperior.add(btnAtras, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBackground(new Color(255, 255, 255));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        panelInferior.add(lblEstadoPedido);
        panelInferior.add(cboxEstadoPedido);
        panelInferior.add(btnActualizar);

        // Agregar componentes
        ventanaCocina.add(panelSuperior, BorderLayout.NORTH);
        ventanaCocina.add(new JScrollPane(tblTablaCocina), BorderLayout.CENTER);
        ventanaCocina.add(panelInferior, BorderLayout.SOUTH);

        // Cargar datos
        cargarPedidos();

        // Acciones
        btnActualizar.addActionListener(e -> actualizarEstadoPedido());

        btnAtras.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCocina);
            if (topFrame != null) topFrame.dispose();

            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setUndecorated(true);
            menu.pack();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
    //Cargar pedidos a la BD
    private void cargarPedidos() {
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
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("idPedido");
                String producto = rs.getString("producto");
                String mesa = "Mesa " + rs.getInt("mesa");
                int cantidad = rs.getInt("cantidad");
                String estado = rs.getString("estado");

                modelo.addRow(new Object[]{id, producto, mesa, cantidad, estado});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error cargando pedidos: " + e.getMessage());
        }
    }
    //Actualizar pedidos en la BD
    private void actualizarEstadoPedido() {
        int fila = tblTablaCocina.getSelectedRow();
        if (fila != -1) {
            int idPedido = (int) tblTablaCocina.getValueAt(fila, 0);
            String nuevoEstado = (String) cboxEstadoPedido.getSelectedItem();

            String sql = "UPDATE pedido SET estado = ? WHERE idPedido = ?";

            try (Connection cn = ConexionDB.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, nuevoEstado);
                ps.setInt(2, idPedido);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "✅ Estado actualizado correctamente.",
                        "Confirmación",
                        JOptionPane.INFORMATION_MESSAGE);
                cargarPedidos();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error al actualizar estado: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Seleccione un pedido de la tabla.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cocina ventana = new Cocina();
            ventana.setContentPane(ventana.ventanaCocina);
            ventana.setUndecorated(true);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}