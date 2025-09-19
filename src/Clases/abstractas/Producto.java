package Clases.abstractas;

public abstract class Producto
{
    protected int id;
    protected String nombre;
    protected double precio;

    public Producto(int id, String nombre, double precio)
    {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    // Cada subclase define cómo calcula el impuesto
    public abstract void calcularImpuesto();

    // Cada subclase devuelve su categoría ("comida", "bebida", "postre")
    public abstract String getCategoria();
}
