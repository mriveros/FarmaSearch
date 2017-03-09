package com.chicajimenez.emilio.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * Created by NeN on 19/04/2016.
 */
@Entity
@Table(name = "lineareserva")
public class LineaReserva {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="id_reserva")
	private Reserva reserva;
	
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="id_farmacia")
	private Farmacia farmacia;
	
	@Column(name = "cantidad")
	private int cantidad;

	
    protected Reserva getReserva() {
		return reserva;
	}

	protected void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public long getId() {
        return id;
    }

    protected void setId(long id) {
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
}
