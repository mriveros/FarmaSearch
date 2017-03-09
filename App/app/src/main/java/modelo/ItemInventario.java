package modelo;

/**
 * Created by NeN on 19/04/2016.
 */
public class ItemInventario {
    private  long id;
    private  int stock;
    private  float precio;
    private  Farmacia farmacia;
    private  Producto producto;
    private  Departamento departamento;

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public ItemInventario() {
    }

    public ItemInventario(long id, int stock,float precio, Farmacia farmacia, Producto producto, Departamento departamento) {
        this.id = id;
        this.stock = stock;
        this.farmacia = farmacia;
        this.producto = producto;
        this.departamento = departamento;
        this.precio = precio;
    }
}
