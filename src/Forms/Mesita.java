package Forms;

import javax.swing.*;
import java.awt.*;

public class Mesita {
    private JPanel panelMesita;
    private JPanel JPDentroScroll;
    private JButton btnMesa1;
    private JButton btnMesa2;
    private JButton btnMesa3;
    private JButton btnMesa4;
    private JButton btnMesa5;
    private JButton btnMesa6;
    private JButton btnMesa7;
    private JButton btnMesa8;
    private JButton btnMesa9;
    private JButton btnMesa10;
    private JButton btnMesa11;
    private JButton btnMesa12;

    private JButton[] botonesMesa = new JButton[24];
    private ImageIcon[] iconosMesa = new ImageIcon[24];

    public Mesita() {
        // Asignar los botones del formulario al arreglo
        botonesMesa[0] = btnMesa1;
        botonesMesa[1] = btnMesa2;
        botonesMesa[2] = btnMesa3;
        botonesMesa[3] = btnMesa4;
        botonesMesa[4] = btnMesa5;
        botonesMesa[5] = btnMesa6;
        botonesMesa[6] = btnMesa7;
        botonesMesa[7] = btnMesa8;
        botonesMesa[8] = btnMesa9;
        botonesMesa[9] = btnMesa10;
        botonesMesa[10] = btnMesa11;
        botonesMesa[11] = btnMesa12;


        // Cargar íconos y aplicar estilo a cada botón
        for (int i = 0; i < 12; i++) {
            String ruta = "imagenes/m" + (i + 1) + ".png";
            iconosMesa[i] = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));

            botonesMesa[i].setIcon(iconosMesa[i]);
            botonesMesa[i].setBorderPainted(false);
            botonesMesa[i].setContentAreaFilled(false);
            botonesMesa[i].setFocusPainted(false);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mesita");
        frame.setContentPane(new Mesita().panelMesita);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
