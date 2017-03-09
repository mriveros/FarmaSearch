package com.chicajimenez.emilio.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.chicajimenez.emilio.dao.DepartamentoDAO;
import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.dao.InventarioDAO;
import com.chicajimenez.emilio.dao.ProductoDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.ItemInventario;
import com.chicajimenez.emilio.modelo.Producto;

/**
 * Servlet implementation class ServletFarmacia
 */
@WebServlet("/ServletInventario")
public class ServletInventario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInventario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.print("SERVLET CONTESTANDO AL GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { e.printStackTrace(); }

		  System.out.println(jb.toString());
		  // 2. initiate jackson mapper
		  JSONObject jObj=null;
			try {
				jObj = new JSONObject(jb.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		    ItemInventario item=null;
		    Producto p=null;
		    Departamento d=null;
		    Farmacia f=null;
		    if(jObj!=null){
		    		long id = 0;
		    		long id_dpto=0;
		    		long id_prod=0;
		    		long id_far=0;
		    		float precio=0;
		    		int stock=0;
					try {
						if(jObj.has("id"))
							if(jObj.get("id").toString().compareTo("")!=0)
								id = jObj.getLong("id");
						if(jObj.get("id_departamento").toString().compareTo("")!=0)
							id_dpto = jObj.getLong("id_departamento");
						if(jObj.get("id_producto").toString().compareTo("")!=0)
							id_prod = jObj.getLong("id_producto");
						if(jObj.get("id_farmacia").toString().compareTo("")!=0)
							id_far= jObj.getLong("id_farmacia");
						if(jObj.has("stock"))
							stock = jObj.getInt("stock");
						if(jObj.has("precio"))
							precio = (float)jObj.getDouble("precio");
					} catch (JSONException e) {
						response.getWriter().append("{\"status\":\"not\"}");
						PrintWriter out = response.getWriter();
					    out.print("");
					    out.close();
						e.printStackTrace();
					}
					if(id_dpto!=0 && id_prod!=0 && id_far!=0){
			    		d = DepartamentoDAO.INSTANCE.getDepartamento(id_dpto);
			    		p = ProductoDAO.INSTANCE.getProducto(id_prod);
			    		f = FarmaciaDAO.INSTANCE.getFarmacia(id_far);
			    		item = new ItemInventario();
			    		if(id!=0)
			    			item.setId(id);
			    		item.setDepartamento(d);
			    		item.setFarmacia(f);
			    		item.setProducto(p);
			    		item.setPrecio(precio);
			    		item.setStock(stock);
					    InventarioDAO.INSTANCE.addItem(item);
					    
					    response.getWriter().append("{\"status\":\"ok\"}");
					}else
						response.getWriter().append("{\"status\":\"not\"}");
		    }else
		    	response.getWriter().append("{\"status\":\"not\"}");

		    
			PrintWriter out = response.getWriter();
		    out.print("");
		    out.close();
		
	}

}
