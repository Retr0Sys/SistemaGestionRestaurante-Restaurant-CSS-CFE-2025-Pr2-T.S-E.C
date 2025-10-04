package Forms;

import javax.swing.*;
import java.awt.*;

public class Mesita {
    private JPanel panelMesita;
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
    private JButton btnMesa13;
    private JButton btnMesa14;
    private JButton btnMesa15;
    private JButton btnMesa16;
    private JButton btnMesa17;
    private JButton btnMesa18;
    private JButton btnMesa19;
    private JButton btnMesa20;
    private JButton btnMesa21;
    private JButton btnMesa22;
    private JButton btnMesa23;
    private JButton btnMesa24;

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
        botonesMesa[12] = btnMesa13;
        botonesMesa[13] = btnMesa14;
        botonesMesa[14] = btnMesa15;
        botonesMesa[15] = btnMesa16;
        botonesMesa[16] = btnMesa17;
        botonesMesa[17] = btnMesa18;
        botonesMesa[18] = btnMesa19;
        botonesMesa[19] = btnMesa20;
        botonesMesa[20] = btnMesa21;
        botonesMesa[21] = btnMesa22;
        botonesMesa[22] = btnMesa23;
        botonesMesa[23] = btnMesa24;

        // Cargar íconos y aplicar estilo a cada botón
        for (int i = 0; i < 24; i++) {
            String ruta = "imagenes/mesa" + (i + 1) + ".png";
            iconosMesa[i] = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

            botonesMesa[i].setIcon(iconosMesa[i]);
            botonesMesa[i].setBorderPainted(false);
            botonesMesa[i].setContentAreaFilled(false);
            botonesMesa[i].setFocusPainted(false);
        }
    }
}
