import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Cocina extends JFrame {
    private JPanel ventana;
    private JLabel lblTitulo;
    private JTable tblTablaCocina;
    private JComboBox<String> cboxEstadoPedido;
    private JButton btnActualizar;
    private JLabel lblEstadoPedido;

    private DefaultTableModel modelo;

    public Cocina() {
        setTitle("Gestión de Cocina");

        cboxEstadoPedido.addItem("Pendiente");
        cboxEstadoPedido.addItem("En preparación");
        cboxEstadoPedido.addItem("Servido");
        cboxEstadoPedido.addItem("Cancelado");

        // Modelo de la tabla
        String[] columnas = {"Nombre", "Cantidad", "Notas", "Mesa", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);
        tblTablaCocina.setModel(modelo);

        // Ejemplo de pedidos
        modelo.addRow(new Object[]{"Pizza Margarita", 2, "Sin aceitunas", "Mesa 5", "Pendiente"});
        modelo.addRow(new Object[]{"Milanesa con papas", 1, "Bien cocida", "Mesa 3", "En preparación"});
        modelo.addRow(new Object[]{"Coca-Cola 1L", 1, "", "Mesa 2", "Servido"});
        modelo.addRow(new Object[]{"Flan casero", 3, "Con dulce de leche", "Mesa 4", "Pendiente"});

        // Acción del boton actualizar
        btnActualizar.addActionListener(e -> {
            int filaSeleccionada = tblTablaCocina.getSelectedRow();
            if (filaSeleccionada != -1) {
                String nuevoEstado = (String) cboxEstadoPedido.getSelectedItem();
                modelo.setValueAt(nuevoEstado, filaSeleccionada, 4); // Columna 4 = Estado
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla.");
            }
        });
    }

    public static void main(String[] args) {
        Cocina ventana = new Cocina();
        ventana.setContentPane(ventana.ventana);
        ventana.setBounds(300,200,600,400);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
