package com.chicajimenez.emilio.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

/**
 * Created by NeN on 19/04/2016.
 */
@Entity
@Table(name = "inventario")
public class ItemInventario {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
	@Column(name = "stock")
    private int stock;
	@Column(name = "precio")
    float precio;
	@ManyToOne
	@JoinColumn(name="id_farmacia")
    private Farmacia farmacia;
	@ManyToOne
	@JoinColumn(name="id_producto")
    private Producto producto;
	@ManyToOne
	@JoinColumn(name="id_departamento")
    private Departamento departamento;

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
    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
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
}
