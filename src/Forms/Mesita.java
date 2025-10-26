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
        // Color de fondo
        panelMesita.setBackground(new Color(245, 245, 245));

        // Vincular botones del .form con el arreglo
        botonesMesa = new JButton[]{
                btnMesa1, btnMesa2, btnMesa3, btnMesa4, btnMesa5, btnMesa6,
                btnMesa7, btnMesa8, btnMesa9, btnMesa10, btnMesa11, btnMesa12
        };
        iconosMesa = new ImageIcon[12];

        // Cargar estado de las mesas desde la BD
        actualizarEstadosMesas();

        // Aplicar estilos y acciones
        for (int i = 0; i < botonesMesa.length; i++) {
            final int index = i;
            JButton boton = botonesMesa[i];
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Efecto hover
            boton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(100, 100, 100)));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton.setBorder(BorderFactory.createEmptyBorder());
                }
            });

            // Acción: abrir FormMesa con el número de mesa seleccionado
            boton.addActionListener(e -> {
                int idMesaSeleccionada = index + 1; // suponiendo que btnMesa1 es la mesa 1, btnMesa2 la 2, etc.

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPDentroScroll);
                topFrame.dispose();

                FormMesa menu = new FormMesa(idMesaSeleccionada); // 👈 pasamos el número de mesa
                menu.setContentPane(menu.JPMesasIni);
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.setLocationRelativeTo(null);
                menu.pack();
                menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                menu.setVisible(true);
            });


        }
    }
    //Consulta la BD y actualiza los íconos de las mesas según su estado.

    private void actualizarEstadosMesas() {
        try {
            List<Mesa> mesas = mesaDAO.listar();
            for (int i = 0; i < botonesMesa.length && i < mesas.size(); i++) {
                Mesa mesa = mesas.get(i);
                String ruta = obtenerRutaIcono(mesa.getIdMesa(), mesa.getEstado());
                iconosMesa[i] = new ImageIcon(new ImageIcon(ruta).getImage()
                        .getScaledInstance(160, 160, Image.SCALE_SMOOTH));
                botonesMesa[i].setIcon(iconosMesa[i]);
                botonesMesa[i].setToolTipText("Mesa " + mesa.getIdMesa() + " - " + mesa.getEstado());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las mesas: " + e.getMessage(),
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }
     //Devuelve la ruta del ícono según el estado de la mesa.
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


    public static void mostrarPantallaCompleta(JFrame ventana) {
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setUndecorated(true);
        ventana.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mesita");

            // ⚠️ Debe ir antes de add() o pack()
            frame.setUndecorated(true);

            Mesita vista = new Mesita();
            frame.setContentPane(vista.panelMesita);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
