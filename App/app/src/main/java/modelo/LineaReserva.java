package modelo;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Created by NeN on 19/04/2016.
 */
public class LineaReserva {
    private long id;
    private Producto producto;
    private Farmacia farmacia;
    private int cantidad;
    @JsonBackReference
    private Reserva reserva;

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
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

    public LineaReserva() {
    }

    public LineaReserva(long id, Producto producto, Farmacia farmacia, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.farmacia = farmacia;
        this.cantidad = cantidad;
    }
}
