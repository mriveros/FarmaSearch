package com.chicajimenez.emilio.modelo;

import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
@Entity
@Table(name = "departamento")
public class Departamento {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "descripcion")
    private String descripcion;
	@Column(name = "imagen")
    private String imagen;
	@ManyToOne
	@JoinColumn(name="id_usuario")
    private Usuario usuario;
	 @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
     @JoinTable(name = "farmacia_departamento",
     joinColumns = {@JoinColumn(name = "id_departamento")},
     inverseJoinColumns = {@JoinColumn(name = "id_farmacia")})  
	private final List<Farmacia> farmacias = new ArrayList<>();
	 
	    public void addFarmacia(Farmacia d){
	    	//for(int i=0;i<farmacias.size();)
	    	farmacias.add(d);
	    }
	    public void delFarmacia(Farmacia d){
	    	farmacias.remove(d);
	    }
	    public Farmacia getFarmacia(long id){
	    	Farmacia d=null;
	    	for(int i=0;i<farmacias.size();++i){
	    		if(farmacias.get(i).getId()==id)
	    			d=farmacias.get(i);
	    	}
	      return d;
	 }
	 public List<Farmacia> getFarmacias() {
		return Collections.unmodifiableList(farmacias);
	 }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}

