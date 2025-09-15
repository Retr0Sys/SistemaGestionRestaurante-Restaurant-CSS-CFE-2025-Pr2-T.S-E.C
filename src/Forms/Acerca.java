package Forms;

import javax.swing.*;
import java.awt.*;

public class Acerca extends  JFrame {
    public JPanel JPacerca;
    private JLabel lblCreadores;
    private JLabel lblVersion;
    private JLabel lblCopy;
    private JButton btnAtras;

    public Acerca(){

        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);

        //Botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPacerca);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Acerca acercaDe = new Acerca();
        acercaDe.setContentPane(acercaDe.JPacerca);
        acercaDe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        acercaDe.setLocationRelativeTo(null);
        acercaDe.pack();
        acercaDe.setVisible(true);
    }
}
