package Clases.abstractas;

public abstract class Producto
{
    protected int id;
    protected String nombre;
    protected double precio;
    protected int estado;

    public Producto(int id, String nombre, double precio, int estado)
    {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
    }

    public double getPrecio()
    {
        return precio;
    }

    public void setPrecio(double precio)
    {
        this.precio = precio;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    // Getter y setter para estado
    public int getEstado()
    {
        return estado;
    }

    public void setEstado(int estado)
    {
        this.estado = estado;
    }

    // Cada subclase define cómo calcula el impuesto
    public abstract void calcularImpuesto();

    // Cada subclase devuelve su categoría ("comida", "bebida", "postre")
    public abstract String getCategoria();
}
