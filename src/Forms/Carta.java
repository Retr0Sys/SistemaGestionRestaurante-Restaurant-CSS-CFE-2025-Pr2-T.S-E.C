package Forms;

import Clases.abstractas.Producto;
import Clases.concret.Comida;
import Clases.concret.Bebida;
import Clases.concret.Postre;
import DAO.ProductoDAO;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;


public class Carta extends JFrame
{
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
    private JComboBox CBProducto;
    private JTextField txtPrecio;
    private JComboBox CBDisponibilidad;
    private JLabel lblTituloCarta;
    private JButton btnModificar;
    private JPanel JPCrear;
    private JTextField txtNuevoProducto;
    private JTextField txtNuevoPrecio;
    private JComboBox CBcategoria;
    private JButton btnCrear;
    private JPanel panelBotonAtras;
    private JPanel panelBotonModificar;
    private JPanel panelBotonCrear;
    private JScrollPane JSCarta;

    private DefaultTableModel modelo;

    public Carta()
    {
        setTitle("Carta del Restaurante");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Estética general
        Color fondo = new Color(245, 245, 245);
        Color blanco = new Color(255, 255, 255);
        Color acento = new Color(255, 159, 101);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 16);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 18);

        ventanaCarta.setBackground(fondo);
        ventanaCarta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPCarta.setBackground(blanco);
        JPcambiarCarta.setBackground(blanco);
        JPCrear.setBackground(blanco);

        JPcambiarCarta.setBorder(BorderFactory.createTitledBorder("Modificar Producto"));
        JPCrear.setBorder(BorderFactory.createTitledBorder("Crear Nuevo Producto"));

        lblTituloCarta.setFont(fuenteTitulo);
        lblTituloCarta.setForeground(acento);

        txtPrecio.setFont(fuenteGeneral);
        txtNuevoProducto.setFont(fuenteGeneral);
        txtNuevoPrecio.setFont(fuenteGeneral);
        CBProducto.setFont(fuenteGeneral);
        CBDisponibilidad.setFont(fuenteGeneral);
        CBcategoria.setFont(fuenteGeneral);

        CBProducto.setBorder(BorderFactory.createLineBorder(acento, 2));
        CBDisponibilidad.setBorder(BorderFactory.createLineBorder(acento, 2));
        CBcategoria.setBorder(BorderFactory.createLineBorder(acento, 2));
        txtPrecio.setBorder(BorderFactory.createLineBorder(acento, 1));
        txtNuevoProducto.setBorder(BorderFactory.createLineBorder(acento, 1));
        txtNuevoPrecio.setBorder(BorderFactory.createLineBorder(acento, 1));

        // Botones con estilo
        btnModificar = crearBotonConEstilo("imagenes/Actualizar.png", 150, 160);
        btnCrear = crearBotonConEstilo("imagenes/Enviar.png", 150, 160);
        btnAtras = crearBotonConEstilo("imagenes/Atras.png", 130, 140);

        panelBotonModificar.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotonModificar.setBackground(blanco);
        panelBotonModificar.add(btnModificar);

        panelBotonCrear.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBotonCrear.setBackground(blanco);
        panelBotonCrear.add(btnCrear);

        panelBotonAtras.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBotonAtras.setBackground(fondo);
        panelBotonAtras.add(btnAtras);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Precio", "Categoría", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);
        tblTablaCartaMenu.setModel(modelo);
        tblTablaCartaMenu.setFont(fuenteGeneral);
        tblTablaCartaMenu.setRowHeight(28);
        tblTablaCartaMenu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblTablaCartaMenu.getTableHeader().setBackground(acento);
        tblTablaCartaMenu.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tblTablaCartaMenu);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPCarta.setLayout(new BorderLayout());
        JPCarta.add(scroll, BorderLayout.CENTER);

        // Ocultar columna ID
        tblTablaCartaMenu.getColumnModel().getColumn(0).setMinWidth(0);
        tblTablaCartaMenu.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTablaCartaMenu.getColumnModel().getColumn(0).setPreferredWidth(0);

        //  Cargar datos
        cargarProductosDisponibles();
        cargarComboProductos();

        CBDisponibilidad.addItem("Disponible");
        CBDisponibilidad.addItem("No Disponible");

        CBcategoria.addItem("comida");
        CBcategoria.addItem("bebida");
        CBcategoria.addItem("postre");

        //  Eventos
        CBProducto.addActionListener(e -> {
            String nombreSeleccionado = (String) CBProducto.getSelectedItem();
            if (nombreSeleccionado != null) {
                try {
                    for (Producto p : ProductoDAO.listar()) {
                        if (p.getNombre().equals(nombreSeleccionado)) {
                            txtPrecio.setText(String.valueOf(p.getPrecio()));
                            CBDisponibilidad.setSelectedItem(p.getEstado() == 1 ? "Disponible" : "No Disponible");
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error obteniendo producto: " + ex.getMessage());
                }
            }
        });

        tblTablaCartaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tblTablaCartaMenu.getSelectedRow();
                if (fila != -1) {
                    String nombreSeleccionado = (String) tblTablaCartaMenu.getValueAt(fila, 1);
                    CBProducto.setSelectedItem(nombreSeleccionado);
                    try {
                        for (Producto p : ProductoDAO.listar()) {
                            if (p.getNombre().equals(nombreSeleccionado)) {
                                txtPrecio.setText(String.valueOf(p.getPrecio()));
                                CBDisponibilidad.setSelectedItem(p.getEstado() == 1 ? "Disponible" : "No Disponible");
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Error cargando datos del producto: " + ex.getMessage());
                    }
                }
            }
        });
        // Botón Modificar
        btnModificar.addActionListener(e ->
        {
            String nombreSeleccionado = (String) CBProducto.getSelectedItem();
            String nuevoPrecioStr = txtPrecio.getText();
            String nuevoEstadoStr = (String) CBDisponibilidad.getSelectedItem();

            if (nombreSeleccionado == null || nuevoPrecioStr.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Seleccione un producto y complete el precio.");
                return;
            }
            try
            {
                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                int estado = nuevoEstadoStr.equals("Disponible") ? 1 : 0;

                Producto productoSeleccionado = null;
                for (Producto p : ProductoDAO.listar())
                {
                    if (p.getNombre().equals(nombreSeleccionado))
                    {
                        productoSeleccionado = p;
                        break;
                    }
                }
                if (productoSeleccionado == null)
                {
                    JOptionPane.showMessageDialog(this, "Producto no encontrado en la base de datos.");
                    return;
                }
                int idProducto = productoSeleccionado.getId();
                ProductoDAO.actualizarProducto(idProducto, nuevoPrecio, estado);
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                // Buscar fila en la tabla
                int filaProducto = -1;
                for (int i = 0; i < tblTablaCartaMenu.getRowCount(); i++)
                {
                    if ((int) tblTablaCartaMenu.getValueAt(i, 0) == idProducto)
                    {
                        filaProducto = i;
                        break;
                    }
                }
                if (estado == 1)
                {
                    if (filaProducto != -1)
                    {
                        tblTablaCartaMenu.setValueAt(nuevoPrecio, filaProducto, 2);
                        tblTablaCartaMenu.setValueAt("Disponible", filaProducto, 4);
                    }
                    else
                    {
                        modelo.addRow(new Object[]
                                {
                                        idProducto,
                                        productoSeleccionado.getNombre(),
                                        nuevoPrecio,
                                        productoSeleccionado.getClass().getSimpleName(),
                                        "Disponible"
                                });
                    }
                }
                else
                {
                    if (filaProducto != -1)
                    {
                        modelo.removeRow(filaProducto);
                    }
                }
                txtPrecio.setText("");
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Precio inválido.");
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
            }
        });
        // Botón Crear
        btnCrear.addActionListener(e ->
        {
            String nombre = txtNuevoProducto.getText().trim();
            String categoria = (String) CBcategoria.getSelectedItem();
            String precioStr = txtNuevoPrecio.getText().trim();

            if (nombre.isEmpty() || categoria == null || precioStr.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Complete todos los campos para crear el producto.");
                return;
            }
            try
            {
                double precio = Double.parseDouble(precioStr);
                Producto nuevoProducto = null;

                switch (categoria.toLowerCase())
                {
                    case "comida":
                        nuevoProducto = new Comida(0, nombre, precio, 1);
                        break;

                    case "bebida":
                        nuevoProducto = new Bebida(0, nombre, precio, 1);
                        break;

                    case "postre":
                        nuevoProducto = new Postre(0, nombre, precio, 1);
                        break;

                    default:
                        JOptionPane.showMessageDialog(this, "Categoría inválida.");
                        return;
                }

                ProductoDAO.crearProducto(nuevoProducto);
                JOptionPane.showMessageDialog(this, "Producto creado correctamente.");

                // Refrescar tabla y combo
                cargarProductosDisponibles();
                cargarComboProductos();

                txtNuevoProducto.setText("");
                txtNuevoPrecio.setText("");
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Precio inválido.");
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this, "Error al crear producto: " + ex.getMessage());
            }
        });

        // Botón Atrás
        btnAtras.addActionListener(e ->
        {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCarta);
            topFrame.dispose();

            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menu.pack();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }

    //  Llena el ComboBox con TODOS los productos (disponibles y no disponibles)
    private void cargarComboProductos()
    {
        CBProducto.removeAllItems();
        try
        {
            List<Producto> productos = ProductoDAO.listar();

            for (Producto p : productos)
            {
                CBProducto.addItem(p.getNombre());
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error cargando productos en combo: " + e.getMessage());
        }
    }

    // 🔹 Llena la tabla SOLO con los productos disponibles
    private void cargarProductosDisponibles()
    {
        modelo.setRowCount(0);
        try
        {
            List<Producto> productos = ProductoDAO.listarDisponibles();
            for (Producto p : productos)
            {
                modelo.addRow(new Object[]
                        {
                                p.getId(),
                                p.getNombre(),
                                p.getPrecio(),
                                p.getClass().getSimpleName(),
                                "Disponible"
                        });
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }
    }
    //Modificaciones en los JButtons
    private JButton crearBotonConEstilo(String rutaImagen, int tamañoNormal, int tamañoZoom) {
        JButton boton = new JButton();
        ImageIcon icono = new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(tamañoNormal, tamañoNormal, Image.SCALE_SMOOTH));
        boton.setIcon(icono);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(tamañoZoom, tamañoZoom, Image.SCALE_SMOOTH)));
                boton.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(tamañoNormal, tamañoNormal, Image.SCALE_SMOOTH)));
                boton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        return boton;
    }

    public static void mostrarPantallaCompleta(JFrame ventana) {
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza
        ventana.setUndecorated(true); // Quita bordes y barra de título
        ventana.setVisible(true); // Muestra la ventana
    }


    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            Carta carta = new Carta();
            carta.setContentPane(carta.ventanaCarta);
            carta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            carta.pack();
            carta.setLocationRelativeTo(null);
            carta.setVisible(true);
            mostrarPantallaCompleta(carta);

        });
    }
}
