package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mesita extends JFrame {
    public JPanel panelMesita;
    private JPanel JPDentroScroll;
    private JButton btnMesa1, btnMesa2, btnMesa3, btnMesa4, btnMesa5, btnMesa6;
    private JButton btnMesa7, btnMesa8, btnMesa9, btnMesa10, btnMesa11, btnMesa12;

    private JButton[] botonesMesa = new JButton[12];
    private ImageIcon[] iconosMesa = new ImageIcon[12];

    public Mesita() {
        //  Estética general
        Color fondo = new Color(245, 245, 245);
        panelMesita.setBackground(fondo);

        // Asignar botones al arreglo
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

        // Aplicar estilo e íconos
        for (int i = 0; i < botonesMesa.length; i++) {
            String ruta = "imagenes/m" + (i + 1) + ".png";
            iconosMesa[i] = new ImageIcon(new ImageIcon(ruta).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));

            botonesMesa[i].setIcon(iconosMesa[i]);
            botonesMesa[i].setBorderPainted(false);
            botonesMesa[i].setContentAreaFilled(false);
            botonesMesa[i].setFocusPainted(false);
            botonesMesa[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            botonesMesa[i].setToolTipText("Mesa " + (i + 1));

            // Hover visual
            final int index = i;
            botonesMesa[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    botonesMesa[index].setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    botonesMesa[index].setBorder(BorderFactory.createEmptyBorder());
                }
            });

            //  Acción de navegación
            botonesMesa[i].addActionListener(e -> {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPDentroScroll);
                topFrame.dispose();

                FormMesa menu = new FormMesa();
                menu.setContentPane(menu.JPMesasIni);
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.setLocationRelativeTo(null);
                menu.pack();
                menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                menu.setVisible(true);
            });
        }
    }
    public static void mostrarPantallaCompleta(JFrame ventana) {
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza
        ventana.setUndecorated(true); // Quita bordes y barra de título
        ventana.setVisible(true); // Muestra la ventana
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mesita");
        frame.setContentPane(new Mesita().panelMesita);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        mostrarPantallaCompleta(frame);

    }
}

