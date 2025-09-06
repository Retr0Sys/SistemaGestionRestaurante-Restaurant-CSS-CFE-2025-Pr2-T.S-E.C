import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Carta extends JFrame
{
    private JPanel ventana;
    private JLabel lblTitulo;
    private JTable tblTablaCartaMenu;

    public Carta()
    {
        setTitle("Carta del Restaurante");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ventana = new JPanel(new BorderLayout());

        // Título
        lblTitulo = new JLabel("Carta del Restaurante", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        ventana.add(lblTitulo, BorderLayout.NORTH);

        // Modelo de la tabla con columnas
        String[] columnas = {"Nombre", "Precio", "Categoría", "Disponibilidad"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Ejemplo de filas
        modelo.addRow(new Object[]{"Pizza Americana", 350.0, "Pizza", "Disponible"});
        modelo.addRow(new Object[]{"Milanesa con papas", 420.0, "Principal", "Agotado"});
        modelo.addRow(new Object[]{"Coca-Cola 500ml", 120.0, "Bebida", "Disponible"});
        modelo.addRow(new Object[]{"Flan casero", 180.0, "Postre", "Disponible"});

        // Tabla SIN JScrollPane
        tblTablaCartaMenu = new JTable(modelo);
        ventana.add(tblTablaCartaMenu, BorderLayout.CENTER);

        add(ventana);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            new Carta().setVisible(true);
        });
    }
}
