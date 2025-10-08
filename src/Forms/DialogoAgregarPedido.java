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

        setTitle("Agregar Productos");
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        tblProductos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con cantidad y botones
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout());

        panelInferior.add(new JLabel("Cantidad:"));
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelInferior.add(spnCantidad);

        btnAgregar = new JButton("Agregar");
        btnCancelar = new JButton("Cancelar");
        panelInferior.add(btnAgregar);
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

            // Cerrar diálogo
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
