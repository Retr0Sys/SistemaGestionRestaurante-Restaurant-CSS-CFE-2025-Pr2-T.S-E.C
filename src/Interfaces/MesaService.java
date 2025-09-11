package Interfaces;

public interface MesaService {

    void asignarMesa(int numeroMesa, String cliente);

    void liberarMesa(int numeroMesa);

    String estadoMesa(int numeroMesa);

    void listarMesas();
}
