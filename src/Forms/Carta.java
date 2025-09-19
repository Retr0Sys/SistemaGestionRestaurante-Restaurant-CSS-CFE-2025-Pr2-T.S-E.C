package Forms;

import Clases.abstractas.Producto;
import DAO.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Carta extends JFrame {
    public JPanel ventanaCarta;
    private JLabel lblTitulo;
    private JTable tblTablaCartaMenu;
    private JButton btnAtras;
    private JTabbedPane TBCarta;
    private JPanel JPCarta;
    private JPanel JPcambiarCarta;
    private JLabel lblNombProdAcambiar;
    private JLabel lblNuevoPrecio;
    private JLabel lblNuevoDisp;
    private JComboBox CBNuevoNombre;
    private JTextField txtNuevoPrecio;
    private JComboBox CBNuevaDisp;
    private JLabel lblTituloCarta;

    public Carta() {
        setTitle("Carta del Restaurante");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ventanaCarta = new JPanel(new BorderLayout());

        // Título
        lblTitulo = new JLabel("Carta del Restaurante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        ventanaCarta.add(lblTitulo, BorderLayout.NORTH);

        // Inicializar TabbedPane y paneles
        TBCarta = new JTabbedPane();
        JPCarta = new JPanel(new BorderLayout());
        JPcambiarCarta = new JPanel();
        JPcambiarCarta.setLayout(new GridLayout(5, 2, 5, 5)); // Para los campos de cambio

        TBCarta.addTab("Carta", JPCarta);
        TBCarta.addTab("Cambiar Producto", JPcambiarCarta);

        // Botón Atrás
        btnAtras = new JButton("Atrás");
        ventanaCarta.add(btnAtras, BorderLayout.SOUTH);
        btnAtras.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCarta);
            topFrame.dispose();

            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setVisible(true);
        });

        // Modelo de la tabla con columnas
        String[] columnas = {"ID", "Nombre", "Precio", "Categoría", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Cargar datos desde la BD
        ProductoDAO dao = new ProductoDAO();
        try {
            List<Producto> productos = dao.listar();
            for (Producto p : productos) {
                String estado = "Disponible";
                try {
                    java.lang.reflect.Method m = p.getClass().getMethod("getEstado");
                    int valor = (int) m.invoke(p);
                    estado = (valor == 1) ? "Disponible" : "No Disponible";
                } catch (Exception ex) {
                    // Por defecto
                }

                modelo.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getClass().getSimpleName(),
                        estado
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }

        // Crear la tabla y agregar al panel de la pestaña
        tblTablaCartaMenu = new JTable(modelo);
        tblTablaCartaMenu.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblTablaCartaMenu.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblTablaCartaMenu.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblTablaCartaMenu.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblTablaCartaMenu.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblTablaCartaMenu.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(tblTablaCartaMenu);
        JPCarta.add(scrollPane, BorderLayout.CENTER);

        // Panel de cambio de producto
        lblNombProdAcambiar = new JLabel("Producto:");
        CBNuevoNombre = new JComboBox<>();
        lblNuevoPrecio = new JLabel("Nuevo Precio:");
        txtNuevoPrecio = new JTextField();
        lblNuevoDisp = new JLabel("Disponibilidad:");
        CBNuevaDisp = new JComboBox<>(new String[]{"Disponible", "No Disponible"});

        JPcambiarCarta.add(lblNombProdAcambiar);
        JPcambiarCarta.add(CBNuevoNombre);
        JPcambiarCarta.add(lblNuevoPrecio);
        JPcambiarCarta.add(txtNuevoPrecio);
        JPcambiarCarta.add(lblNuevoDisp);
        JPcambiarCarta.add(CBNuevaDisp);

        // Cargar nombres al comboBox
        for (int i = 0; i < tblTablaCartaMenu.getRowCount(); i++) {
            CBNuevoNombre.addItem(tblTablaCartaMenu.getValueAt(i, 1).toString());
        }

        ventanaCarta.add(TBCarta, BorderLayout.CENTER);
        add(ventanaCarta);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Carta carta = new Carta();
            carta.setContentPane(carta.ventanaCarta);
            carta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            carta.pack();
            carta.setLocationRelativeTo(null);
            carta.setVisible(true);
        });
    }
}
