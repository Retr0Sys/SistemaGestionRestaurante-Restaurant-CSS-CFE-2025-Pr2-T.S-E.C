package Forms;

import javax.swing.*;
import java.awt.*;

public class Mesa extends  JFrame {
    public JPanel JPMesasIni;
    private JTabbedPane JPPedidos;
    private JPanel JPprincipal;
    private JPanel JPasignar;
    private JPanel JPpedidos;
    private JSplitPane SplitPrincipal;
    private JPanel JParribaPri;
    private JPanel JPabajoPri;
    private JScrollPane ScrollAbajoPri;
    private JLabel lblMesa;
    private JLabel lblSelecEstado;
    private JLabel lblEstado;
    private JTable TBPedidosMesa;
    private JComboBox CBmesa;
    private JComboBox CBSelecEstado;
    private JLabel lblMesero;
    private JComboBox CBMesero;
    private JLabel lblMesa2;
    private JButton BtnAsignar;
    private JLabel lblMesa3;
    private JComboBox CBmesa3;
    private JComboBox CBmesa2;
    private JLabel lblOperacion;
    private JTable TBProd2;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JLabel lblSubtotal;
    private JTextField txtSubtotal;
    private JButton BtnEnviar;
    private JLabel lblPedidosMesaPedidos;
    private JLabel lblPedidoMesa;
    private JRadioButton RDBaniadir;
    private JRadioButton RDBeliminar;
    private JLabel lblEstadoFin;
    private JButton btnAtras;
    private JLabel lblMesas;
    private JButton cambiarEstadoButton;
    private JPanel JPReservas;
    private JLabel lblMesa4;
    private JLabel lblOperacionReserva;
    private JLabel lblCanitdadPersonas;
    private JTextField txtCantidadPersonas;
    private JLabel lblFecha;
    private JButton btnEnviar;
    private JRadioButton rbtnAñadir;
    private JRadioButton rbtnEliminar;
    private JLabel lblCalendario;


    public Mesa(){
        lblMesas.setFont(new Font("Arial", Font.BOLD, 18));

        //Botón Atrás
        btnAtras.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMesasIni);
            topFrame.dispose();
            // Abrimos la ventana del menú principal
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        mesa.setContentPane(mesa.JPMesasIni);
        mesa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mesa.setLocationRelativeTo(null);
        mesa.pack();
        mesa.setVisible(true);
    }
}
