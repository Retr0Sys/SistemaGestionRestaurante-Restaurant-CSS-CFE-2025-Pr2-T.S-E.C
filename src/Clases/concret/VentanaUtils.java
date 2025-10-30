package Clases.concret;

import javax.swing.*;
import java.awt.*;

public class VentanaUtils {

    /**
     * Adapta una ventana JFrame a pantalla completa y aplica escalado proporcional si se desea.
     *
     * @param frame La ventana a adaptar.
     * @param usarPantallaCompleta Si true, maximiza la ventana y la hace sin bordes.
     */
    public static void adaptarVentana(JFrame frame, boolean usarPantallaCompleta) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (usarPantallaCompleta) {
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            frame.setSize(screenSize);
            frame.setLocationRelativeTo(null);
        }
    }

    /**
     * Escala una imagen proporcionalmente según el alto de pantalla.
     *
     * @param rutaImagen Ruta del archivo de imagen.
     * @param porcentajeAltura Porcentaje del alto de pantalla (ej: 0.3 para 30%).
     * @return ImageIcon escalado.
     */
    public static ImageIcon escalarImagen(String rutaImagen, double porcentajeAltura) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int alto = (int) (screenSize.height * porcentajeAltura);
        ImageIcon original = new ImageIcon(rutaImagen);
        Image imagenEscalada = original.getImage().getScaledInstance(alto, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    /**
     * Escala un botón cuadrado según el alto de pantalla.
     *
     * @param boton JButton a escalar.
     * @param icono ImageIcon original.
     * @param porcentajeAltura Porcentaje del alto de pantalla.
     */
    public static void escalarBoton(JButton boton, ImageIcon icono, double porcentajeAltura) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int size = (int) (screenSize.height * porcentajeAltura);
        Image imagen = icono.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        boton.setIcon(new ImageIcon(imagen));
        boton.setPreferredSize(new Dimension(size, size));
        boton.setMaximumSize(new Dimension(size, size));
    }
}
