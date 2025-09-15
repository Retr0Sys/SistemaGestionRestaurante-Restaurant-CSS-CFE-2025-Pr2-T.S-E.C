package Forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Cocina extends JFrame {
    public JPanel ventanaCocina;
    private JLabel lblTitulo;
    private JTable tblTablaCocina;
    private JComboBox<String> cboxEstadoPedido;
    private JButton btnActualizar;
    private JLabel lblEstadoPedido;
    private JButton btnAtras;

    private DefaultTableModel modelo;

    public Cocina() {

        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);


        ImageIcon imagen2 = new ImageIcon(new ImageIcon("imagenes/Actualizar.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnActualizar.setIcon(imagen2);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setFocusPainted(false);


        setTitle("Gestión de Cocina");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalTextPosition(JLabel.CENTER);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);


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

        //Botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCocina);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Cocina ventana = new Cocina();
        ventana.setContentPane(ventana.ventanaCocina);
        ventana.setBounds(300,200,600,400);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
