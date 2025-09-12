package Clases.concret;

public class EstadoMesa
{
    private int idEstadoMesa;
    private String estado; // disponible, ocupada, reservada, limpieza...

    public EstadoMesa() {}

    public EstadoMesa(int idEstadoMesa, String estado)
    {
        this.idEstadoMesa = idEstadoMesa;
        this.estado = estado;
    }

    public int getIdEstadoMesa()
    {
        return idEstadoMesa;
    }

    public void setIdEstadoMesa(int idEstadoMesa)
    {
        this.idEstadoMesa = idEstadoMesa;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    @Override
    public String toString()
    {
        return "EstadoMesa{" +"idEstadoMesa=" + idEstadoMesa +", estado='" + estado + '\'' +'}';
    }
}
