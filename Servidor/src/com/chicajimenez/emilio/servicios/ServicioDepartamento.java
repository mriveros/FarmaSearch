package com.chicajimenez.emilio.servicios;

import javax.ws.rs.PathParam;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chicajimenez.emilio.dao.DepartamentoDAO;
import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;



@Path("/servicioD")
public class ServicioDepartamento {
	

	// Devuelve un departamento por ID en JSON
	@GET
	@Path("/Departamento/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Departamento getDepartamentoByIdJSON(@PathParam("id") long id){
		return DepartamentoDAO.INSTANCE.getDepartamento(id);
	}
	
	
	// Devuelve un departamento de una farmacia por ID en JSON
	@GET
	@Path("/DepartamentosF/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Departamento> getDepartamentoByIdFarmJSON(@PathParam("id") long id){
		List<Departamento>  departamentos =DepartamentoDAO.INSTANCE.getDepartamentos();
		List<Departamento>  departamentosFarm = new ArrayList<>();
		for(Departamento d : departamentos){
			if(d.getFarmacia(id)!=null){
				departamentosFarm.add(d);
			}
		}
		return departamentosFarm;
		
	}
	// Devuelve un departamento de una farmacia por ID en JSON
		@GET
		@Path("/DepartamentosF/{id}/{id_usuario}")
		@Produces(MediaType.APPLICATION_JSON)
		public List<Departamento> getDepartamentoByIdFarmJSON(@PathParam("id") long id,
				@PathParam("id_usuario") long id_usuario){
			List<Departamento>  departamentos =DepartamentoDAO.INSTANCE.getDepartamentosUsuario(id_usuario);
			List<Departamento>  departamentosFarm = new ArrayList<>();
			for(Departamento d : departamentos){
				if(d.getFarmacia(id)!=null){
					departamentosFarm.add(d);
				}
			}
			return departamentosFarm;
			
		}
		// Devuelve el listado de farmacias de un usario en JSON
		@GET
		@Path("/Departamentos/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public List<Departamento> getAllDepartamentosUserInJSON(@PathParam("id") long id){
			return DepartamentoDAO.INSTANCE.getDepartamentosUsuario(id);
		}
	
	
	// Devuelve el listado de farmacias en JSON
	@GET
	@Path("/Departamentos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Departamento> getAllDepartamentosInJSON(){
		return DepartamentoDAO.INSTANCE.getDepartamentos();
	}
	
	// Añadir nueva Farmacia - return JSON para ok o no ok
	@PUT
	@Path("/Departamento/{nombre}/{descripcion}/{imagen}")
	@Produces(MediaType.APPLICATION_JSON)
	public String guardarDepartamento(@PathParam("nombre") String nombre, 
			@PathParam("descripcion") String descripcion,
			@PathParam("imagen") String imagen){
		Departamento departamento = new Departamento();
		departamento.setNombre(nombre);
		departamento.setDescripcion(descripcion);
		departamento.setImagen(imagen);
		
		if(!DepartamentoDAO.INSTANCE.addDepartameno(departamento)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
	
	// Actualizar una farmacia existente
	// Añadir nueva Farmacia - return JSON para ok o no ok
		@PUT
		@Path("/Departamento/{id}/{nombre}/{descripcion}/{imagen}")
		@Produces(MediaType.APPLICATION_JSON)
		public String actualizaDpto(@PathParam("id") long id,
				@PathParam("nombre") String nombre, 
				@PathParam("descripcion") String descripcion,
				@PathParam("imagen") String imagen){
			Departamento departamento = DepartamentoDAO.INSTANCE.getDepartamento(id);
			departamento.setNombre(nombre);
			departamento.setDescripcion(descripcion);
			departamento.setImagen(imagen);
			
			if(!DepartamentoDAO.INSTANCE.addDepartameno(departamento)){
				return "{\"status\":\"ok\"}";
			}
			else {
				return "{\"status\":\"not ok\"}";
			}
		}
		//Eliminar una farmacia por id
		@DELETE
		@Path("/Departamento/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String eliminarDpto(@PathParam("id") long id){
			
			if(!DepartamentoDAO.INSTANCE.delDepartamento(id)){
				return "{\"status\":\"ok\"}";
			}
			else {
				return "{\"status\":\"not ok\"}";
			}
		}
}
