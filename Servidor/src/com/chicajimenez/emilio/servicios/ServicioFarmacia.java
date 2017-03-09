package com.chicajimenez.emilio.servicios;

import javax.ws.rs.PathParam;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.Usuario;



@Path("/servicioF")
public class ServicioFarmacia {
	

	// Devuelve una farmacia por ID en JSON
	@GET
	@Path("/Farmacia/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Farmacia getFarmaciaByIdJSON(@PathParam("id") long id){
		return FarmaciaDAO.INSTANCE.getFarmacia(id);
	}
	
	
	// Devuelve el listado de farmacias en JSON
	@GET
	@Path("/Farmacias")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Farmacia> getAllFarmaciasInJSON(){
		return FarmaciaDAO.INSTANCE.getFarmacias();
	}

	// Devuelve el listado de farmacias de un usario en JSON
	@GET
	@Path("/Farmacias/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Farmacia> getAllFarmaciasUserInJSON(@PathParam("id") long id){
		System.out.println(id);
		return FarmaciaDAO.INSTANCE.getFarmaciasUsuario(id);
	}
	
	// Añadir nueva Farmacia - return JSON para ok o no ok
	@PUT
	@Path("/Farmacia/{nombre}/{descripcion}/{imagen}/{latitud}/{longitud}/{email}/{telefono}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String guardarFarmacia(@PathParam("nombre") String nombre, 
			@PathParam("descripcion") String descripcion,
			@PathParam("imagen") String imagen,
			@PathParam("latitud") float latitud,
			@PathParam("longitud") float longitud,
			@PathParam("email") String email,
			@PathParam("telefono") String telefono,
			@PathParam("id_usuario") long id_usuario){
		Farmacia farmacia = new Farmacia();
		farmacia.setNombre(nombre);
		farmacia.setDescripcion(descripcion);
		farmacia.setImagen(imagen);
		farmacia.setLatitud(latitud);
		farmacia.setLongitud(longitud);
		farmacia.setEmail(email);
		farmacia.setTelefono(telefono);
		Usuario u = UsuarioDAO.INSTANCE.getUsuario(id_usuario);
		farmacia.setUsuario(u);
		
		if(!FarmaciaDAO.INSTANCE.addFarmacia(farmacia)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
	
	// Actualizar una farmacia existente
	@PUT
	@Path("/Farmacia/{id}/{nombre}/{descripcion}/{imagen}/{latitud}/{longitud}/{email}/{telefono}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String actualizarFarmacia(@PathParam("id") long id,
			@PathParam("nombre") String nombre, 
			@PathParam("descripcion") String descripcion,
			@PathParam("imagen") String imagen,
			@PathParam("latitud") float latitud,
			@PathParam("longitud") float longitud,
			@PathParam("email") String email,
			@PathParam("telefono") String telefono,
			@PathParam("id_usuario") long id_usuario){
		Farmacia farmacia = FarmaciaDAO.INSTANCE.getFarmacia(id);
		farmacia.setNombre(nombre);
		farmacia.setDescripcion(descripcion);
		farmacia.setImagen(imagen);
		farmacia.setLatitud(latitud);
		farmacia.setLongitud(longitud);
		farmacia.setEmail(email);
		farmacia.setTelefono(telefono);
		Usuario u = UsuarioDAO.INSTANCE.getUsuario(id_usuario);
		farmacia.setUsuario(u);
		
		if(!FarmaciaDAO.INSTANCE.addFarmacia(farmacia)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
	//Eliminar una farmacia por id
	@DELETE
	@Path("/Farmacia/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String eliminarFarmacia(@PathParam("id") long id){
		
		if(!FarmaciaDAO.INSTANCE.delFarmacia(id)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
}
