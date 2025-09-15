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
