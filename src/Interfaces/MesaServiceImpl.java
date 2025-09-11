package Interfaces;

public class MesaServiceImpl implements MesaService {
    @Override
    public void asignarMesa(int numeroMesa, String cliente) {
        //Logica para asignar una mesa a un cliente
    }

    @Override
    public void liberarMesa(int numeroMesa) {
        //Logica para liberar una mesa

    }

    @Override
    public String estadoMesa(int numeroMesa) {
        //Logica para obtener el estado de una mesa
        return "";
    }

    @Override
    public void listarMesas() {
        //Logica para listar todas las mesas

    }
}
