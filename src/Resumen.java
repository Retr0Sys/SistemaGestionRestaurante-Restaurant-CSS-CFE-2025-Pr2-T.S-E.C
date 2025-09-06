import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Resumen extends JFrame {
    private JPanel ventana;
    private JLabel lblTitulo;
    private JButton btnVentasDelDia;
    private JButton btnpedidosPorMesa;
    private JComboBox<String> cboxMesa;
    private JButton btnTop3;
    private JTable tblTabla;

    private DefaultTableModel modelo;

    public Resumen() {
        setTitle("Resumen del Restaurante");

        // modelo de tabla
        String[] columnas = {"Mesa", "Producto", "Cantidad", "Total"};
        modelo = new DefaultTableModel(columnas, 0);
        tblTabla.setModel(modelo);

        // llenar combo con mesas
        cboxMesa.addItem("Mesa 1");
        cboxMesa.addItem("Mesa 2");
        cboxMesa.addItem("Mesa 3");
        cboxMesa.addItem("Mesa 4");

        // boton ventas del dia
        btnVentasDelDia.addActionListener(e -> cargarVentasDelDia());

        // boton pedidos por mesa
        btnpedidosPorMesa.addActionListener(e -> {
            String mesaSeleccionada = (String) cboxMesa.getSelectedItem();
            if (mesaSeleccionada != null) {
                cargarPedidosPorMesa(mesaSeleccionada);
            }
        });

        // boton top 3
        btnTop3.addActionListener(e -> cargarTop3());
    }

    // ventas del dia
    private void cargarVentasDelDia() {
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{"Mesa 1", "Pizza Margarita", 2, "$500"});
        modelo.addRow(new Object[]{"Mesa 2", "Milanesa", 1, "$350"});
        modelo.addRow(new Object[]{"Mesa 3", "Refresco", 3, "$450"});
        modelo.addRow(new Object[]{"Mesa 1", "Flan casero", 2, "$300"});
        modelo.addRow(new Object[]{"Mesa 4", "Hamburguesa", 1, "$400"});
    }

    // pedidos por mesa
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

    // top 3 productos mas vendidos
    private void cargarTop3() {
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{"-", "Pizza Margarita", 10, "$2500"});
        modelo.addRow(new Object[]{"-", "Milanesa", 8, "$2800"});
        modelo.addRow(new Object[]{"-", "Refresco", 6, "$900"});
    }

    public static void main(String[] args) {
        Resumen ventana = new Resumen();
        ventana.setContentPane(ventana.ventana);
        ventana.setBounds(300,200,500,300);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
