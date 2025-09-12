package Clases.concret;

public class Mesa
{
    private int idMesa;
    private int capacidad;
    private int idEstadoMesa; // FK a EstadoMesa
    private int idPersonal;   // FK a Personal (mesero asignado)

    public Mesa() {}

    public Mesa(int idMesa, int capacidad, int idEstadoMesa, int idPersonal)
    {
        this.idMesa = idMesa;
        this.capacidad = capacidad;
        this.idEstadoMesa = idEstadoMesa;
        this.idPersonal = idPersonal;
    }

    public int getIdMesa()
    {
        return idMesa;
    }

    public void setIdMesa(int idMesa)
    {
        this.idMesa = idMesa;
    }

    public int getCapacidad()
    {
        return capacidad;
    }

    public void setCapacidad(int capacidad)
    {
        this.capacidad = capacidad;
    }

    public int getIdEstadoMesa()
    {
        return idEstadoMesa;
    }

    public void setIdEstadoMesa(int idEstadoMesa)
    {
        this.idEstadoMesa = idEstadoMesa;
    }

    public int getIdPersonal()
    {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal)
    {
        this.idPersonal = idPersonal;
    }

    @Override
    public String toString()
    {
        return "Mesa{" +
                "idMesa=" + idMesa +
                ", capacidad=" + capacidad +
                ", idEstadoMesa=" + idEstadoMesa +
                ", idPersonal=" + idPersonal +
                '}';
    }
}
