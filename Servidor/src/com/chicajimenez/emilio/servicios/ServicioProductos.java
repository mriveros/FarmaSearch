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

import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.dao.InventarioDAO;
import com.chicajimenez.emilio.dao.ProductoDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.Producto;



@Path("/servicioP")
public class ServicioProductos {
	

	// Devuelve un producto por ID en JSON
	@GET
	@Path("/Producto/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Producto getProductoByIdJSON(@PathParam("id") long id){
		return ProductoDAO.INSTANCE.getProducto(id);
	}
	
	
	// Devuelve el listado de productos en JSON
	@GET
	@Path("/Productos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> getAllProductosInJSON(){
		return ProductoDAO.INSTANCE.getProductos();
	}
	
	// Devuelve el listado de productos de un usuario en JSON
	@GET
	@Path("/Productos/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> getAllProductosInJSON(@PathParam("id") long id){
		
		return ProductoDAO.INSTANCE.getProductosUsuario(id);
	}
	
	// Devuelve el listado de productos no inventariados en una farmacia de un usuario en JSON
	@GET
	@Path("/ProductosN/{id}/{id_usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Producto> getAllProductosNoInventariadosInJSON(@PathParam("id") long id,
			@PathParam("id_usuario") long id_usuario){
		return ProductoDAO.INSTANCE.getProductosNoInventariados(id, id_usuario);
	}

	//Eliminar un producto por id
	@DELETE
	@Path("/Producto/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String eliminarProducto(@PathParam("id") long id){
		
		if(!ProductoDAO.INSTANCE.delProducto(id)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
}
