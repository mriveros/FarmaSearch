package modelo;

/**
 * Created by NeN on 19/04/2016.
 */
public class ItemCesta {
    private int cantidad;
    private Farmacia farmacia;
    private  Producto producto;
    private float precio;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public ItemCesta() {
    }

    public void addCantidad(){
        cantidad++;
    }
    public void restarUnoACantidad(){
        cantidad--;
    }

    public ItemCesta( int cantidad, Farmacia farmacia, Producto producto,float precio) {
        this.cantidad = cantidad;
        this.farmacia = farmacia;
        this.producto = producto;
        this.precio=precio;
    }
}
