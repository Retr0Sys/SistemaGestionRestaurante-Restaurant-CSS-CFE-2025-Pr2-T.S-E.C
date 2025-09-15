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
