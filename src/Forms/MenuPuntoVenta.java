package Forms;

import Clases.concret.Mesa;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MenuPuntoVenta extends JFrame {
    public JPanel JPMenuPrinc;
    private JButton btnCarta, btnCocina, btnFact, btnMesa, btnResumen, btnAcerca, btnSalir;
    private JLabel lblCSS;

    public MenuPuntoVenta() {
        //Ajustes visuales
        JLabel fondo = new JLabel(new ImageIcon("imagenes/madera.jpg"));
        fondo.setLayout(new BorderLayout());

        JPMenuPrinc = new JPanel();
        JPMenuPrinc.setOpaque(false);
        JPMenuPrinc.setLayout(new BoxLayout(JPMenuPrinc, BoxLayout.Y_AXIS));
        JPMenuPrinc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(40, 40, 40, 40),
                BorderFactory.createMatteBorder(2, 2, 6, 6, new Color(120, 120, 120, 60))
        ));

        JLabel lblTitulo = new JLabel("Menú Principal");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblTitulo.setForeground(new Color(60, 60, 60));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 30, 30));
        panelBotones.setOpaque(false);
        panelBotones.setBackground(new Color(255, 255, 255, 80));
        panelBotones.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));

        btnCarta = crearBoton("imagenes/Menu.png");
        btnCocina = crearBoton("imagenes/Cocina.png");
        btnFact = crearBoton("imagenes/Facturas.png");
        btnMesa = crearBoton("imagenes/Mesas.png");
        btnResumen = crearBoton("imagenes/Resumen.png");
        btnAcerca = crearBoton("imagenes/Acerca.png");

        panelBotones.add(btnCarta);
        panelBotones.add(btnCocina);
        panelBotones.add(btnFact);
        panelBotones.add(btnMesa);
        panelBotones.add(btnResumen);
        panelBotones.add(btnAcerca);

        lblCSS = new JLabel("Sistema CSS - Punto de Venta");
        lblCSS.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblCSS.setForeground(new Color(80, 80, 80));
        lblCSS.setIcon(new ImageIcon("imagenes/iconoCSS.png"));
        lblCSS.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCSS.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Botón salir
        btnSalir = new JButton("Salir");
        btnSalir.setIcon(new ImageIcon(new ImageIcon("imagenes/Salir.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        btnSalir.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setBackground(new Color(180, 50, 50));
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new Dimension(200, 60));
        btnSalir.setMaximumSize(new Dimension(200, 60));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> {
            reproducirSonido("sonido/button_09-190435.wav");
            System.exit(0);
        });

        JPanel panelSalir = new JPanel();
        panelSalir.setOpaque(false);
        panelSalir.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        panelSalir.add(btnSalir);
        panelSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Acciones de botones
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

        JPMenuPrinc.add(lblTitulo);
        JPMenuPrinc.add(panelBotones);
        JPMenuPrinc.add(lblCSS);
        JPMenuPrinc.add(panelSalir);

        fondo.add(JPMenuPrinc, BorderLayout.CENTER);
        setContentPane(fondo);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);

        // Animación de entrada
        JPMenuPrinc.setLocation(0, -100);
        Timer anim = new Timer(10, new AbstractAction() {
            int y = -100;
            public void actionPerformed(ActionEvent e) {
                if (y < 0) {
                    JPMenuPrinc.setLocation(0, y++);
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        anim.start();
    }
    //Metodo para reproducir sonidos
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
    //Metodo para dar imagenes a los botones
    private JButton crearBoton(String rutaImagen) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int anchoPantalla = screenSize.width;
        int iconSize = (int) (anchoPantalla * 0.12); // 12% del ancho
        int hoverSize = iconSize + 10;

        JButton boton = new JButton();
        ImageIcon icono = new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
        boton.setIcon(icono);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Efectos al pasar el ratón
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(hoverSize, hoverSize, Image.SCALE_SMOOTH)));
                boton.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setIcon(new ImageIcon(new ImageIcon(rutaImagen).getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH)));
                boton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        return boton;
    }
    //Metodo para abrir la ventana en pantalla completa
    private void abrirVentanaMax(JFrame ventana, String panelNombre) {
        dispose(); // cerrar ventana actual

        // Obtener el panel por nombre
        JPanel panel = (JPanel) getPanelPorNombre(ventana, panelNombre);
        ventana.setContentPane(panel);

        ventana.setUndecorated(true); // sin bordes
        ventana.pack(); // ajustar al contenido
        ventana.setLocationRelativeTo(null); // centrar
        ventana.setVisible(true); // mostrar primero
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); // luego maximizar
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void abrirVentanaAcerca(JFrame ventana, String panelNombre) {
        ventana.setContentPane((JPanel) getPanelPorNombre(ventana, panelNombre));
        ventana.setUndecorated(true); //  sin bordes
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH); //  pantalla completa
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
    //Comprobaciones de existencia de la ventana
    private Component getPanelPorNombre(JFrame ventana, String nombreCampo) {
        try {
            return (Component) ventana.getClass().getDeclaredField(nombreCampo).get(ventana);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo acceder al panel: " + nombreCampo);
        }
    }

    public static void adaptarVentanaAResolucion(JFrame ventana) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds(); // área útil sin superponer barra de tareas

        ventana.setBounds(bounds); // adapta tamaño
        ventana.setLocation(bounds.x, bounds.y); // asegura posición correcta
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPuntoVenta menu = new MenuPuntoVenta();

            // Adaptar a resolución de pantalla
            MenuPuntoVenta.adaptarVentanaAResolucion(menu);

            // Aplicar configuración visual completa
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setUndecorated(true);
            menu.pack();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
