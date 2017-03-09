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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
@Entity
@Table(name = "usuario")
public class Usuario {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
	@Column(name = "nombre")
    private String nombre;
	@Column(name = "apellidos")
    private String apellidos;
	@Column(name = "email")
    private String email;
	@Column(name = "password")
    private String password;
	@Column(name = "tipo")
	private int tipo;
	//Con cascada basta salvar al padre (persistencia por alcanzabilidad)
    /*@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="usuario")
    private final List<Farmacia> farmacias = new ArrayList<>();
    
    public void addFarmacia(Farmacia d){
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
	 }*/

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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
    
}
