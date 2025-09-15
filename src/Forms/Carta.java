package Forms;

import Clases.concret.Comida;

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
        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras = new JButton("Atras");
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);



        // Modelo de la tabla con columnas
        String[] columnas = {"Nombre", "Precio", "Categoría", "Disponibilidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Ejemplo de filas
        Comida comida1 = new Comida("Paella", 200,"Principal", "Disponible");
        Comida comida2 = new Comida("Tarta de Queso", 300, "Postre", "No Disponible");
        Comida comida3 = new Comida("Coca-Cola", 100, "Bebida", "Disponible");

        // Agregar filas al modelo
        modelo.addRow(new Object[]{comida1.getNombre(), comida1.getPrecio(), comida1.getCategoria(), comida1.getDisponibilidad()});
        modelo.addRow(new Object[]{comida2.getNombre(), comida2.getPrecio(), comida2.getCategoria(), comida2.getDisponibilidad()});
        modelo.addRow(new Object[]{comida3.getNombre(), comida3.getPrecio(), comida3.getCategoria(), comida3.getDisponibilidad()});

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
