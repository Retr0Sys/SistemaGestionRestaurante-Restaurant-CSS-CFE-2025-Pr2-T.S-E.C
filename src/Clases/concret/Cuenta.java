package Clases.concret;

import java.sql.Timestamp;

public class Cuenta
{
    private int idCuenta;
    private int idMesa;
    private Timestamp fechaApertura;
    private Timestamp fechaCierre;
    private int estado; // 1 = abierto, 0 = cerrado

    public Cuenta(int idCuenta, int idMesa, Timestamp fechaApertura, Timestamp fechaCierre, int estado)
    {
        this.idCuenta = idCuenta;
        this.idMesa = idMesa;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.estado = estado;
    }

    public int getIdCuenta()
    {
        return idCuenta;
    }

    public int getIdMesa()
    {
        return idMesa;
    }

    public Timestamp getFechaApertura()
    {
        return fechaApertura;
    }

    public Timestamp getFechaCierre()
    {
        return fechaCierre;
    }

    public int getEstado()
    {
        return estado;
    }

    public void setEstado(int estado)
    {
        this.estado = estado;
    }
}
