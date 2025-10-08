package Forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Resumen extends JFrame {
    public JPanel ventanaResumen;
    private JLabel lblTitulo;
    private JButton btnVentasDelDia;
    private JButton btnpedidosPorMesa;
    private JComboBox<String> cboxMesa;
    private JButton btnTop3;
    private JTable tblTabla;
    private JButton btnAtras;

    private DefaultTableModel modelo;

    public Resumen() {
        // Estética general
        Color fondo = new Color(245, 245, 245);
        Color acento = new Color(255, 159, 101);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 22);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);

        ventanaResumen = new JPanel(new BorderLayout());
        ventanaResumen.setBackground(fondo);
        ventanaResumen.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título
        lblTitulo = new JLabel("Resumen del Restaurante", SwingConstants.CENTER);
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setForeground(acento);
        ventanaResumen.add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Mesa", "Producto", "Cantidad", "Total"};
        modelo = new DefaultTableModel(columnas, 0);
        tblTabla = new JTable(modelo);
        tblTabla.setFont(fuenteGeneral);
        tblTabla.setRowHeight(28);
        tblTabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTabla.getTableHeader().setBackground(acento);
        tblTabla.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollTabla = new JScrollPane(tblTabla);
        ventanaResumen.add(scrollTabla, BorderLayout.CENTER);

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelControles.setBackground(fondo);

        cboxMesa = new JComboBox<>();
        cboxMesa.setFont(fuenteGeneral);
        cboxMesa.setBorder(BorderFactory.createLineBorder(acento, 2));
        cboxMesa.addItem("Mesa 1");
        cboxMesa.addItem("Mesa 2");
        cboxMesa.addItem("Mesa 3");
        cboxMesa.addItem("Mesa 4");

        btnVentasDelDia = new JButton();
        btnpedidosPorMesa = new JButton();
        btnTop3 = new JButton();
        btnAtras = new JButton();

        estilizarBoton(btnVentasDelDia, "imagenes/Ventas diarias.png");
        estilizarBoton(btnpedidosPorMesa, "imagenes/Pedidos x mesa.png");
        estilizarBoton(btnTop3, "imagenes/Top 3.png");
        estilizarBoton(btnAtras, "imagenes/Atras.png");

        panelControles.add(btnVentasDelDia);
        panelControles.add(btnpedidosPorMesa);
        panelControles.add(cboxMesa);
        panelControles.add(btnTop3);
        panelControles.add(btnAtras);

        ventanaResumen.add(panelControles, BorderLayout.SOUTH);

        // Acciones
        btnVentasDelDia.addActionListener(e -> cargarVentasDelDia());

        btnpedidosPorMesa.addActionListener(e -> {
            String mesaSeleccionada = (String) cboxMesa.getSelectedItem();
            if (mesaSeleccionada != null) {
                cargarPedidosPorMesa(mesaSeleccionada);
            }
        });

        btnTop3.addActionListener(e -> cargarTop3());

        btnAtras.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaResumen);
            topFrame.dispose();
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });

        //  Configuración de ventana
        setContentPane(ventanaResumen);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Resumen");
        setVisible(true);
    }

    private void estilizarBoton(JButton boton, String rutaIcono) {
        ImageIcon icono = new ImageIcon(new ImageIcon(rutaIcono).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        boton.setIcon(icono);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBorder(BorderFactory.createEmptyBorder());
            }
        });
    }

    private void cargarVentasDelDia() {
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{"Mesa 1", "Pizza Margarita", 2, "$500"});
        modelo.addRow(new Object[]{"Mesa 2", "Milanesa", 1, "$350"});
        modelo.addRow(new Object[]{"Mesa 3", "Refresco", 3, "$450"});
        modelo.addRow(new Object[]{"Mesa 1", "Flan casero", 2, "$300"});
        modelo.addRow(new Object[]{"Mesa 4", "Hamburguesa", 1, "$400"});
    }

    private void cargarPedidosPorMesa(String mesa) {
        modelo.setRowCount(0);
        switch (mesa) {
            case "Mesa 1":
                modelo.addRow(new Object[]{"Mesa 1", "Pizza Margarita", 2, "$500"});
                modelo.addRow(new Object[]{"Mesa 1", "Flan casero", 2, "$300"});
                break;
            case "Mesa 2":
                modelo.addRow(new Object[]{"Mesa 2", "Milanesa", 1, "$350"});
                break;
            case "Mesa 3":
                modelo.addRow(new Object[]{"Mesa 3", "Refresco", 3, "$450"});
                break;
            case "Mesa 4":
                modelo.addRow(new Object[]{"Mesa 4", "Hamburguesa", 1, "$400"});
                break;
        }
    }

    private void cargarTop3() {
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{"-", "Pizza Margarita", 10, "$2500"});
        modelo.addRow(new Object[]{"-", "Milanesa", 8, "$2800"});
        modelo.addRow(new Object[]{"-", "Refresco", 6, "$900"});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Resumen::new);
    }
}