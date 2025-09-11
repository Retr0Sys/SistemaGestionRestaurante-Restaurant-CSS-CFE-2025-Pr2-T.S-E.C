package Interfaces;

public interface PagoService {

    void procesarPago(double monto);

    void emitirRecibo(String detallesPago);

    boolean validarPago(String metodoPago, double monto);

    void registrarTransaccion(String detallesTransaccion);
}
