package Forms;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import DAO.MesaDAO;
import DAO.PedidoDAO;
import Clases.concret.Mesa;

public class FormMesa extends JFrame
{
    public JPanel JPMesasIni;
    private JComboBox<Integer> CBmesa;
    private JComboBox<String> CBSelecEstado;
    private JLabel lblEstado;
    private JTable TBPedidosMesa;
    private JButton cambiarEstadoButton;
    private JButton btnAtras;
    private JTabbedPane JPPedidos;
    private JPanel JPprincipal;
    private JSplitPane SplitPrincipal;
    private JPanel JParribaPri;
    private JPanel JPabajoPri;
    private JScrollPane ScrollAbajoPri;
    private JLabel lblPedidoMesa;
    private JPanel JPasignar;
    private JPanel JPpedidos;
    private JPanel JPReservas;
    private JLabel lblMesas;
    private JLabel lblMesa2;
    private JComboBox CBmesa2;
    private JComboBox CBMesero;
    private JLabel lblMesero;
    private JButton BtnAsignar;
    private JLabel lblMesa3;
    private JLabel lblOperacion;
    private JTable TBProd2;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JLabel lblSubtotal;
    private JTextField txtSubtotal;
    private JButton BtnEnviar;
    private JLabel lblPedidosMesaPedidos;
    private JRadioButton RDBaniadir;
    private JRadioButton RDBeliminar;
    private JComboBox CBmesa3;
    private JLabel lblMesa4;
    private JLabel lblOperacionReserva;
    private JLabel lblCanitdadPersonas;
    private JTextField txtCantidadPersonas;
    private JLabel lblFecha;
    private JButton btnEnviar;
    private JRadioButton rbtnAñadir;
    private JRadioButton rbtnEliminar;
    private JLabel lblMesa;
    private JLabel lblSelecEstado;
    private JLabel lblEstadoFin;
    private JLabel lblCalendario;
    private JComboBox CBmesa4;

    private MesaDAO mesaDAO = new MesaDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();

    public FormMesa()
    {
        ImageIcon imagen = new ImageIcon(new ImageIcon("imagenes/Atras.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnAtras.setIcon(imagen);
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.setFocusPainted(false);


        ImageIcon imagen2 = new ImageIcon(new ImageIcon("imagenes/Cambiar estado.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        cambiarEstadoButton.setIcon(imagen2);
        cambiarEstadoButton.setBorderPainted(false);
        cambiarEstadoButton.setContentAreaFilled(false);
        cambiarEstadoButton.setFocusPainted(false);

        ImageIcon imagen3 = new ImageIcon(new ImageIcon("imagenes/Asignar.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        BtnAsignar.setIcon(imagen3);
        BtnAsignar.setBorderPainted(false);
        BtnAsignar.setContentAreaFilled(false);
        BtnAsignar.setFocusPainted(false);

        ImageIcon imagen4 = new ImageIcon(new ImageIcon("imagenes/Enviar.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        BtnEnviar.setIcon(imagen4);
        BtnEnviar.setBorderPainted(false);
        BtnEnviar.setContentAreaFilled(false);
        BtnEnviar.setFocusPainted(false);

        ImageIcon imagen5 = new ImageIcon(new ImageIcon("imagenes/Enviar.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btnEnviar.setIcon(imagen5);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setFocusPainted(false);


        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));

        cargarMesas();
        cargarEstados();

        // Evento al seleccionar una mesa
        CBmesa.addActionListener(e -> mostrarDatosMesa());

        // Evento al presionar el botón cambiar estado
        cambiarEstadoButton.addActionListener(e -> cambiarEstadoMesa());

        // Botón atrás
        btnAtras.addActionListener(e ->
        {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPMesasIni);
            topFrame.dispose();
            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setVisible(true);
        });
    }

    private void cargarMesas()
    {
        try
        {
            List<Mesa> mesas = mesaDAO.listar();

            // Limpiamos los ComboBox antes de cargarlos
            CBmesa.removeAllItems();
            CBmesa2.removeAllItems();
            CBmesa3.removeAllItems();
            CBmesa4.removeAllItems();

            // Cargar los ids de las mesas en cada ComboBox
            for (Mesa m : mesas)
            {
                CBmesa.addItem(m.getIdMesa());
                CBmesa2.addItem(m.getIdMesa());
                CBmesa3.addItem(m.getIdMesa());
                CBmesa4.addItem(m.getIdMesa());
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error cargando mesas: " + e.getMessage());
        }
    }


    private void cargarEstados()
    {
        // Estados fijos cargados directamente en el ComboBox
        CBSelecEstado.addItem("Disponible");
        CBSelecEstado.addItem("Ocupada");
        CBSelecEstado.addItem("Reservada");
        CBSelecEstado.addItem("Limpieza");
    }

    private void mostrarDatosMesa()
    {
        try
        {
            int idMesa = (int) CBmesa.getSelectedItem();
            Mesa m = mesaDAO.buscarPorId(idMesa);

            // Mostrar estado actual
            lblEstadoFin.setText(m.getEstado());

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error mostrando datos: " + e.getMessage());
        }
    }


    private void cambiarEstadoMesa()
    {
        try
        {
            int idMesa = (int) CBmesa.getSelectedItem();
            String nuevoEstado = (String) CBSelecEstado.getSelectedItem();

            // Actualizar mesa
            Mesa m = mesaDAO.buscarPorId(idMesa);
            m.setEstado(nuevoEstado);
            mesaDAO.actualizar(m);

            // Mostrar el nuevo estado en lblEstadoFin
            lblEstadoFin.setText(nuevoEstado);

            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error actualizando estado: " + e.getMessage());
        }
    }


    public static void main(String[] args)
    {
        FormMesa form = new FormMesa();
        form.setContentPane(form.JPMesasIni);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setLocationRelativeTo(null);
        form.pack();
        form.setVisible(true);
    }
}
