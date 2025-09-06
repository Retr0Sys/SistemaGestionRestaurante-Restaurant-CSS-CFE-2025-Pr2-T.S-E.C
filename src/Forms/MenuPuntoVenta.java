package Forms;

import javax.swing.*;
import java.awt.*;

public class MenuPuntoVenta extends JFrame {
    public JPanel JPMenuPrinc;
    private JButton btnCarta;
    private JButton btnCocina;
    private JButton btnFact;
    private JButton btnMesa;
    private JButton btnResumen;
    private JButton btnAcerca;
    private JLabel lblCSS;

    public MenuPuntoVenta() {
        //Colocamos imagenes a los botones
        ImageIcon carta = new ImageIcon(new ImageIcon("imagenes/Menu.png").getImage().getScaledInstance( 80, 80, Image.SCALE_SMOOTH));
        ImageIcon cocina = new ImageIcon(new ImageIcon("imagenes/Cocina.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon factura = new ImageIcon(new ImageIcon("imagenes/Facturas.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon mesa = new ImageIcon(new ImageIcon("imagenes/Mesas.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon resumen = new ImageIcon(new ImageIcon("imagenes/Resumen.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon acerca = new ImageIcon(new ImageIcon("imagenes/Acerca.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        btnCarta.setIcon(carta);
        btnCocina.setIcon(cocina);
        btnFact.setIcon(factura);
        btnMesa.setIcon(mesa);
        btnResumen.setIcon(resumen);
        btnAcerca.setIcon(acerca);
        lblCSS.setFont(new Font("Arial", Font.BOLD, 12));



        btnCarta.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMenuPrinc);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            Carta menu = new Carta();
            menu.setContentPane(menu.ventanaCarta);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });



        btnCocina.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMenuPrinc);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            Cocina menu = new Cocina();
            menu.setContentPane(menu.ventanaCocina);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });

        btnFact.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMenuPrinc);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            Facturacion menu = new Facturacion();
            menu.setContentPane(menu.ventanaFact);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });

        btnMesa.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMenuPrinc);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            Mesa menu = new Mesa();
            menu.setContentPane(menu.JPMesasIni);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });

        btnResumen.addActionListener(e -> {
            // Cerramos la ventana de Bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMenuPrinc);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            Resumen menu = new Resumen();
            menu.setContentPane(menu.ventanaResumen);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });

        btnAcerca.addActionListener(e -> {
            // Abrimos la ventana de Acerca de
            Acerca menu = new Acerca();
            menu.setContentPane(menu.JPacerca);
            menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            menu.setLocationRelativeTo(null);
            menu.pack();
            menu.setVisible(true);
        });

    }


    public static void main(String[] args) {
        JFrame ventana = new JFrame("MenuPrincipal");
        ventana.setContentPane(new MenuPuntoVenta().JPMenuPrinc);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.pack();
        ventana.setVisible(true);
    }
}
