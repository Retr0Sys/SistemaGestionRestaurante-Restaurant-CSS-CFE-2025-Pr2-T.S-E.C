package Forms;

import Clases.abstractas.Producto;
import Clases.concret.Comida;
import Clases.concret.Bebida;
import Clases.concret.Postre;
import Interfaces.CartaService;
import Interfaces.CartaServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Carta extends JFrame {
    public JPanel ventanaCarta;
    private JLabel lblTituloCarta;
    private JTable tblTablaCartaMenu;
    private JButton btnAtras, btnModificar, btnCrear;
    private JTabbedPane TBCarta;
    private JPanel JPCarta, JPcambiarCarta, JPCrear;
    private JComboBox<String> CBProducto, CBDisponibilidad, CBcategoria;
    private JTextField txtPrecio, txtStockCambiar, txtNuevoProducto, txtNuevoPrecio, txtStockCrear;
    private JPanel panelBotonAtras, panelBotonModificar, panelBotonCrear;
    private JScrollPane JSCarta;
    private JLabel lblNombProdAcambiar;
    private JLabel lblNuevoPrecio;
    private JLabel lblNuevoDisp;
    private JLabel lblStockCambiar;
    private JLabel lblStockCrear;
    private DefaultTableModel modelo;

    private final CartaService cartaService = new CartaServiceImpl();

    public Carta() {
        // ===== Resolución y estilos =====
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int anchoPantalla = screenSize.width;
        int tamañoNormal = (int) (anchoPantalla * 0.10);
        int tamañoZoom = tamañoNormal + 15;

        Color fondo = new Color(245, 245, 245);
        Color blanco = new Color(255, 255, 255);
        Color acento = new Color(255, 159, 101);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 16);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 18);

        // ===== Ventana principal =====
        setTitle("Carta del Restaurante");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ventanaCarta = new JPanel(new BorderLayout());
        ventanaCarta.setBackground(fondo);
        ventanaCarta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== Botones con estilo =====
        btnModificar = crearBotonConEstilo("imagenes/Actualizar.png", tamañoNormal, tamañoZoom);
        btnCrear = crearBotonConEstilo("imagenes/Enviar.png", tamañoNormal, tamañoZoom);
        btnAtras = crearBotonConEstilo("imagenes/Atras.png", tamañoNormal, tamañoZoom);

        // ===== TABS =====
        TBCarta = new JTabbedPane();
        TBCarta.setFont(fuenteGeneral);
        TBCarta.setBackground(blanco);
        TBCarta.setBorder(BorderFactory.createLineBorder(acento, 2));

        JPCarta = new JPanel(new BorderLayout());
        JPcambiarCarta = new JPanel();
        JPCrear = new JPanel();

        JPCarta.setBackground(blanco);
        JPcambiarCarta.setBackground(blanco);
        JPCrear.setBackground(blanco);

        TBCarta.addTab("Carta", JPCarta);
        TBCarta.addTab("Modificar Producto", JPcambiarCarta);
        TBCarta.addTab("Crear Producto", JPCrear);

        ventanaCarta.add(TBCarta, BorderLayout.CENTER);

        // ===== Panel Atrás =====
        panelBotonAtras = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonAtras.setBackground(fondo);
        panelBotonAtras.add(btnAtras);
        ventanaCarta.add(panelBotonAtras, BorderLayout.SOUTH);

        // ===== Tabla Carta =====
        String[] columnas = {"ID", "Nombre", "Precio", "Categoría", "Estado", "Stock"};
        modelo = new DefaultTableModel(columnas, 0);
        tblTablaCartaMenu = new JTable(modelo);
        tblTablaCartaMenu.setFont(fuenteGeneral);
        tblTablaCartaMenu.setRowHeight(28);
        tblTablaCartaMenu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTablaCartaMenu.getTableHeader().setBackground(acento);
        tblTablaCartaMenu.getTableHeader().setForeground(Color.WHITE);
        tblTablaCartaMenu.setShowGrid(false);
        tblTablaCartaMenu.setIntercellSpacing(new Dimension(0, 0));
        tblTablaCartaMenu.setSelectionBackground(new Color(255, 230, 200));
        tblTablaCartaMenu.setSelectionForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(tblTablaCartaMenu);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPCarta.add(scroll, BorderLayout.CENTER);

        tblTablaCartaMenu.getColumnModel().getColumn(0).setMinWidth(0);
        tblTablaCartaMenu.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTablaCartaMenu.getColumnModel().getColumn(0).setPreferredWidth(0);

        // ===== Modificar Producto =====
        JPcambiarCarta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(acento, 1), "Modificar Producto"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JPcambiarCarta.setLayout(new GridLayout(5, 2, 10, 10));

        CBProducto = new JComboBox<>();
        txtPrecio = new JTextField();
        txtStockCambiar = new JTextField();
        CBDisponibilidad = new JComboBox<>();
        CBDisponibilidad.addItem("Disponible");
        CBDisponibilidad.addItem("No Disponible");

        JPcambiarCarta.add(new JLabel("Producto:")); JPcambiarCarta.add(CBProducto);
        JPcambiarCarta.add(new JLabel("Nuevo Precio:")); JPcambiarCarta.add(txtPrecio);
        JPcambiarCarta.add(new JLabel("Nuevo Stock:")); JPcambiarCarta.add(txtStockCambiar);
        JPcambiarCarta.add(new JLabel("Disponibilidad:")); JPcambiarCarta.add(CBDisponibilidad);

        panelBotonModificar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonModificar.setBackground(blanco);
        panelBotonModificar.add(btnModificar);
        JPcambiarCarta.add(panelBotonModificar);

        // ===== Crear Producto =====
        JPCrear.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(acento, 1), "Crear Nuevo Producto"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JPCrear.setLayout(new GridLayout(5, 2, 10, 10));

        txtNuevoProducto = new JTextField();
        txtNuevoPrecio = new JTextField();
        txtStockCrear = new JTextField();
        CBcategoria = new JComboBox<>();
        CBcategoria.addItem("comida");
        CBcategoria.addItem("bebida");
        CBcategoria.addItem("postre");

        JPCrear.add(new JLabel("Nombre:")); JPCrear.add(txtNuevoProducto);
        JPCrear.add(new JLabel("Precio:")); JPCrear.add(txtNuevoPrecio);
        JPCrear.add(new JLabel("Stock:")); JPCrear.add(txtStockCrear);
        JPCrear.add(new JLabel("Categoría:")); JPCrear.add(CBcategoria);

        panelBotonCrear = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonCrear.setBackground(blanco);
        panelBotonCrear.add(btnCrear);
        JPCrear.add(panelBotonCrear);

        // ===== Eventos =====
        CBProducto.addActionListener(e -> cargarDatosProductoSeleccionado());
        tblTablaCartaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblTablaCartaMenu.getSelectedRow();
                if (fila != -1) {
                    CBProducto.setSelectedItem(tblTablaCartaMenu.getValueAt(fila, 1).toString());
                    cargarDatosProductoSeleccionado();
                }
            }
        });

        btnModificar.addActionListener(e -> modificarProducto());
        btnCrear.addActionListener(e -> crearProducto());
        btnAtras.addActionListener(e -> cerrarVentana());

        // ===== Cargar datos iniciales =====
        cargarProductosDisponibles();
        cargarComboProductos();
    }

    private void cargarDatosProductoSeleccionado() {
        String nombre = (String) CBProducto.getSelectedItem();
        if (nombre != null) {
            try {
                Producto p = cartaService.buscarPorNombre(nombre);
                if (p != null) {
                    txtPrecio.setText(String.valueOf(p.getPrecio()));
                    txtStockCambiar.setText(String.valueOf(cartaService.obtenerStock(p.getId())));
                    CBDisponibilidad.setSelectedItem(p.getEstado() == 1 ? "Disponible" : "No Disponible");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error obteniendo datos: " + e.getMessage());
            }
        }
    }

    private void cargarProductosDisponibles() {
        modelo.setRowCount(0);
        try {
            List<Producto> productos = cartaService.listarTodos();
            for (Producto p : productos) {
                int stock = cartaService.obtenerStock(p.getId());
                String estadoStr = (p.getEstado() == 1 && stock > 0) ? "Disponible" : "No Disponible";

                modelo.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getClass().getSimpleName(),
                        estadoStr,
                        stock
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }
    }


    private void cargarComboProductos() {
        CBProducto.removeAllItems();
        try {
            List<Producto> productos = cartaService.listarTodos();
            for (Producto p : productos) {
                CBProducto.addItem(p.getNombre());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando combo: " + e.getMessage());
        }
    }

    private void modificarProducto() {
        String nombre = (String) CBProducto.getSelectedItem();
        if (nombre == null) return;

        try {
            String precioStr = txtPrecio.getText().trim();
            String stockStr = txtStockCambiar.getText().trim();

            if (precioStr.isEmpty() || stockStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Precio y stock son obligatorios.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (stock <= 5 && stock > 0) {
                JOptionPane.showMessageDialog(this, "El stock es bajo (≤5).",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            int estado = (stock == 0) ? 0 : (CBDisponibilidad.getSelectedItem().equals("Disponible") ? 1 : 0);
            Producto p = cartaService.buscarPorNombre(nombre);
            if (p != null) {
                cartaService.actualizarProducto(p.getId(), precio, estado);
                cartaService.actualizarStock(p.getId(), stock);
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                cargarProductosDisponibles();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio o stock inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void crearProducto() {
        try {
            String nombre = txtNuevoProducto.getText().trim();
            String categoria = (String) CBcategoria.getSelectedItem();
            String precioStr = txtNuevoPrecio.getText().trim();
            String stockStr = txtStockCrear.getText().trim();

            // Validaciones
            if (nombre.isEmpty() || nombre.matches("\\d+")) {
                JOptionPane.showMessageDialog(this,
                        "El nombre del producto es obligatorio y no puede ser solo números.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "El stock no puede ser negativo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (stock <= 5) {
                JOptionPane.showMessageDialog(this, "El stock es bajo (≤5).",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

            int estado = (stock == 0) ? 0 : 1; // stock 0 → No Disponible
            Producto nuevo;
            switch (categoria.toLowerCase()) {
                case "comida" -> nuevo = new Comida(0, nombre, precio, estado);
                case "bebida" -> nuevo = new Bebida(0, nombre, precio, estado);
                case "postre" -> nuevo = new Postre(0, nombre, precio, estado);
                default -> {
                    JOptionPane.showMessageDialog(this, "Categoría inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            cartaService.crearProducto(nuevo);
            cartaService.actualizarStock(nuevo.getId(), stock);
            JOptionPane.showMessageDialog(this, "Producto creado correctamente.");

            cargarProductosDisponibles();
            cargarComboProductos();
            txtNuevoProducto.setText("");
            txtNuevoPrecio.setText("");
            txtStockCrear.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio o stock inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al crear producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void cerrarVentana() {
        dispose();
        MenuPuntoVenta menu = new MenuPuntoVenta();
        menu.setContentPane(menu.JPMenuPrinc);
        menu.setUndecorated(true);
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menu.setVisible(true);
    }

    private JButton crearBotonConEstilo(String rutaImagen, int tamañoNormal, int tamañoZoom) {
        JButton boton = new JButton();
        ImageIcon icono = new ImageIcon(new ImageIcon(rutaImagen).getImage()
                .getScaledInstance(tamañoNormal, tamañoNormal, Image.SCALE_SMOOTH));
        boton.setIcon(icono);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage()
                        .getScaledInstance(tamañoZoom, tamañoZoom, Image.SCALE_SMOOTH)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage()
                        .getScaledInstance(tamañoNormal, tamañoNormal, Image.SCALE_SMOOTH)));
            }
        });
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Carta carta = new Carta();
            carta.setContentPane(carta.ventanaCarta);
            carta.setUndecorated(true);
            carta.setExtendedState(JFrame.MAXIMIZED_BOTH);
            carta.setVisible(true);
        });
    }
}
