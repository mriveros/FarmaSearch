package com.chicajimenez.emilio.servicios;

import javax.ws.rs.PathParam;

import java.sql.Timestamp;
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
import com.chicajimenez.emilio.dao.PedidoDAO;
import com.chicajimenez.emilio.dao.ProductoDAO;
import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.ItemInventario;
import com.chicajimenez.emilio.modelo.Pedido;
import com.chicajimenez.emilio.modelo.Producto;
import com.chicajimenez.emilio.modelo.Usuario;



@Path("/servicioPe")
public class ServicioPedido {
	

	// Devuelve un pedido por ID en JSON
	@GET
	@Path("/Pedido/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Pedido getPedidoByIdJSON(@PathParam("id") long id){
		return PedidoDAO.INSTANCE.getPedido(id);
	}
	
	
	// Devuelve los pedidos en JSON
	@GET
	@Path("/Pedidos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pedido> getAllPedidoInJSON(){
		return PedidoDAO.INSTANCE.getPedidos();
	}
	
	// Añadir nuevo pedido - return JSON para ok o no ok
	@PUT
	@Path("/Pedido/{id_usuario}/{formapago}/{fecha}")
	@Produces(MediaType.APPLICATION_JSON)
	public String guardarPedido(@PathParam("id_usuario") long id_usuario,
			@PathParam("formapago") String formapago,
			@PathParam("fecha") Timestamp fecha){
		Pedido p = new Pedido();
		p.setFormaPago(formapago);
		p.setFecha(fecha);
		Usuario u = UsuarioDAO.INSTANCE.getUsuario(id_usuario);
		p.setUsuario(u);
		
		if(!PedidoDAO.INSTANCE.addPedido(p)){
			return "{\"status\":\"ok\"}";
		}
		else {
			return "{\"status\":\"not ok\"}";
		}
	}
	
	// Actualizar un item del inventario existente - return JSON para ok o no ok
		@PUT
		@Path("/Pedido/{id}/{stock}")
		@Produces(MediaType.APPLICATION_JSON)
		public String actualizaItem(@PathParam("id") long id,
				@PathParam("stock") int stock){
			ItemInventario i =InventarioDAO.INSTANCE.getItem(id);
			i.setStock(stock);
			if(!InventarioDAO.INSTANCE.addItem(i)){
				return "{\"status\":\"ok\"}";
			}
			else {
				return "{\"status\":\"not ok\"}";
			}
		}
		//Eliminar un item del inventario por id
		@DELETE
		@Path("/Pedido/{id}")
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
