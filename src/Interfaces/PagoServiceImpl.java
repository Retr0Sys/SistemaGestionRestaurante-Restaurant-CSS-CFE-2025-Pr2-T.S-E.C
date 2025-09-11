package Interfaces;

public class PagoServiceImpl implements  PagoService {

    @Override
    public void procesarPago(double monto) {
        // Lógica para procesar el pago
    }

    @Override
    public void emitirRecibo(String detallesPago) {
        // Lógica para emitir un recibo
    }

    @Override
    public boolean validarPago(String metodoPago, double monto) {
        // Lógica para validar el pago
        return false;
    }

    @Override
    public void registrarTransaccion(String detallesTransaccion) {
        // Lógica para registrar la transacción
    }
}
