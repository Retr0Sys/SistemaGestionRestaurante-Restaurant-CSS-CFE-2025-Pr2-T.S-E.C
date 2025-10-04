package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Facturacion extends JFrame {
    public JPanel ventanaFact;
    private JComboBox<String> cboxMesa;
    private JLabel lblSubtotal;
    private JLabel lblSubtotalNumero;
    private JLabel lblPropina;
    private JComboBox<String> cboxPropina;
    private JLabel lblMetPago;
    private JComboBox<String> cboxMetPago;
    private JLabel lblTotal;
    private JLabel lblTotalNum;
    private JButton btnComprobante;
    private JLabel lblFact;
    private JLabel lblMesa;
    private JLabel lblDescuento;
    private JLabel lblComoarreglamosestoJPG;
    private JButton btnAtras;

    private double subtotal = 1000;
    private static int contadorFactura = 1;

    public Facturacion() {
        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);

        ImageIcon imagen2 = new ImageIcon(new ImageIcon("imagenes/Comprobante.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnComprobante.setIcon(imagen2);
        btnComprobante.setBorderPainted(false);
        btnComprobante.setContentAreaFilled(false);
        btnComprobante.setFocusPainted(false);

        setTitle("Facturación");
        lblFact.setFont(new Font("Arial", Font.BOLD, 18));

        cboxMesa.addItem("Mesa 1");
        cboxMesa.addItem("Mesa 2");
        cboxMesa.addItem("Mesa 3");
        cboxMesa.addItem("Mesa 4");

        cboxPropina.addItem("0%");
        cboxPropina.addItem("5%");
        cboxPropina.addItem("10%");
        cboxPropina.addItem("15%");

        cboxMetPago.addItem("Efectivo");
        cboxMetPago.addItem("Tarjeta de débito");
        cboxMetPago.addItem("Tarjeta de crédito");
        cboxMetPago.addItem("Transferencia");

        lblSubtotalNumero.setText("$ " + subtotal);
        cboxPropina.addActionListener(e -> actualizarTotal());

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

            crearPDF(mesa, propina, metodoPago, total);
        });

        btnAtras.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaFact);
            topFrame.dispose();
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });
    }

    private void crearPDF(String mesa, String propina, String metodoPago, String total) {
        try {
            PDDocument documento = new PDDocument();
            PDPage pagina = new PDPage(PDRectangle.LETTER);
            documento.addPage(pagina);
            PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

            float margenIzq = 50;
            float anchoPagina = pagina.getMediaBox().getWidth();
            float altoPagina = pagina.getMediaBox().getHeight();
            float y = altoPagina - 50;

            // ▪ Barras decorativas laterales
            contenido.setNonStrokingColor(new Color(240, 240, 255));
            contenido.addRect(20, 0, 10, altoPagina); // izquierda
            contenido.fill();
            contenido.addRect(anchoPagina - 30, 0, 10, altoPagina); // derecha
            contenido.fill();


            // ▪ Título centrado
            contenido.setNonStrokingColor(Color.BLACK);
            String titulo = "Factura N° " + contadorFactura;
            float tituloWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(titulo) / 1000 * 18;
            float tituloX = (anchoPagina - tituloWidth) / 2;

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 18);
            contenido.newLineAtOffset(tituloX, y);
            contenido.showText(titulo);
            contenido.endText();

            y -= 20;

            // ▪ Línea decorativa debajo del título
            contenido.setStrokingColor(new Color(180, 180, 220));
            contenido.setLineWidth(1.5f);
            contenido.moveTo(margenIzq, y);
            contenido.lineTo(anchoPagina - margenIzq, y);
            contenido.stroke();

            y -= 30;

            // ▪ Mesa y Mesero alineados a la izquierda
            contenido.setNonStrokingColor(Color.BLACK);
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA, 12);
            contenido.newLineAtOffset(margenIzq, y);
            contenido.showText("Mesa: " + mesa);
            contenido.newLineAtOffset(0, -18);
            contenido.showText("Mesero: EJEMPLO");
            contenido.endText();

            y -= 60;

            // ▪ Tabla de pedidos centrada
            float rowHeight = 20;
            float[] colWidths = {200, 100, 100};
            float tableWidth = 0;
            for (float w : colWidths) tableWidth += w;
            float tableX = (anchoPagina - tableWidth) / 2;
            float tableY = y;

            String[][] datos = {
                    {"Producto", "Cantidad", "Precio"},
                    {"Hamburguesa", "2", "$500"},
                    {"Refresco", "2", "$200"},
                    {"Postre", "1", "$300"}
            };

            contenido.setStrokingColor(Color.BLACK);
            contenido.setLineWidth(1f);

            for (int i = 0; i < datos.length; i++) {
                for (int j = 0; j < colWidths.length; j++) {
                    float cellX = tableX + sum(colWidths, j);
                    float cellY = tableY - i * rowHeight;

                    contenido.addRect(cellX, cellY, colWidths[j], rowHeight);
                    contenido.beginText();
                    contenido.setFont(PDType1Font.HELVETICA, 10);
                    contenido.newLineAtOffset(cellX + 5, cellY + 5);
                    contenido.showText(datos[i][j]);
                    contenido.endText();
                }
            }

            contenido.stroke();

            // ▪ Campos de facturación alineados a la izquierda
            y = tableY - datos.length * rowHeight - 40;
            String[] campos = {
                    "Subtotal: $" + subtotal,
                    "Propina: " + propina,
                    "Método de pago: " + metodoPago,
                    "Total: " + total
            };

            contenido.setNonStrokingColor(Color.BLACK);
            contenido.setFont(PDType1Font.HELVETICA, 12);
            for (String campo : campos) {
                contenido.beginText();
                contenido.newLineAtOffset(margenIzq, y);
                contenido.showText(campo);
                contenido.endText();
                y -= 18;
            }

            // ▪ Mensajes finales al pie del PDF
            float pieY = 60;
            String mensaje1 = "Comprobante vía cliente aprobado por DGI habilitado y en regla, vencimiento: 31/12/2025";
            String mensaje2 = "Comprobante DGI N°1000 a N°35000";
            String mensaje3 = "Restaurante CSS";

            contenido.setNonStrokingColor(Color.BLACK);

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            contenido.newLineAtOffset((anchoPagina - PDType1Font.HELVETICA_OBLIQUE.getStringWidth(mensaje1) / 1000 * 10) / 2, pieY);
            contenido.showText(mensaje1);
            contenido.endText();

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenido.newLineAtOffset((anchoPagina - PDType1Font.HELVETICA_BOLD.getStringWidth(mensaje2) / 1000 * 12) / 2, pieY - 15);
            contenido.showText(mensaje2);
            contenido.endText();

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenido.newLineAtOffset((anchoPagina - PDType1Font.HELVETICA_BOLD.getStringWidth(mensaje3) / 1000 * 12) / 2, pieY - 30);
            contenido.showText(mensaje3);
            contenido.endText();

            // ▪ Finalizar y guardar
            contenido.close();
            documento.save("src/ProgramaPrincipal/Facturas/Factura_" + contadorFactura + ".pdf");
            documento.close();
            contadorFactura++;

        } catch (Exception x) {
            System.out.println("error: " + x.getMessage());
        }
    }

    private float sum(float[] arr, int hasta) {
        float total = 0;
        for (int i = 0; i < hasta; i++) total += arr[i];
        return total;
    }

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
        ventana.setContentPane(ventana.ventanaFact);
        ventana.setBounds(300, 200, 500, 300);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}