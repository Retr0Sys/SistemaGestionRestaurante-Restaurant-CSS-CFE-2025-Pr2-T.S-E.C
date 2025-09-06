import javax.swing.*;

public class Facturacion extends JFrame {
    private JPanel ventana;         // del .form
    private JComboBox<String> cboxMesa;       // del .form
    private JLabel lblSubtotal;               // del .form
    private JLabel lblSubtotalNumero;         // del .form
    private JLabel lblPropina;                // del .form
    private JComboBox<String> cboxPropina;    // del .form
    private JLabel lblMetPago;                // del .form
    private JComboBox<String> cboxMetPago;    // del .form
    private JLabel lblTotal;                  // del .form
    private JLabel lblTotalNum;               // del .form
    private JButton btnComprobante;           // del .form

    private double subtotal = 1000;  // 🔹 ejemplo de subtotal fijo (se puede cambiar dinámicamente)

    public Facturacion() {
        setTitle("Facturación");


        // Cargar mesas (ejemplo)
        cboxMesa.addItem("Mesa 1");
        cboxMesa.addItem("Mesa 2");
        cboxMesa.addItem("Mesa 3");
        cboxMesa.addItem("Mesa 4");

        // Cargar propina
        cboxPropina.addItem("0%");
        cboxPropina.addItem("5%");
        cboxPropina.addItem("10%");
        cboxPropina.addItem("15%");

        // Cargar métodos de pago
        cboxMetPago.addItem("Efectivo");
        cboxMetPago.addItem("Tarjeta de débito");
        cboxMetPago.addItem("Tarjeta de crédito");
        cboxMetPago.addItem("Transferencia");

        // Mostrar subtotal inicial
        lblSubtotalNumero.setText("$ " + subtotal);

        // Listener para actualizar total al cambiar propina
        cboxPropina.addActionListener(e -> actualizarTotal());

        // Acción del botón para generar comprobante
        btnComprobante.addActionListener(e -> {
            String mesa = (String) cboxMesa.getSelectedItem();
            String propina = (String) cboxPropina.getSelectedItem();
            String metodoPago = (String) cboxMetPago.getSelectedItem();
            String total = lblTotalNum.getText();

            JOptionPane.showMessageDialog(this,
                    "📋 Comprobante\n" +
                            "Mesa: " + mesa + "\n" +
                            "Subtotal: $" + subtotal + "\n" +
                            "Propina: " + propina + "\n" +
                            "Método de pago: " + metodoPago + "\n" +
                            "Total: " + total,
                    "Factura generada",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
    // Metodo para caluclar total segun propina
    private void actualizarTotal() {
        String seleccion = (String) cboxPropina.getSelectedItem();
        int porcentaje = 0;
        if (seleccion != null) {
            if (seleccion.equals("5%")) porcentaje = 5;
            else if (seleccion.equals("10%")) porcentaje = 10;
            else if (seleccion.equals("15%")) porcentaje = 15;
        }

        double total = subtotal + (subtotal * porcentaje / 100.0);
        lblTotalNum.setText("$ " + total);
    }

    public static void main(String[] args) {
        Facturacion ventana = new Facturacion();
        ventana.setContentPane(ventana.ventana);
        ventana.setBounds(300,200,500,300);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
