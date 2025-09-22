package Forms;

import Clases.abstractas.Producto;
import DAO.ProductoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class Carta extends JFrame
{
    public JPanel ventanaCarta;
    private JLabel lblTitulo;
    private JTable tblTablaCartaMenu;
    private JButton btnAtras;
    private JTabbedPane TBCarta;
    private JPanel JPCarta;
    private JPanel JPcambiarCarta;
    private JLabel lblNombProdAcambiar;
    private JLabel lblNuevoPrecio;
    private JLabel lblNuevoDisp;
    private JComboBox CBNuevoNombre;
    private JTextField txtNuevoPrecio;
    private JComboBox CBNuevaDisp;
    private JLabel lblTituloCarta;
    private JButton btnModificar;

    public Carta()
    {
        setTitle("Carta del Restaurante");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de la tabla con columnas
        String[] columnas = {"ID", "Nombre", "Precio", "Categoría", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Cargar datos desde la BD
        ProductoDAO dao = new ProductoDAO();
        try
        {
            List<Producto> productos = dao.listar();
            for (Producto p : productos)
            {
                String estado = "Disponible";
                try
                {
                    java.lang.reflect.Method m = p.getClass().getMethod("getEstado");
                    int valor = (int) m.invoke(p);
                    estado = (valor == 1) ? "Disponible" : "No Disponible";
                }
                catch (Exception ex)
                {
                    // por defecto
                }

                modelo.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getPrecio(),
                        p.getClass().getSimpleName(),
                        estado
                });
            }
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + e.getMessage());
        }

        // Asignar el modelo a la tabla ya creada en el .form
        tblTablaCartaMenu.setModel(modelo);
        tblTablaCartaMenu.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblTablaCartaMenu.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblTablaCartaMenu.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblTablaCartaMenu.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblTablaCartaMenu.getColumnModel().getColumn(4).setPreferredWidth(120);
        tblTablaCartaMenu.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Cargar nombres al comboBox
        for (int i = 0; i < tblTablaCartaMenu.getRowCount(); i++)
        {
            CBNuevoNombre.addItem(tblTablaCartaMenu.getValueAt(i, 1).toString());
        }

        // Cargar estados al combo de disponibilidad
        CBNuevaDisp.addItem("Disponible");
        CBNuevaDisp.addItem("No Disponible");

        // Evento al seleccionar producto: precargar precio y estado
        CBNuevoNombre.addActionListener(e ->
        {
            String nombreSeleccionado = (String) CBNuevoNombre.getSelectedItem();
            if (nombreSeleccionado != null)
            {
                for (int i = 0; i < tblTablaCartaMenu.getRowCount(); i++)
                {
                    if (tblTablaCartaMenu.getValueAt(i, 1).toString().equals(nombreSeleccionado))
                    {
                        txtNuevoPrecio.setText(tblTablaCartaMenu.getValueAt(i, 2).toString());
                        CBNuevaDisp.setSelectedItem(tblTablaCartaMenu.getValueAt(i, 4).toString());
                        break;
                    }
                }
            }
        });

        // Acción botón Modificar
        btnModificar.addActionListener(e ->
        {
            String nombreSeleccionado = (String) CBNuevoNombre.getSelectedItem();
            String nuevoPrecioStr = txtNuevoPrecio.getText();
            String nuevoEstadoStr = (String) CBNuevaDisp.getSelectedItem();

            if (nombreSeleccionado == null || nuevoPrecioStr.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Seleccione un producto y complete el precio.");
                return;
            }

            try
            {
                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                int estado = nuevoEstadoStr.equals("Disponible") ? 1 : 0;

                // Buscar ID del producto en la tabla
                int idProducto = -1;
                for (int i = 0; i < tblTablaCartaMenu.getRowCount(); i++)
                {
                    if (tblTablaCartaMenu.getValueAt(i, 1).toString().equals(nombreSeleccionado))
                    {
                        idProducto = (int) tblTablaCartaMenu.getValueAt(i, 0);
                        break;
                    }
                }

                if (idProducto != -1)
                {
                    dao.actualizarProducto(idProducto, nuevoPrecio, estado);
                    JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");

                    // Actualizar la tabla en tiempo real (mostrar texto, no 0/1)
                    for (int i = 0; i < tblTablaCartaMenu.getRowCount(); i++)
                    {
                        if ((int) tblTablaCartaMenu.getValueAt(i, 0) == idProducto)
                        {
                            tblTablaCartaMenu.setValueAt(nuevoPrecio, i, 2);
                            tblTablaCartaMenu.setValueAt(nuevoEstadoStr, i, 4);
                            break;
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "No se encontró el producto.");
                }
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Precio inválido.");
            }
            catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
            }
        });

        // Acción botón Atrás
        btnAtras.addActionListener(e ->
        {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(ventanaCarta);
            topFrame.dispose();

            MenuPuntoVenta menu = new MenuPuntoVenta();
            menu.setContentPane(menu.JPMenuPrinc);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.pack();
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        });
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            Carta carta = new Carta();
            carta.setContentPane(carta.ventanaCarta);
            carta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            carta.pack();
            carta.setLocationRelativeTo(null);
            carta.setVisible(true);
        });
    }
}
