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
import com.chicajimenez.emilio.dao.InventarioDAO;
import com.chicajimenez.emilio.dao.ProductoDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.ItemInventario;
import com.chicajimenez.emilio.modelo.Producto;



@Path("/servicioI")
public class ServicioInventario {
	

	// Devuelve un item de inventario por ID en JSON
	@GET
	@Path("/Inventario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ItemInventario getItemByIdJSON(@PathParam("id") long id){
		return InventarioDAO.INSTANCE.getItem(id);
	}
	
	
	// Devuelve el listado de los items de inventario en JSON
	@GET
	@Path("/Items")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemInventario> getAllItemsInJSON(){
		return InventarioDAO.INSTANCE.getInventario();
	}
	// Devuelve el listado de los items del inventario de una farmacia en JSON
	@GET
	@Path("/Items/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ItemInventario> getAllItemsFarmaIDInJSON(@PathParam("id") long idFarmacia){
		List<ItemInventario> itemsPorFarmacia=new ArrayList<>();
		List<ItemInventario> items=InventarioDAO.INSTANCE.getInventario();
		if(items!=null)
			for(int i=0;i<items.size();++i){
				if(items.get(i).getFarmacia().getId()==idFarmacia)
					itemsPorFarmacia.add(items.get(i));
			}
		return itemsPorFarmacia;
	}
	
	// Añadir nuevo Item al inventario - return JSON para ok o no ok
	@PUT
	@Path("/Inventario/{stock}/{id_farmacia}/{id_producto}/{id_departamento}")
	@Produces(MediaType.APPLICATION_JSON)
	public String guardarItem(@PathParam("stock") int stock,
			@PathParam("id_farmacia") long id_farmacia,
			@PathParam("id_producto") long id_producto,
			@PathParam("id_departamento") long id_departamento,
			@PathParam("precio") float precio){
		ItemInventario itemInventario = new ItemInventario();
		itemInventario.setStock(stock);
		itemInventario.setPrecio(precio);
		Departamento d = DepartamentoDAO.INSTANCE.getDepartamento(id_departamento);
		Farmacia f = FarmaciaDAO.INSTANCE.getFarmacia(id_farmacia);
		Producto p = ProductoDAO.INSTANCE.getProducto(id_producto);
		itemInventario.setDepartamento(d);
		itemInventario.setFarmacia(f);
		itemInventario.setProducto(p);
		
		if(!InventarioDAO.INSTANCE.addItem(itemInventario)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
	
	// Actualizar un item del inventario existente - return JSON para ok o no ok
		@PUT
		@Path("/Inventario/{id}/{stock}/{precio}")
		@Produces(MediaType.APPLICATION_JSON)
		public String actualizaItem(@PathParam("id") long id,
				@PathParam("stock") int stock,
				@PathParam("precio") float precio){
			ItemInventario i =InventarioDAO.INSTANCE.getItem(id);
			i.setStock(stock);
			i.setPrecio(precio);
			if(!InventarioDAO.INSTANCE.addItem(i)){
				return "{\"status\":\"ok\"}";
			}
			else {
				return "{\"status\":\"not ok\"}";
			}
		}
		//Eliminar un item del inventario por id
		@DELETE
		@Path("/Inventario/{id}")
		@Produces(MediaType.APPLICATION_JSON)
		public String eliminarItem(@PathParam("id") long id){
			
			if(!InventarioDAO.INSTANCE.delItem(id)){
				return "{\"status\":\"ok\"}";
			}
			else {
				return "{\"status\":\"not ok\"}";
			}
		}
}
