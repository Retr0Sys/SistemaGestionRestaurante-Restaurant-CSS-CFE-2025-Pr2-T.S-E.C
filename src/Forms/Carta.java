package Forms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Carta extends JFrame
{
    public JPanel ventanaCarta;
    private JLabel lblTitulo;
    JTable tblTablaCartaMenu;
    private JButton btnAtras;
    private JTabbedPane TBCarta;
    private JPanel JPCarta;
    private JPanel JPcambiarCarta;
    private JLabel lblNombProdAcambiar;
    private JLabel lblNuevoPrecio;
    private JLabel lblNuevoCategoria;
    private JLabel lblNuevoDisp;
    private JLabel lblCambiarDisp;
    private JComboBox CBnuevaDisp;
    private JComboBox CBNuevoNombre;
    private JTextField txtNuevoPrecio;
    private JComboBox CBNuevaCat;
    private JComboBox CBNuevaDisp;
    private JLabel lblTituloCarta;


    //*Imporante* Agregar el TABBEDPANE a el Jpanel principal porque no se ve por pantalla.
    public Carta()
    {
        setTitle("Carta del Restaurante");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ventanaCarta = new JPanel(new BorderLayout());

        // Título
        lblTitulo = new JLabel("Carta del Restaurante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        ventanaCarta.add(lblTitulo, BorderLayout.NORTH);

        // Modelo de la tabla con columnas
        String[] columnas = {"Nombre", "Precio", "Categoría", "Disponibilidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Ejemplo de filas
        modelo.addRow(new Object[]{"Pizza Americana", 350.0, "Clases.concret.Comida", "Disponible"});
        modelo.addRow(new Object[]{"Milanesa con papas", 420.0, "Clases.concret.Comida", "Agotado"});
        modelo.addRow(new Object[]{"Coca-Cola 500ml", 120.0, "Clases.concret.Bebida", "Disponible"});
        modelo.addRow(new Object[]{"Flan casero", 180.0, "Clases.concret.Postre", "Disponible"});

        // Tabla SIN JScrollPane
        tblTablaCartaMenu = new JTable(modelo);
        ventanaCarta.add(tblTablaCartaMenu, BorderLayout.CENTER);


        add(ventanaCarta);

        // Botón Atrás
        btnAtras = new JButton("Atrás");
        ventanaCarta.add(btnAtras, BorderLayout.SOUTH);
        btnAtras.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCarta);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setVisible(true);
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Carta carta = new Carta();
            carta.setContentPane(carta.ventanaCarta);
            carta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            carta.pack();
            carta.setLocationRelativeTo(null);
            carta.setVisible(true);
        });
    }
}
