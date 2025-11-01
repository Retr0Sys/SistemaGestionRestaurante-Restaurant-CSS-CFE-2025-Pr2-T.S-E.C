package Clases.abstractas;

public abstract class Producto {
    protected int id;
    protected String nombre;
    protected double precio;
    protected int estado;
    protected int stock; // 🆕 Nuevo campo

    public Producto(int id, String nombre, double precio, int estado, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.estado = estado;
        this.stock = stock;
    }

    // Constructor original (sin stock)
    public Producto(int id, String nombre, double precio, int estado) {
        this(id, nombre, precio, estado, 0);
    }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public abstract void calcularImpuesto();
    public abstract String getCategoria();
}
