package com.chicajimenez.emilio.modelo;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
@Entity
@Table(name = "pedido")
public class Pedido {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	@ManyToOne
	@JoinColumn(name="id_usuario")
    private Usuario usuario;
	 @Column(name = "formapago")
    private String formaPago;
	 @Column(name = "fecha")
    private Timestamp fecha;
    //Con cascada basta salvar al padre (persistencia por alcanzabilidad)
    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "lineapedido",joinColumns ={@JoinColumn(name = "id_pedido")})*/
	 @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="pedido")
    private List<LineaPedido> lineasPedido = new ArrayList<LineaPedido>();

    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public List<LineaPedido> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(List<LineaPedido> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}

	public List<LineaPedido> getLineaPedidos() {
        return lineasPedido;
    }

     public void addLinea(LineaPedido l){
    	 l.setPedido(this);
         lineasPedido.add(l);
     }
    public void delLinea(LineaPedido l){
        lineasPedido.remove(l);
    }
    public LineaPedido getLinea(long id){
    	LineaPedido l=null;
    	for(int i=0;i<lineasPedido.size();++i){
    		if(lineasPedido.get(i).getId()==id)
    			l=lineasPedido.get(i);
    	}
       return l;
    }
}
