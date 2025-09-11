package Exepciones;

public class MesaNoDisponibleException extends RuntimeException {
    public MesaNoDisponibleException() {
        super("Esta mesa no se encuentra disponible.");
    }
}
