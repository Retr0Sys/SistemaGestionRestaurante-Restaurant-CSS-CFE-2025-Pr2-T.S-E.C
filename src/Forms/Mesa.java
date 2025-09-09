package Forms;

import Clases.concret.Pedido;
import Clases.concret.mesa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JButton btnCambiarEstado;
    private Carta carta = new Carta(); // Instancia de la carta

    public Mesa() {
        //Formateamos texto
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


        //Cargamos datos iniciales
        List<mesa> mesas = new ArrayList<>();
        mesas.add(new mesa(1, "libre", "Juan"));
        mesas.add(new mesa(2, "ocupada", "Maria"));
        mesas.add(new mesa(3, "reservada", "Carlos"));
        mesas.add(new mesa(4, "Limpieza", "Ana"));
        mesas.add(new mesa(5, "ocupada", "Luis"));
        mesas.add(new mesa(6, "libre", "Sofia"));
        mesas.add(new mesa(7, "reservada", "Miguel"));
        mesas.add(new mesa(8, "libre", "Elena"));
        mesas.add(new mesa(8, "ocupada", "Diego"));
        mesas.add(new mesa(9, "Limpieza", "Laura"));

        //Creamos modleo comboBox
        DefaultComboBoxModel cbxMesa = new DefaultComboBoxModel();
        DefaultComboBoxModel cbxMesa2 = new DefaultComboBoxModel();
        DefaultComboBoxModel cbxMesa3 = new DefaultComboBoxModel();
        DefaultComboBoxModel cbxMesero = new DefaultComboBoxModel();
        DefaultComboBoxModel cbxEstado = new DefaultComboBoxModel();
        //Seteamos el modelo a nuestro comboBox
        CBmesa.setModel(cbxMesa);
        CBmesa2.setModel(cbxMesa2);
        CBmesa3.setModel(cbxMesa3);
        CBMesero.setModel(cbxMesero);
        CBSelecEstado.setModel(cbxEstado);

        cbxMesa.removeAllElements();
        for (mesa m : mesas) {
            cbxMesa.addElement(m.getNumero());
        }

        cbxMesa2.removeAllElements();
        for (mesa m : mesas) {
            cbxMesa2.addElement(m.getNumero());
        }

        cbxMesa3.removeAllElements();
        for (mesa m : mesas) {
            cbxMesa3.addElement(m.getNumero());
        }

        cbxEstado.removeAllElements();
        for(mesa m : mesas){
            cbxEstado.addElement(m.getEstado());
        }

        cbxMesero.removeAllElements();
        for (mesa m : mesas) {
            cbxMesero.addElement(m.getMesero() != null ? m.getMesero() : "");
        }


        int indiceSeleccionado = CBmesa.getSelectedIndex();
        if (indiceSeleccionado >= 0) {
            mesa mesaSeleccionada = mesas.get(indiceSeleccionado);
            CBmesa.setSelectedItem(mesaSeleccionada.getNumero());

            String estadoTexto = "";
            if(mesaSeleccionada.getEstado().equals("libre")){
                estadoTexto = "Libre";
            } else if (mesaSeleccionada.getEstado().equals("ocupada")) {
                estadoTexto = "Ocupada";
            } else if (mesaSeleccionada.getEstado().equals("reservada")) {
                estadoTexto = "Reservada";
            } else if (mesaSeleccionada.getEstado().equals("Limpieza")) {
                estadoTexto = "Limpieza";
            }
            lblEstadoFin.setText(estadoTexto);

            String mesero = mesaSeleccionada.getMesero();
            CBMesero.setSelectedItem(mesero != null ? mesero : "");
        }
        btnCambiarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indiceSeleccionado = CBmesa.getSelectedIndex();
                if (indiceSeleccionado >= 0) {
                    mesa mesaSeleccionada = mesas.get(indiceSeleccionado);
                    CBmesa.setSelectedItem(mesaSeleccionada.getNumero());
                    String estadoact = mesaSeleccionada.getEstado();
                    String nuevoEstado = CBSelecEstado.getSelectedItem().toString();
                    mesaSeleccionada.estado = nuevoEstado;
                    lblEstadoFin.setText(nuevoEstado);
                    JOptionPane.showMessageDialog(null, "Estado de la mesa " + mesaSeleccionada.getNumero() + " cambiado a " + nuevoEstado);
                }
            }
        });
        CBmesa.addActionListener(e -> {
            int index = CBmesa.getSelectedIndex();
            if (index >= 0) {
                int numeroMesa = (int) CBmesa.getSelectedItem(); // Obtener número de mesa
                List<Pedido> pedidos = pedidosMesa.getOrDefault(numeroMesa, new ArrayList<>());

                DefaultTableModel model = (DefaultTableModel) TBPedidosMesa.getModel();
                model.setRowCount(0); // Limpiar tabla

                for (Pedido p : pedidos) {
                    model.addRow(new Object[]{p.getNombre(), p.getPrecio(), p.getCategoria(), p.getCantidad()});
                }
            }
        });
    }

    public void tomarPedido() {
        txtCantidad.setEnabled(false);
        txtSubtotal.setEnabled(false);

        int numMesa = (int) CBmesa3.getSelectedItem(); // Obtener número de mesa
        String tipoOp = RDBaniadir.isSelected() ? "Añadir" : "Eliminar";

        if (tipoOp.equals("Añadir")) {
            txtCantidad.setEnabled(true);
            txtSubtotal.setEnabled(true);

            carta.setVisible(true); // Mostrar ventana de carta

            carta.tblTablaCartaMenu.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int filaSeleccionada = carta.tblTablaCartaMenu.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String nombre = carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 0).toString();
                        double precio = Double.parseDouble(carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 1).toString());
                        String categoria = carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 2).toString();
                        String disponibilidad = carta.tblTablaCartaMenu.getValueAt(filaSeleccionada, 3).toString();

                        if (disponibilidad.equals("Disponible")) {
                            Pedido nuevoPedido = new Pedido(nombre, precio, categoria, 1); // cantidad por defecto 1
                            pedidosMesa.computeIfAbsent(numMesa, k -> new ArrayList<>()).add(nuevoPedido);

                            // Actualizar tabla TBProd2
                            actualizarTablaPedidos(numMesa);

                            // Actualizar campos visuales
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
            actualizarTablaPedidos(numMesa);
        }


        if (tipoOp.equals("Eliminar")) {
            Object[][] pedidosMesa = obtenerPedidosDeMesa(numMesa);
            String[] columnas = {"Nombre", "Precio", "Categoría", "Cantidad"};
            DefaultTableModel modelo = new DefaultTableModel(pedidosMesa, columnas);
            TBProd2.setModel(modelo);
        }
    }

    // Mapa para almacenar pedidos por mesa
    private Map<Integer, List<Pedido>> pedidosMesa = new HashMap<>();

    // Método para obtener pedidos de una mesa específica
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

    private void actualizarTablaPedidos(int numMesa) {
        List<Pedido> pedidos = pedidosMesa.getOrDefault(numMesa, new ArrayList<>());
        String[] columnas = {"Nombre", "Precio", "Categoría", "Cantidad"};
        Object[][] datos = new Object[pedidos.size()][4];

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            datos[i][0] = p.getNombre();
            datos[i][1] = p.getPrecio();
            datos[i][2] = p.getCategoria();
            datos[i][3] = p.getCantidad();
        }

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        TBProd2.setModel(modelo);
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
