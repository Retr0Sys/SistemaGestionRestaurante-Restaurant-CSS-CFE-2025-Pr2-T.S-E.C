package Forms;

import Clases.abstractas.Producto;
import DAO.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DialogoAgregarPedido extends JDialog {
    private JTable tblProductos;
    private JButton btnAgregar;
    private JButton btnCancelar;
    private JTextField txtSubtotal;
    private JPanel contentPane;
    private JSpinner spnCantidad;

    public DialogoAgregarPedido(JFrame parent, JTable tablaDestino, JTextField txtSubtotalDestino) {
        super(parent, true);
        this.txtSubtotal = txtSubtotalDestino;

        // Estética general
        Color fondo = new Color(245, 245, 245);
        Color acento = new Color(255, 159, 101);
        Font fuenteGeneral = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 14);

        setTitle("Agregar Productos");
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(fondo);

        // Tabla de productos
        tblProductos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tblProductos.setFont(fuenteGeneral);
        tblProductos.setRowHeight(28);
        tblProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblProductos.getTableHeader().setBackground(acento);
        tblProductos.getTableHeader().setForeground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setBackground(fondo);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(fuenteGeneral);
        panelInferior.add(lblCantidad);

        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spnCantidad.setFont(fuenteGeneral);
        ((JSpinner.DefaultEditor) spnCantidad.getEditor()).getTextField().setColumns(3);
        panelInferior.add(spnCantidad);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(fuenteBoton);
        btnAgregar.setBackground(acento);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInferior.add(btnAgregar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(fuenteBoton);
        btnCancelar.setBackground(new Color(200, 200, 200));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInferior.add(btnCancelar);

        add(panelInferior, BorderLayout.SOUTH);

        // Cargar productos desde la base de datos
        cargarProductosDesdeDB();

        // Evento botón Agregar
        btnAgregar.addActionListener(e -> {
            int fila = tblProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.");
                return;
            }

            DefaultTableModel modelDestino = (DefaultTableModel) tablaDestino.getModel();

            int idProducto = (int) tblProductos.getValueAt(fila, 0);
            String nombre = tblProductos.getValueAt(fila, 1).toString();
            double precio = Double.parseDouble(tblProductos.getValueAt(fila, 2).toString());
            int cantidad = (Integer) spnCantidad.getValue();

            modelDestino.addRow(new Object[]{idProducto, nombre, cantidad, precio * cantidad});

            // Actualizar subtotal
            double subtotalActual = Double.parseDouble(this.txtSubtotal.getText());
            subtotalActual += precio * cantidad;
            this.txtSubtotal.setText(String.valueOf(subtotalActual));

            dispose();
        });

        // Evento botón Cancelar
        btnCancelar.addActionListener(e -> dispose());
    }


    private void cargarProductosDesdeDB() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio"}, 0);
        try {
            List<Producto> productos = ProductoDAO.listarDisponibles();
            for (Producto p : productos) {
                model.addRow(new Object[]{p.getId(), p.getNombre(), p.getPrecio()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }
        tblProductos.setModel(model);
    }
}