package Forms;

import Clases.abstractas.Producto;
import Clases.concret.Mesa;
import Clases.concret.Pedido;
import Clases.concret.Cuenta;
import DAO.MesaDAO;
import DAO.PedidoDAO;
import DAO.CuentaDAO;
import DAO.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FormMesa extends JFrame {
    public JPanel JPMesasIni;
    public  JComboBox<String> CBSelecEstado;
    private JComboBox<Integer> CBmesa;
    private JLabel lblEstado;
    private JTable TBPedidosMesa;
    private JButton cambiarEstadoButton;
    private JButton btnAtras;
    private JTabbedPane JPPedidos;
    private JPanel JPprincipal;
    private JSplitPane SplitPrincipal;
    private JPanel JParribaPri;
    private JPanel JPabajoPri;
    private JScrollPane ScrollAbajoPri;
    private JLabel lblPedidoMesa;
    private JPanel JPasignar;
    private JPanel JPpedidosRealizar;
    private JPanel JPReservas;
    private JLabel lblMesas;
    private JLabel lblMesa2;
    private JComboBox CBmesa2;
    private JComboBox CBMesero;
    private JLabel lblMesero;
    private JButton BtnAsignar;
    private JLabel lblMesa3;
    private JLabel lblOperacion;
    protected JTable TBProductosMesa;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JLabel lblSubtotal;
    JTextField txtSubtotal;
    private JButton BtnEnviar;
    private JLabel lblPedidosMesaPedidos;
    private JComboBox CBmesa3;
    private JLabel lblMesa4;
    private JLabel lblOperacionReserva;
    private JLabel lblCanitdadPersonas;
    private JTextField txtCantidadPersonas;
    private JLabel lblFecha;
    private JButton btnEnviar;
    private JRadioButton rbtnAñadir;
    private JRadioButton rbtnEliminar;
    private JLabel lblMesa;
    private JLabel lblSelecEstado;
    private JLabel lblEstadoFin;
    private JLabel lblCalendario;
    private JComboBox CBmesa4;
    private JButton btnAbrirCuenta;
    private JButton btnCerrarCuenta;
    private JButton btnEliminarProd;
    private JButton btnAñadirProd;
    private JLabel lblCuenta;
    private JTable TBCuentas;
    private Mesita vistaMesas;

    private MesaDAO mesaDAO = new MesaDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private CuentaDAO cuentaDAO = new CuentaDAO();

    public FormMesa() {
        setContentPane(JPMesasIni);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

        // Estética general
        Color fondo = new Color(245, 245, 245);
        Color acento = new Color(255, 159, 101);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 20);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);

        JPMesasIni.setBackground(fondo);
        JPMesasIni.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Tablas estilizadas
        TBPedidosMesa.setFont(fuenteGeneral);
        TBPedidosMesa.setRowHeight(28);
        TBPedidosMesa.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        TBPedidosMesa.getTableHeader().setBackground(acento);
        TBPedidosMesa.getTableHeader().setForeground(Color.WHITE);

        TBProductosMesa.setFont(fuenteGeneral);
        TBProductosMesa.setRowHeight(28);
        TBProductosMesa.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        TBProductosMesa.getTableHeader().setBackground(acento);
        TBProductosMesa.getTableHeader().setForeground(Color.WHITE);
        lblMesas.setFont(fuenteTitulo);
        lblMesas.setForeground(Color.BLACK);

        // Subtotal visual
        txtSubtotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtSubtotal.setForeground(new Color(124, 126, 124));
        txtSubtotal.setHorizontalAlignment(JTextField.CENTER);

        // Combos estilizados
        CBmesa.setFont(fuenteGeneral);
        CBmesa3.setFont(fuenteGeneral);
        CBSelecEstado.setFont(fuenteGeneral);
        CBmesa.setBorder(BorderFactory.createLineBorder(acento, 2));
        CBmesa3.setBorder(BorderFactory.createLineBorder(acento, 2));
        CBSelecEstado.setBorder(BorderFactory.createLineBorder(acento, 2));

        // Botones con cursor y sombra
        inicializarIconos();
        aplicarHover(btnAtras);
        aplicarHover(cambiarEstadoButton);
        aplicarHover(BtnAsignar);
        aplicarHover(BtnEnviar);
        aplicarHover(btnEnviar);
        aplicarHover(btnAñadirProd);
        aplicarHover(btnEliminarProd);
        aplicarHover(btnAbrirCuenta);
        aplicarHover(btnCerrarCuenta);

        if (TBProductosMesa.getModel() == null || TBProductosMesa.getColumnCount() == 0) {
            TBProductosMesa.setModel(new DefaultTableModel(
                    new Object[]{"ID", "Producto", "Cantidad", "Subtotal"}, 0
            ));
        }

        // Inicializar subtotal
        if (txtSubtotal.getText().isEmpty()) {
            txtSubtotal.setText("0");
        }

        vistaMesas = new Mesita(); // usa el constructor sin parámetros
        JPMesasIni.add(vistaMesas.panelMesita, BorderLayout.CENTER); // o donde quieras ubicarla

        cargarMesas();
        cargarEstados();

        CBmesa.addActionListener(e -> mostrarDatosMesa());
        cambiarEstadoButton.addActionListener(e -> cambiarEstadoMesa());

        btnAbrirCuenta.addActionListener(e -> abrirCuenta());
        btnCerrarCuenta.addActionListener(e -> cerrarCuenta());

        btnAñadirProd.addActionListener(e -> abrirDialogoAgregarPedido());
        btnEliminarProd.addActionListener(e -> eliminarProductoSeleccionado());

        BtnEnviar.addActionListener(e -> enviarPedidos());

        btnAtras.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMesasIni);
            topFrame.dispose();
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menu.pack();
            menu.setVisible(true);
        });
    }
    private void aplicarHover(JButton boton) {
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


    private void inicializarIconos() {
        btnAtras.setIcon(new ImageIcon(new ImageIcon("imagenes/Atras.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);

        cambiarEstadoButton.setIcon(new ImageIcon(new ImageIcon("imagenes/Cambiar estado.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        cambiarEstadoButton.setBorderPainted(false);
        cambiarEstadoButton.setContentAreaFilled(false);
        cambiarEstadoButton.setFocusPainted(false);

        BtnAsignar.setIcon(new ImageIcon(new ImageIcon("imagenes/Asignar.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        BtnAsignar.setBorderPainted(false);
        BtnAsignar.setContentAreaFilled(false);
        BtnAsignar.setFocusPainted(false);

        BtnEnviar.setIcon(new ImageIcon(new ImageIcon("imagenes/Enviar.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        BtnEnviar.setBorderPainted(false);
        BtnEnviar.setContentAreaFilled(false);
        BtnEnviar.setFocusPainted(false);

        btnEnviar.setIcon(new ImageIcon(new ImageIcon("imagenes/Enviar.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setFocusPainted(false);

        btnAñadirProd.setIcon(new ImageIcon(new ImageIcon("imagenes/Añadir.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        btnAñadirProd.setBorderPainted(false);
        btnAñadirProd.setContentAreaFilled(false);
        btnAñadirProd.setFocusPainted(false);
    }

    private void cargarMesas() {
        try {
            List<Mesa> mesas = mesaDAO.listar();
            CBmesa.removeAllItems();
            CBmesa3.removeAllItems();
            for (Mesa m : mesas) {
                CBmesa.addItem(m.getIdMesa());
                CBmesa3.addItem(m.getIdMesa());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando mesas: " + e.getMessage());
        }
    }

    private void cargarEstados() {
        CBSelecEstado.addItem("Disponible");
        CBSelecEstado.addItem("Ocupada");
        CBSelecEstado.addItem("Reservada");
        CBSelecEstado.addItem("Limpieza");
    }

    private void mostrarDatosMesa() {
        try {
            int idMesa = (int) CBmesa.getSelectedItem();
            Mesa m = mesaDAO.buscarPorId(idMesa);
            lblEstado.setText(m.getEstado());
            cargarPedidosMesa(idMesa);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mostrando datos: " + e.getMessage());
        }
    }

    private void cambiarEstadoMesa() {
        try {
            int idMesa = (int) CBmesa.getSelectedItem();
            String nuevoEstado = (String) CBSelecEstado.getSelectedItem();
            Mesa m = mesaDAO.buscarPorId(idMesa);
            m.setEstado(nuevoEstado);
            mesaDAO.actualizar(m);
            lblEstado.setText(nuevoEstado);
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error actualizando estado: " + e.getMessage());
        }
    }

    private void abrirCuenta() {
        try {
            int idMesa = (int) CBmesa.getSelectedItem();
            if (cuentaDAO.tieneCuentaAbierta(idMesa)) {
                JOptionPane.showMessageDialog(this, "La mesa ya tiene una cuenta abierta.");
                return;
            }
            Cuenta nuevaCuenta = new Cuenta(idMesa, 1);
            cuentaDAO.insertar(nuevaCuenta);
            JOptionPane.showMessageDialog(this, "Cuenta abierta para la mesa " + idMesa);
            cargarPedidosMesa(idMesa);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al abrir cuenta: " + e.getMessage());
        }
    }

    private void cerrarCuenta() {
        try {
            int idMesa = (int) CBmesa.getSelectedItem();
            if (!cuentaDAO.tieneCuentaAbierta(idMesa)) {
                JOptionPane.showMessageDialog(this, "No hay cuenta abierta para esta mesa.");
                return;
            }
            cuentaDAO.cerrarCuenta(idMesa);
            JOptionPane.showMessageDialog(this, "Cuenta cerrada correctamente.");
            cargarPedidosMesa(idMesa);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cerrar cuenta: " + e.getMessage());
        }
    }

    private void cargarPedidosMesa(int idMesa) {
        try {
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID Pedido", "Producto", "Cantidad", "Fecha"}, 0);
            int idCuenta = cuentaDAO.obtenerIdCuentaAbierta(idMesa);
            if (idCuenta == -1) {
                TBPedidosMesa.setModel(model);
                return;
            }
            List<Pedido> pedidos = pedidoDAO.listarPorCuenta(idCuenta);
            for (Pedido p : pedidos) {
                model.addRow(new Object[]{p.getIdPedido(), p.getIdProducto(), p.getCantidad(), p.getFechaHora()});
            }
            TBPedidosMesa.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando pedidos: " + e.getMessage());
        }
    }

    private void abrirDialogoAgregarPedido() {
        DialogoAgregarPedido dialog = new DialogoAgregarPedido(this, TBProductosMesa, txtSubtotal);
        dialog.setVisible(true);
    }

    private void enviarPedidos() {
        DefaultTableModel model = (DefaultTableModel) TBProductosMesa.getModel();
        int filas = model.getRowCount();

        if (filas == 0) {
            JOptionPane.showMessageDialog(this, "No hay productos para enviar.");
            return;
        }

        int idMesa = (int) CBmesa3.getSelectedItem();
        int idCuenta;
        try {
            idCuenta = cuentaDAO.obtenerIdCuentaAbierta(idMesa);
            if (idCuenta == -1) {
                JOptionPane.showMessageDialog(this, "No hay cuenta abierta para esta mesa.");
                return;
            }

            for (int i = 0; i < filas; i++) {
                Object valorId = model.getValueAt(i, 0);
                Object valorCantidad = model.getValueAt(i, 2);

                // Validar que los valores existan
                if (valorId == null || valorCantidad == null) continue;

                int idProducto = Integer.parseInt(valorId.toString());
                int cantidad = Integer.parseInt(valorCantidad.toString());

                pedidoDAO.agregarPedido(idCuenta, idProducto, cantidad);
            }

            JOptionPane.showMessageDialog(this, "Pedidos enviados correctamente.");

            // Limpiar tabla y subtotal
            model.setRowCount(0);
            txtSubtotal.setText("0");

            // Actualizar tabla de pedidos en FormMesa
            cargarPedidosMesa(idMesa);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error enviando pedidos: " + e.getMessage());
        }
    }

    private void eliminarProductoSeleccionado() {
        int filaSeleccionada = TBProductosMesa.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) TBProductosMesa.getModel();

        // Obtener subtotal actual y valor del producto eliminado
        try {
            double subtotalActual = Double.parseDouble(txtSubtotal.getText());
            Object valorSubtotalProd = model.getValueAt(filaSeleccionada, 3);

            double subtotalProd = 0;
            if (valorSubtotalProd != null && !valorSubtotalProd.toString().isEmpty()) {
                subtotalProd = Double.parseDouble(valorSubtotalProd.toString());
            }

            // Actualizar subtotal
            double nuevoSubtotal = subtotalActual - subtotalProd;
            if (nuevoSubtotal < 0) nuevoSubtotal = 0;

            txtSubtotal.setText(String.valueOf(nuevoSubtotal));

            // Eliminar fila
            model.removeRow(filaSeleccionada);

            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al calcular subtotal: " + e.getMessage());
        }
    }
    public static void mostrarPantallaCompleta(JFrame ventana) {
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza
        ventana.setUndecorated(true); // Quita bordes y barra de título
        ventana.setVisible(true); // Muestra la ventana
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FormMesa form = new FormMesa();
            form.setVisible(true);
            mostrarPantallaCompleta(form);

        });
    }
}
