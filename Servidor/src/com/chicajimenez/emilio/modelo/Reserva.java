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

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
@Entity
@Table(name = "reserva")
public class Reserva {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @ManyToOne
	@JoinColumn(name="id_usuario")
    private Usuario usuario;
    @Column(name = "fecha")
    private Timestamp fecha;
    //Con cascada basta salvar al padre (persistencia por alcanzabilidad)
    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "lineareserva",joinColumns ={@JoinColumn(name = "id_reserva")})*/
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="reserva")
    private List<LineaReserva> lineaReservas = new ArrayList<>();

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
    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    public List<LineaReserva> getLineaReservas() {
		return lineaReservas;
	}

	public void setLineaReservas(List<LineaReserva> lineaReservas) {
		for(int i=0;i<lineaReservas.size();++i){
			lineaReservas.get(i).setReserva(this);
		}
		this.lineaReservas = lineaReservas;
	}

	public void addLinea(LineaReserva l){
   	 	l.setReserva(this);
   	 lineaReservas.add(l);
    }
    public void delLinea(LineaReserva l){
    	lineaReservas.remove(l);
    }
    public LineaReserva getLinea(long id){
    	LineaReserva l=null;
    	for(int i=0;i<lineaReservas.size();++i){
    		if(lineaReservas.get(i).getId()==id)
    			l=lineaReservas.get(i);
    	}
      return l;
    }
}
