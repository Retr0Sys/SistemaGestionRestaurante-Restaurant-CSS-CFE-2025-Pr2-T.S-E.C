package Forms;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import DAO.CuentaDAO;
import DAO.MesaDAO;
import DAO.PedidoDAO;
import DAO.ConexionDB;
import Clases.concret.Mesa;
import Clases.concret.Pedido;

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
    private JLabel lblDescuento;
    private JComboBox<String> cboxDescuento;
    private JLabel lblMetPago;
    private JComboBox<String> cboxMetPago;
    private JLabel lblTotal;
    private JLabel lblTotalNum;
    private JButton btnComprobante;
    private JLabel lblFact;
    private JLabel lblMesa;
    private JButton btnAtras;

    private double subtotal = 0;
    private static int contadorFactura = 1;

    public Facturacion() {
        // Configuración de botones
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

        // Cargar mesas desde BD
        cargarMesasDesdeBD();

        // Cargar propinas
        cboxDescuento.addItem("0%");
        cboxDescuento.addItem("5%");
        cboxDescuento.addItem("10%");
        cboxDescuento.addItem("15%");

        // Métodos de pago
        cboxMetPago.addItem("Efectivo");
        cboxMetPago.addItem("Tarjeta de débito");
        cboxMetPago.addItem("Tarjeta de crédito");
        cboxMetPago.addItem("Transferencia");

        lblSubtotalNumero.setText("$ " + subtotal);

        // Actualizar total cuando cambia la propina
        cboxDescuento.addActionListener(e -> actualizarTotal());

        // Cargar pedidos al seleccionar mesa
        cboxMesa.addActionListener(e -> {
            if (cboxMesa.getSelectedItem() != null) {
                cargarPedidosDeMesa();
            }
        });

        // Botón generar comprobante
        btnComprobante.addActionListener(e -> {
            String mesa = (String) cboxMesa.getSelectedItem();
            String propina = (String) cboxDescuento.getSelectedItem();
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

        // Botón atrás
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

    // ====================== Cargar mesas desde BD ======================
    private void cargarMesasDesdeBD() {
        try {
            MesaDAO mesaDAO = new MesaDAO();
            cboxMesa.removeAllItems();
            for (Mesa m : mesaDAO.listar()) {
                cboxMesa.addItem("Mesa " + m.getIdMesa());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando mesas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ====================== Cargar pedidos de la mesa seleccionada ======================
    private void cargarPedidosDeMesa() {
        try {
            String seleccion = (String) cboxMesa.getSelectedItem();
            if (seleccion == null || seleccion.isEmpty()) return;

            int idMesa = Integer.parseInt(seleccion.replace("Mesa ", "").trim());

            // Buscar cuenta activa de la mesa
            CuentaDAO cuentaDAO = new CuentaDAO();
            int idCuenta = cuentaDAO.obtenerIdCuentaAbierta(idMesa);

            if (idCuenta == -1) {
                JOptionPane.showMessageDialog(this, "La mesa no tiene una cuenta activa.");
                lblSubtotalNumero.setText("$ 0");
                lblTotalNum.setText("$ 0");
                subtotal = 0;
                return;
            }

            // Listar pedidos de esa cuenta
            List<Pedido> pedidos = PedidoDAO.listarPorCuenta(idCuenta);

            double nuevoSubtotal = 0;
            for (Pedido p : pedidos) {
                double precio = obtenerPrecioProducto(p.getIdProducto()); // p.getIdProducto() = IdCatalogoProducto
                nuevoSubtotal += precio * p.getCantidad();
            }

            subtotal = nuevoSubtotal;
            lblSubtotalNumero.setText("$ " + subtotal);
            actualizarTotal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ====================== Obtener precio del producto ======================
    private double obtenerPrecioProducto(int idCatalogoProducto) {
        double precio = 0;
        try {
            String sql = "SELECT precio FROM catalogoproducto WHERE IdCatalogoProducto = ?";
            try (java.sql.Connection con = ConexionDB.getConnection();
                 java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idCatalogoProducto);
                try (java.sql.ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        precio = rs.getDouble("precio");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error obteniendo precio: " + e.getMessage());
        }
        return precio;
    }

    // ====================== Crear PDF ======================
    private void crearPDF(String mesa, String propina, String metodoPago, String total) {
        try {
            PDDocument documento = new PDDocument();
            PDPage pagina = new PDPage(PDRectangle.LETTER);
            documento.addPage(pagina);
            PDPageContentStream contenido = new PDPageContentStream(documento, pagina);

            float anchoPagina = pagina.getMediaBox().getWidth();
            float altoPagina = pagina.getMediaBox().getHeight();
            float margenIzq = 50;
            float y = altoPagina - 50;

            // Barras laterales y logo
            contenido.setNonStrokingColor(new Color(240, 240, 255));
            contenido.addRect(20, 0, 10, altoPagina); contenido.fill();
            contenido.addRect(anchoPagina - 30, 0, 10, altoPagina); contenido.fill();

            PDImageXObject logo = PDImageXObject.createFromFile("imagenes/CSSLogoENPNG.png", documento);
            contenido.drawImage(logo, margenIzq, y - 110, 130, 130);

            // Información del local
            float infoX = anchoPagina - 200;
            float infoY = y - 10;
            String[] info = {"RestaurantesCSS.SA", "Uruguay - 1020",
                    "RUT: 22.343542.001-6", "Telf: 47642135", "Móvil: +59892533006"};
            contenido.setNonStrokingColor(Color.BLACK);
            contenido.setFont(PDType1Font.HELVETICA, 10);
            for (String linea : info) {
                contenido.beginText();
                contenido.newLineAtOffset(infoX, infoY);
                contenido.showText(linea);
                contenido.endText();
                infoY -= 12;
            }

            // e-Ticket
            float ticketY = y - 150;
            String ticket = "e - Ticket A - " + contadorFactura;
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contenido.newLineAtOffset(margenIzq, ticketY);
            contenido.showText(ticket);
            contenido.endText();

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA, 10);
            contenido.newLineAtOffset(margenIzq, ticketY - 15);
            contenido.showText("moneda: UYU");
            contenido.endText();

            // Método de pago y fecha
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA, 10);
            contenido.newLineAtOffset(anchoPagina - 200, ticketY);
            contenido.showText("Método de pago: " + metodoPago);
            contenido.endText();

            String fechaHora = new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
            contenido.beginText();
            contenido.newLineAtOffset(anchoPagina - 200, ticketY - 15);
            contenido.showText("Fecha: " + fechaHora);
            contenido.endText();

            // Línea divisoria
            y = ticketY - 30;
            contenido.setStrokingColor(new Color(180, 180, 220));
            contenido.setLineWidth(1f);
            contenido.moveTo(margenIzq, y);
            contenido.lineTo(anchoPagina - margenIzq, y);
            contenido.stroke();
            y -= 30;

            // Mesa
            String mesaTexto = mesa;
            float mesaTextoWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(mesaTexto) / 1000 * 14;
            float mesaTextoX = (anchoPagina - mesaTextoWidth) / 2;
            contenido.setNonStrokingColor(new Color(220, 220, 240));
            contenido.addRect(mesaTextoX - 10, y - 5, mesaTextoWidth + 20, 20);
            contenido.fill();
            contenido.setNonStrokingColor(Color.BLACK);
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contenido.newLineAtOffset(mesaTextoX, y);
            contenido.showText(mesaTexto);
            contenido.endText();

            // Línea divisoria debajo de mesa
            y -= 25;
            contenido.setStrokingColor(new Color(180, 180, 220));
            contenido.setLineWidth(1f);
            contenido.moveTo(margenIzq, y);
            contenido.lineTo(anchoPagina - margenIzq, y);
            contenido.stroke();
            y -= 25;

            // Título "Consumo Final"
            String titulo = "Consumo Final";
            float tituloWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(titulo) / 1000 * 14;
            float tituloX = (anchoPagina - tituloWidth) / 2;
            contenido.setNonStrokingColor(new Color(180, 180, 220));
            contenido.addRect(tituloX - 10, y - 5, tituloWidth + 20, 20);
            contenido.fill();
            contenido.setNonStrokingColor(Color.BLACK);
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contenido.newLineAtOffset(tituloX, y);
            contenido.showText(titulo);
            contenido.endText();

            // Espacio entre título y tabla
            y -= 40;

            // Tabla de pedidos reales
            float rowHeight = 20;
            float[] colWidths = {200, 100, 100};
            float tableWidth = colWidths[0] + colWidths[1] + colWidths[2];
            float tableX = (anchoPagina - tableWidth) / 2;
            float tableY = y;

            // Obtener pedidos de la mesa
            String seleccionMesa = (String) cboxMesa.getSelectedItem();
            int idMesa = Integer.parseInt(seleccionMesa.replace("Mesa ", "").trim());
            CuentaDAO cuentaDAO = new CuentaDAO();
            int idCuenta = cuentaDAO.obtenerIdCuentaAbierta(idMesa);
            List<Pedido> pedidos = PedidoDAO.listarPorCuenta(idCuenta);

            String[][] datos = new String[pedidos.size() + 1][3];
            datos[0] = new String[]{"Producto", "Cantidad", "Precio"};
            for (int i = 0; i < pedidos.size(); i++) {
                Pedido p = pedidos.get(i);
                String nombreProd = p.getNombreProducto(); // asegurarse de que Pedido tenga getNombreProducto()
                int cant = p.getCantidad();
                double precio = obtenerPrecioProducto(p.getIdProducto());
                datos[i + 1] = new String[]{nombreProd, String.valueOf(cant), "$" + precio};
            }

            // Dibujar tabla
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

            // Línea divisoria debajo de tabla
            y = tableY - datos.length * rowHeight - 20;
            contenido.setStrokingColor(new Color(180, 180, 220));
            contenido.setLineWidth(1f);
            contenido.moveTo(margenIzq, y);
            contenido.lineTo(anchoPagina - margenIzq, y);
            contenido.stroke();
            y -= 20;

            // Facturación en dos columnas
            String[] etiquetas = {"Subtotal:", "IVA:", "Propina:", "Total:"};
            double iva = subtotal * 0.22; // ejemplo 22%
            String[] valores = {"$" + subtotal, "$" + iva, propina, total};
            for (int i = 0; i < etiquetas.length; i++) {
                contenido.beginText();
                contenido.setFont(PDType1Font.HELVETICA, 12);
                contenido.newLineAtOffset(margenIzq, y);
                contenido.showText(etiquetas[i]);
                contenido.endText();

                contenido.beginText();
                contenido.newLineAtOffset(anchoPagina - margenIzq - 100, y);
                contenido.showText(valores[i]);
                contenido.endText();

                y -= 18;
            }

            // Línea divisoria debajo de facturación
            y -= 10;
            contenido.setStrokingColor(new Color(180, 180, 220));
            contenido.setLineWidth(1f);
            contenido.moveTo(margenIzq, y);
            contenido.lineTo(anchoPagina - margenIzq, y);
            contenido.stroke();
            y -= 30;

            // ======================= Bloque legal y agradecimiento =======================
            String legal1 = "Documento legal aprobado y en regla por DGI";
            String legal2 = "N° Legal de 1000 a 3500 Vencimiento 31/12/2025";

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            float legal1Width = PDType1Font.HELVETICA_OBLIQUE.getStringWidth(legal1) / 1000 * 10;
            contenido.newLineAtOffset((anchoPagina - legal1Width) / 2, 60);
            contenido.showText(legal1);
            contenido.endText();

            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            float legal2Width = PDType1Font.HELVETICA_OBLIQUE.getStringWidth(legal2) / 1000 * 10;
            contenido.newLineAtOffset((anchoPagina - legal2Width) / 2, 45);
            contenido.showText(legal2);
            contenido.endText();

            String agradecimiento = "Gracias por su preferencia, lo esperamos pronto";
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_OBLIQUE, 12);
            float agradecimientoWidth = PDType1Font.HELVETICA_OBLIQUE.getStringWidth(agradecimiento) / 1000 * 12;
            contenido.newLineAtOffset((anchoPagina - agradecimientoWidth) / 2, 30);
            contenido.showText(agradecimiento);
            contenido.endText();

            // QR
            PDImageXObject qr = PDImageXObject.createFromFile("imagenes/qr.png", documento);
            float qrSize = 130;
            float qrX = anchoPagina - margenIzq - qrSize;
            contenido.drawImage(qr, qrX, 30, qrSize, qrSize);

            String ivaTexto = "IVA al día";
            contenido.beginText();
            contenido.setFont(PDType1Font.HELVETICA_OBLIQUE, 10);
            float ivaTextoWidth = PDType1Font.HELVETICA_OBLIQUE.getStringWidth(ivaTexto) / 1000 * 10;
            contenido.newLineAtOffset(qrX + (qrSize - ivaTextoWidth) / 2, 20);
            contenido.showText(ivaTexto);
            contenido.endText();

            contenido.close();
            documento.save("src/ProgramaPrincipal/Facturas/e-Ticket_A_N°_" + contadorFactura + ".pdf");
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
        String seleccion = (String) cboxDescuento.getSelectedItem();
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
