package com.chicajimenez.emilio.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

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

/**
 * Servlet implementation class ServletFarmacia
 */
@WebServlet("/ServletBD")
public class ServletBD extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletBD() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			List<ItemInventario> inventario =InventarioDAO.INSTANCE.getInventario();
			List<Farmacia> farmacias =FarmaciaDAO.INSTANCE.getFarmacias();
			List<Producto> productos =ProductoDAO.INSTANCE.getProductos();
			List<Usuario> usuarios =UsuarioDAO.INSTANCE.getUsuarios();
			List<Departamento> departamentos =DepartamentoDAO.INSTANCE.getDepartamentos();
			List<Reserva> reservas =ReservaDAO.INSTANCE.getReservas();
			List<Pedido> pedidos =PedidoDAO.INSTANCE.getPedidos();

			
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.disable(Feature.FAIL_ON_NULL_FOR_PRIMITIVES);
			
			String jsonProductos = mapper.writeValueAsString(productos);
			String jsonDepartamentos = mapper.writeValueAsString(departamentos);
			String jsonsFarmacias= mapper.writeValueAsString(farmacias);
			String jsonsInventario = mapper.writeValueAsString(inventario);
			String jsonsUsuarios = mapper.writeValueAsString(usuarios);
			String jsonsReservas = mapper.writeValueAsString(reservas);
			String jsonsPedidos = mapper.writeValueAsString(pedidos);
			/*System.out.println("{\"productos\":"+jsonProductos+",\"departamentos\":"+jsonDepartamentos+","
					+ "\"farmacias\":"+jsonsFarmacias +",\"inventario\":"+jsonsInventario+"],"
					+ "\"usuarios\":"+jsonsUsuarios+",\"reservas\":"+jsonsReservas+",\"pedidos\":"+jsonsPedidos+"}");*/
			
			response.getWriter().append("{\"productos\":"+jsonProductos+",\"departamentos\":"+jsonDepartamentos+","
					+ "\"farmacias\":"+jsonsFarmacias +",\"inventario\":"+jsonsInventario+","
							+ "\"usuarios\":"+jsonsUsuarios+",\"reservas\":"+jsonsReservas+",\"pedidos\":"+jsonsPedidos+"}");
		    
			PrintWriter out = response.getWriter();
		    out.print("");
		    out.close();
		
	}

}
