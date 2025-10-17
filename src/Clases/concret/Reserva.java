package Clases.concret;

import java.sql.Date;
import java.sql.Time;

public class Reserva {
    private int idReserva;
    private int idMesa;
    private Date fecha;
    private Time hora;

    public Reserva() {}

    public Reserva(int idReserva, int idMesa, Date fecha, Time hora) {
        this.idReserva = idReserva;
        this.idMesa = idMesa;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", idMesa=" + idMesa +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
