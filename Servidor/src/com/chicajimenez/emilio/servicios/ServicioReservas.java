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
import com.chicajimenez.emilio.dao.ReservaDAO;
import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.ItemInventario;
import com.chicajimenez.emilio.modelo.Pedido;
import com.chicajimenez.emilio.modelo.Producto;
import com.chicajimenez.emilio.modelo.Reserva;
import com.chicajimenez.emilio.modelo.Usuario;



@Path("/servicioR")
public class ServicioReservas {
	

	// Devuelve un reserva por ID en JSON
	@GET
	@Path("/Reserva/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Reserva getReservaByIdJSON(@PathParam("id") long id){
		return ReservaDAO.INSTANCE.getReserva(id);
	}
	
	
	// Devuelve los reservas en JSON
	@GET
	@Path("/Reservas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reserva> getAllReservasInJSON(){
		return ReservaDAO.INSTANCE.getReservas();
	}
	

	// Devuelve los reservas en JSON por farmacia
	@GET
	@Path("/Reservas/{idfarmacia}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reserva> getAllReservasPorFInJSON(@PathParam("idfarmacia") long id){
		return ReservaDAO.INSTANCE.getReservas(id);
	}
	
	
}
