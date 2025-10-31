package Forms;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import Clases.concret.Mesa;
import DAO.MesaDAO;

public class Mesita extends JFrame {
    public JPanel panelMesita;
    private JPanel JPDentroScroll;
    private JButton btnMesa1, btnMesa2, btnMesa3, btnMesa4, btnMesa5, btnMesa6;
    private JButton btnMesa7, btnMesa8, btnMesa9, btnMesa10, btnMesa11, btnMesa12;

    private JButton[] botonesMesa;
    private ImageIcon[] iconosMesa;
    private MesaDAO mesaDAO = new MesaDAO();

    public Mesita() {
        // Obtener resolución de pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int anchoPantalla = screenSize.width;
        int iconSize = (int) (anchoPantalla * 0.10);

        // Aspectos visuales
        panelMesita = new JPanel(new GridLayout(3, 4, 30, 30)); // 3 filas x 4 columnas
        panelMesita.setBackground(new Color(245, 245, 245));
        panelMesita.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPDentroScroll = new JPanel();
        JPDentroScroll.setBackground(Color.WHITE);
        JPDentroScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Inicializar botones
        btnMesa1 = new JButton(); btnMesa2 = new JButton(); btnMesa3 = new JButton(); btnMesa4 = new JButton();
        btnMesa5 = new JButton(); btnMesa6 = new JButton(); btnMesa7 = new JButton(); btnMesa8 = new JButton();
        btnMesa9 = new JButton(); btnMesa10 = new JButton(); btnMesa11 = new JButton(); btnMesa12 = new JButton();

        botonesMesa = new JButton[]{
                btnMesa1, btnMesa2, btnMesa3, btnMesa4, btnMesa5, btnMesa6,
                btnMesa7, btnMesa8, btnMesa9, btnMesa10, btnMesa11, btnMesa12
        };
        iconosMesa = new ImageIcon[12];

        actualizarEstadosMesas(iconSize);

        for (int i = 0; i < botonesMesa.length; i++) {
            final int index = i;
            JButton boton = botonesMesa[i];

            boton.setPreferredSize(new Dimension(iconSize, iconSize));
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            boton.addActionListener(e -> {
                int idMesaSeleccionada = index + 1;

                dispose();

                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("FormMesa");
                    frame.setUndecorated(true);

                    FormMesa vista = new FormMesa();
                    adaptarVentanaAResolucion(frame);
                    frame.setContentPane(vista.JPMesasIni);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                });
            });

            panelMesita.add(boton);
        }
    }

    private void actualizarEstadosMesas(int iconSize) {
        try {
            List<Mesa> mesas = mesaDAO.listar();
            for (int i = 0; i < botonesMesa.length && i < mesas.size(); i++) {
                Mesa mesa = mesas.get(i);
                String ruta = obtenerRutaIcono(mesa.getIdMesa(), mesa.getEstado());
                iconosMesa[i] = new ImageIcon(new ImageIcon(ruta).getImage()
                        .getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                botonesMesa[i].setIcon(iconosMesa[i]);
                botonesMesa[i].setToolTipText("Mesa " + mesa.getIdMesa() + " - " + mesa.getEstado());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las mesas: " + e.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerRutaIcono(int numeroMesa, String estado) {
        if (estado == null) estado = "Libre";
        switch (estado.toLowerCase()) {
            case "ocupada":
                return "imagenes/mr" + numeroMesa + ".png";
            case "reservada":
                return "imagenes/ma" + numeroMesa + ".png";
            case "limpieza":
                return "imagenes/mv" + numeroMesa + ".png";
            case "libre":
            default:
                return "imagenes/m" + numeroMesa + ".png";
        }
    }

    public static void adaptarVentanaAResolucion(JFrame ventana) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        ventana.setBounds(bounds);
        ventana.setLocation(bounds.x, bounds.y);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mesita");
            frame.setUndecorated(true);

            Mesita vista = new Mesita();
            adaptarVentanaAResolucion(frame);
            frame.setContentPane(vista.panelMesita);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}