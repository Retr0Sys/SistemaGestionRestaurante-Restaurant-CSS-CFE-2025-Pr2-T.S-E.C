package Exepciones;

public class NoHayMesasValidasException extends Exception {
    public NoHayMesasValidasException() {
        super("No hay mesas con cuentas activas o con consumo mayor a $0.");
    }

    public NoHayMesasValidasException(String mensaje) {
        super(mensaje);
    }
}
