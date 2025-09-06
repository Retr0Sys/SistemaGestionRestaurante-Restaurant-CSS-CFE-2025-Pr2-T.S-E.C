import javax.swing.*;
import java.awt.*;

public class BienvenidaMenuInicial extends  JFrame {

    private JPanel JPBienvenida;
    private JLabel lblBienvenida;
    private JButton BtnIngresar;
    private JLabel lblFoto;

    public BienvenidaMenuInicial(){
        //Seteamos icono para nuestra bienvenida y fuente
        ImageIcon iconoCSS = new ImageIcon(new ImageIcon("imagenes/CSSLogo.jpg").getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        lblFoto.setIcon(iconoCSS);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 18));

        //Agregamos accion al boton ingresar
        BtnIngresar.addActionListener(e -> {
            // Cerramos la ventana de bienvenida
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(JPBienvenida);
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
        JFrame frame = new JFrame("Bienvenida");
        frame.setContentPane(new BienvenidaMenuInicial().JPBienvenida);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
