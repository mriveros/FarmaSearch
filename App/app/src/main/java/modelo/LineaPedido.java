package modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by NeN on 19/04/2016.
 */
public class LineaPedido {
    private long id;
    private Producto producto;
    private Farmacia farmacia;
    private int cantidad;
    @JsonBackReference
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }


    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LineaPedido() {
    }

    public LineaPedido(long id, Producto producto,Farmacia farmacia, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.farmacia = farmacia;
        this.cantidad = cantidad;
    }
}
