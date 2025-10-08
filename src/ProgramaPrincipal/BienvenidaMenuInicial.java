package ProgramaPrincipal;

import Forms.MenuPuntoVenta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;

public class BienvenidaMenuInicial extends JFrame {

    private JPanel JPBienvenida;
    private JLabel lblBienvenida;
    private JLabel lblSubtitulo;
    private JButton BtnIngresar;
    private JLabel lblFoto;

    public BienvenidaMenuInicial() {
        // Panel principal con layout vertical
        JPBienvenida = new JPanel();
        JPBienvenida.setOpaque(false); // Fondo transparente para ver imagen detrás
        JPBienvenida.setLayout(new BoxLayout(JPBienvenida, BoxLayout.Y_AXIS));
        JPBienvenida.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Texto de bienvenida
        lblBienvenida = new JLabel("¡Bienvenido al Punto de Venta!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(50, 50, 50));
        lblBienvenida.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Subtítulo
        lblSubtitulo = new JLabel("Sistema de gestión para restaurantes");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Separador visual
        JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
        separador.setMaximumSize(new Dimension(400, 2));
        separador.setForeground(new Color(180, 180, 180));

        // Logo con marco decorativo
        lblFoto = new JLabel();
        ImageIcon iconoCSS = new ImageIcon(new ImageIcon("imagenes/CSSLogo.jpg").getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
        lblFoto.setIcon(iconoCSS);
        lblFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFoto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Botón con imagen y efectos
        BtnIngresar = new JButton();
        ImageIcon iconoIngresar = new ImageIcon(new ImageIcon("imagenes/Ingresar.png").getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH));
        BtnIngresar.setIcon(iconoIngresar);
        BtnIngresar.setContentAreaFilled(false);
        BtnIngresar.setBorderPainted(false);
        BtnIngresar.setFocusPainted(false);
        BtnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        BtnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        BtnIngresar.setToolTipText("Haz clic para comenzar");

        // Acción del botón con sonido
        BtnIngresar.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");

            Window ventana = SwingUtilities.getWindowAncestor(JPBienvenida);
            if (ventana != null) {
                ventana.dispose();
            }

            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });

        // Efectos visuales: zoom + sombra
        BtnIngresar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                ImageIcon iconoZoom = new ImageIcon(new ImageIcon("imagenes/Ingresar.png")
                        .getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH));
                BtnIngresar.setIcon(iconoZoom);
                BtnIngresar.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }

            public void mouseExited(MouseEvent evt) {
                BtnIngresar.setIcon(iconoIngresar);
                BtnIngresar.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        // Agregamos componentes en orden
        JPBienvenida.add(lblBienvenida);
        JPBienvenida.add(lblSubtitulo);
        JPBienvenida.add(separador);
        JPBienvenida.add(Box.createRigidArea(new Dimension(0, 20)));
        JPBienvenida.add(lblFoto);
        JPBienvenida.add(Box.createRigidArea(new Dimension(0, 20)));
        JPBienvenida.add(BtnIngresar);
    }

    // Método para reproducir sonido
    private void reproducirSonido(String ruta) {
        try {
            File archivoSonido = new File(ruta);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoSonido);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("No se pudo reproducir el sonido: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bienvenida");
        frame.setIconImage(new ImageIcon("imagenes/CSSLogo.jpg").getImage());

        // Fondo con imagen
        JLabel fondo = new JLabel(new ImageIcon("imagenes/madera.jpg"));
        fondo.setLayout(new BorderLayout());

        BienvenidaMenuInicial bienvenida = new BienvenidaMenuInicial();
        fondo.add(bienvenida.JPBienvenida, BorderLayout.CENTER);

        frame.setContentPane(fondo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}