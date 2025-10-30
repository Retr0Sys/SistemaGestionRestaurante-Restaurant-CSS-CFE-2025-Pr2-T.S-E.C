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
        //Aspectos visuales
        panelMesita.setBackground(new Color(245, 245, 245));
        panelMesita.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        if (JPDentroScroll != null) {
            JPDentroScroll.setBackground(Color.WHITE);
            JPDentroScroll.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
        }
        //Adjuntamos los bottones al array de JButtons
        botonesMesa = new JButton[]{
                btnMesa1, btnMesa2, btnMesa3, btnMesa4, btnMesa5, btnMesa6,
                btnMesa7, btnMesa8, btnMesa9, btnMesa10, btnMesa11, btnMesa12
        };
        iconosMesa = new ImageIcon[12];

        actualizarEstadosMesas();
        //Damos formato a los botones
        for (int i = 0; i < botonesMesa.length; i++) {
            final int index = i;
            JButton boton = botonesMesa[i];

            boton.setPreferredSize(new Dimension(160, 160));
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            //Al seleccionar cualquiera de los botones nos envía a la siguiente ventana con la mesa seleccionada
            boton.addActionListener(e -> {
                int idMesaSeleccionada = index + 1;

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPDentroScroll);
                topFrame.dispose();

                FormMesa menu = new FormMesa(idMesaSeleccionada);
                menu.setContentPane(menu.JPMesasIni);
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.setLocationRelativeTo(null);
                menu.pack();
                menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
                menu.setVisible(true);
            });
        }
    }
    //Metodo para asignar una imagen dependiendo del estado de la mesa
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
    //Obtenemos las rutas de las imagenes
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