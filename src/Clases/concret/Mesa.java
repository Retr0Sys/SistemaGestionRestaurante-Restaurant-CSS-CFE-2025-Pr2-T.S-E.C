package Clases.concret;

public class Mesa
{
    public String JPMesasIni;
    private int idMesa;
    private int capacidad;
    private String estado;   // disponible, ocupada, reservada, limpieza...

    public Mesa() {}

    public Mesa(int idMesa, int capacidad, String estado)
    {
        this.idMesa = idMesa;
        this.capacidad = capacidad;
        this.estado = estado;
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
        return "Mesa{" +
                "idMesa=" + idMesa +
                ", capacidad=" + capacidad +
                ", estado='" + estado + '\'' +
                '}';
    }
}
