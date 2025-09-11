package Interfaces;

public interface CartaService {

    void mostrarCarta();

    void agregarItem(String item);

    void eliminarItem(String item);

    void actualizarItem(String item, String nuevoItem);

}
