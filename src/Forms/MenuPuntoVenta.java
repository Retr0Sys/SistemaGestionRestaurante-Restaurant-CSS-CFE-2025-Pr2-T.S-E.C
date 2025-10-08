package Forms;

import Clases.concret.Mesa;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

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
        // Panel principal
        JPMenuPrinc = new JPanel();
        JPMenuPrinc.setBackground(new Color(245, 245, 245));
        JPMenuPrinc.setLayout(new BoxLayout(JPMenuPrinc, BoxLayout.Y_AXIS));
        JPMenuPrinc.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 30, 30));
        panelBotones.setOpaque(false);

        // Crear botones con estilo
        btnCarta = crearBoton("imagenes/Menu.png");
        btnCocina = crearBoton("imagenes/Cocina.png");
        btnFact = crearBoton("imagenes/Facturas.png");
        btnMesa = crearBoton("imagenes/Mesas.png");
        btnResumen = crearBoton("imagenes/Resumen.png");
        btnAcerca = crearBoton("imagenes/Acerca.png");

        // Agregar botones al panel
        panelBotones.add(btnCarta);
        panelBotones.add(btnCocina);
        panelBotones.add(btnFact);
        panelBotones.add(btnMesa);
        panelBotones.add(btnResumen);
        panelBotones.add(btnAcerca);

        // Etiqueta inferior
        lblCSS = new JLabel("Sistema CSS - Punto de Venta");
        lblCSS.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCSS.setForeground(new Color(80, 80, 80));
        lblCSS.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCSS.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Acciones
        btnCarta.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            abrirVentanaMax(new Carta(), "ventanaCarta");
        });

        btnCocina.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            abrirVentanaMax(new Cocina(), "ventanaCocina");
        });

        btnFact.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            abrirVentanaMax(new Facturacion(), "ventanaFact");
        });

        btnMesa.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            abrirVentanaMax(new Mesita(), "panelMesita");
        });

        btnResumen.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            abrirVentanaMax(new Resumen(), "ventanaResumen");
        });

        btnAcerca.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            abrirVentanaAcerca(new Acerca(), "JPacerca");
        });
        // Agregar al panel principal
        JPMenuPrinc.add(panelBotones);
        JPMenuPrinc.add(lblCSS);

        setContentPane(JPMenuPrinc);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // Método para crear botones con imagen y efectos
    private JButton crearBoton(String rutaImagen) {
        JButton boton = new JButton();
        ImageIcon icono = new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        boton.setIcon(icono);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hover: sombra + zoom
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH)));
                boton.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                boton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        return boton;
    }


    private void abrirVentanaMax(JFrame ventana, String panelNombre) {
        dispose();
        ventana.setContentPane((JPanel) getPanelPorNombre(ventana, panelNombre));
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.pack();
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setVisible(true);
    }

    private void abrirVentanaAcerca(JFrame ventana, String panelNombre) {
        ventana.setContentPane((JPanel) getPanelPorNombre(ventana, panelNombre));
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setVisible(true);
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

    // Utilidad para obtener panel por nombre
    private Component getPanelPorNombre(JFrame ventana, String nombreCampo) {
        try {
            return (Component) ventana.getClass().getDeclaredField(nombreCampo).get(ventana);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo acceder al panel: " + nombreCampo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPuntoVenta());
    }
}
