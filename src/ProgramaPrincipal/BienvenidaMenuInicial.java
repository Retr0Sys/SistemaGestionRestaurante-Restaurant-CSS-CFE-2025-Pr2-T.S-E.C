package ProgramaPrincipal;

import Forms.MenuPuntoVenta;

import javax.swing.*;
import java.awt.*;

public class BienvenidaMenuInicial extends JFrame {

    private JPanel JPBienvenida;
    private JLabel lblBienvenida;
    private JButton BtnIngresar;
    private JLabel lblFoto;

    public BienvenidaMenuInicial() {
        // Panel principal con layout vertical
        JPBienvenida = new JPanel();
        JPBienvenida.setBackground(new Color(245, 245, 245));
        JPBienvenida.setLayout(new BoxLayout(JPBienvenida, BoxLayout.Y_AXIS));
        JPBienvenida.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Texto de bienvenida
        lblBienvenida = new JLabel("¡Bienvenido al Punto de Venta!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(50, 50, 50));
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        // Logo grande y centrado
        lblFoto = new JLabel();
        ImageIcon iconoCSS = new ImageIcon(new ImageIcon("imagenes/CSSLogo.jpg").getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        lblFoto.setIcon(iconoCSS);
        lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFoto.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Botón solo con imagen, sin fondo ni borde
        BtnIngresar = new JButton();
        ImageIcon iconoIngresar = new ImageIcon(new ImageIcon("imagenes/Ingresar.png").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
        BtnIngresar.setIcon(iconoIngresar);
        BtnIngresar.setContentAreaFilled(false);
        BtnIngresar.setBorderPainted(false);
        BtnIngresar.setFocusPainted(false);
        BtnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BtnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Acción del botón
        BtnIngresar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(JPBienvenida);
            if (ventana != null) {
                ventana.dispose(); // Cierra la ventana correctamente
            }

            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menu.pack();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true); // Abre la nueva ventana
        });

        // Efectos combinados: zoom + sombra
        BtnIngresar.setBorder(BorderFactory.createEmptyBorder()); // Sin borde por defecto

        BtnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Zoom al pasar el mouse
                ImageIcon iconoZoom = new ImageIcon(new ImageIcon("imagenes/Ingresar.png")
                        .getImage().getScaledInstance(260, 260, Image.SCALE_SMOOTH));
                BtnIngresar.setIcon(iconoZoom);

                // Sombra inferior
                BtnIngresar.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Volver al tamaño original
                ImageIcon iconoNormal = new ImageIcon(new ImageIcon("imagenes/Ingresar.png")
                        .getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
                BtnIngresar.setIcon(iconoNormal);

                // Quitar sombra
                BtnIngresar.setBorder(BorderFactory.createEmptyBorder());
            }
        });


        // Agregamos componentes en orden: título, logo, botón
        JPBienvenida.add(lblBienvenida);
        JPBienvenida.add(lblFoto);
        JPBienvenida.add(BtnIngresar);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bienvenida");
        frame.setContentPane(new BienvenidaMenuInicial().JPBienvenida);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}