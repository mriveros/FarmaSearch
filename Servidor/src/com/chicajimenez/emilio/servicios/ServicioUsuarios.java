package com.chicajimenez.emilio.servicios;

import javax.ws.rs.PathParam;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.Usuario;
import com.chicajimenez.emilio.util.Cifrador;



@Path("/servicioU")
public class ServicioUsuarios {
	

	// Devuelve un usuario por ID en JSON
	@GET
	@Path("/Usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario getUsuarioByIdJSON(@PathParam("id") long id){
		return UsuarioDAO.INSTANCE.getUsuario(id);
	}
	
	
	// Devuelve el listado de usuarios en JSON
	@GET
	@Path("/Usuarios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usuario> getAllUsuariosInJSON(){
		return UsuarioDAO.INSTANCE.getUsuarios();
	}

	
	// Añadir nuevo Usuario - return JSON para ok o no ok
	@POST
	@Path("/Usuario")
	@Produces(MediaType.APPLICATION_JSON)
	public String guardarUsuario(@FormParam("nombre") String nombre, 
			@FormParam("apellidos") String apellidos,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("tipo") int tipo){
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setEmail(email);
		usuario.setPassword(Cifrador.cifrar(password,Cifrador.SHA1));
		usuario.setTipo(tipo);
		
		if(!UsuarioDAO.INSTANCE.addUsuario(usuario)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}

	//Eliminar una farmacia por id
	@DELETE
	@Path("/Usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String eliminarUsuario(@PathParam("id") long id){
		
		if(!UsuarioDAO.INSTANCE.delUsuario(id)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
}
