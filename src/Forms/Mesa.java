package Forms;

import Clases.concret.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mesa extends  JFrame {
    public JPanel JPMesasIni;
    private JTabbedPane JPPedidos;
    private JPanel JPprincipal;
    private JPanel JPasignar;
    private JPanel JPpedidos;
    private JSplitPane SplitPrincipal;
    private JPanel JParribaPri;
    private JPanel JPabajoPri;
    private JScrollPane ScrollAbajoPri;
    private JLabel lblMesa;
    private JLabel lblSelecEstado;
    private JLabel lblEstado;
    private JTable TBPedidosMesa;
    private JComboBox CBmesa;
    private JComboBox CBSelecEstado;
    private JLabel lblMesero;
    private JComboBox CBMesero;
    private JLabel lblMesa2;
    private JButton BtnAsignar;
    private JLabel lblMesa3;
    private JComboBox CBmesa3;
    private JComboBox CBmesa2;
    private JLabel lblOperacion;
    private JLabel lblMesas;
    private JTable TBProd2;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JLabel lblSubtotal;
    private JTextField txtSubtotal;
    private JButton BtnEnviar;
    private JLabel lblPedidosMesaPedidos;
    private JLabel lblPedidoMesa;
    private JRadioButton RDBaniadir;
    private JRadioButton RDBeliminar;
    private JLabel lblEstadoFin;
    private JButton btnAtras;
    private Carta carta = new Carta(); // Instancia de la carta

    public Mesa(){

        lblMesas.setFont(new Font("Arial", Font.BOLD, 18));

        //Botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMesasIni);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setVisible(true);
        });
        CBmesa.addKeyListener(new KeyAdapter() {
        });
    }

    private Map<Integer, List<Pedido>> pedidosMesa = new HashMap<>();


    private Object[][] obtenerPedidosDeMesa(int numMesa) {
        List<Pedido> pedidos = pedidosMesa.getOrDefault(numMesa, new ArrayList<>());
        Object[][] datos = new Object[pedidos.size()][4];
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            datos[i][0] = p.getNombre();
            datos[i][1] = p.getPrecio();
            datos[i][2] = p.getCategoria();
            datos[i][3] = p.getCantidad();
        }
        return datos;
    }

    public void tomarPedido() {
        txtCantidad.setEnabled(false);
        txtSubtotal.setEnabled(false);
        int numMesa = CBmesa3.getSelectedIndex();
        String tipoOp = RDBaniadir.isSelected() ? "Añadir" : "Eliminar";

        if (tipoOp.equals("Añadir")) {
            txtCantidad.setEnabled(true);
            txtSubtotal.setEnabled(true);

            // Mostrar la ventana de la carta
            carta.setVisible(true);

            // Agregar listener para capturar selección de producto
            carta.tblTablaCartaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int filaSeleccionada = carta.tblTablaCartaMenu.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombre = carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 0).toString();
                        double precio = Double.parseDouble(carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 1).toString());
                        String categoria = carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 2).toString();
                        String disponibilidad = carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 3).toString();

                        if (disponibilidad.equals("Disponible")) {
                            // Crear pedido y agregarlo a la mesa
                            Pedido nuevoPedido = new Pedido(nombre, precio, categoria, 1); // cantidad por defecto 1
                            pedidosMesa.computeIfAbsent(numMesa, k -> new ArrayList<>()).add(nuevoPedido);

                            // Actualizar tabla TBProd2
                            Object[][] pedidosActualizados = obtenerPedidosDeMesa(numMesa);
                            String[] columnas = {"Nombre", "Precio", "Categoría", "Cantidad"};
                            DefaultTableModel modelo = new DefaultTableModel(pedidosActualizados, columnas);
                            TBProd2.setModel(modelo);

                            // Calcular subtotal
                            txtCantidad.setText("1");
                            txtSubtotal.setText(String.valueOf(precio));
                        } else {
                            JOptionPane.showMessageDialog(null, "Producto no disponible.");
                        }
                    }
                }
            });
        }

        if (tipoOp.equals("Eliminar")) {
            Object[][] pedidosMesa = obtenerPedidosDeMesa(numMesa);
            String[] columnas = {"Nombre", "Precio", "Categoría", "Cantidad"};
            DefaultTableModel modelo = new DefaultTableModel(pedidosMesa, columnas);
            TBProd2.setModel(modelo);
        }
    }



    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        mesa.setContentPane(mesa.JPMesasIni);
        mesa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mesa.setLocationRelativeTo(null);
        mesa.pack();
        mesa.setVisible(true);
    }
}
