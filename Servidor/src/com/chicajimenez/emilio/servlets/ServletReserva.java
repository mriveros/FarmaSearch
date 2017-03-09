package com.chicajimenez.emilio.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.chicajimenez.emilio.dao.DepartamentoDAO;
import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.dao.InventarioDAO;
import com.chicajimenez.emilio.dao.ReservaDAO;
import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.ItemInventario;
import com.chicajimenez.emilio.modelo.LineaReserva;
import com.chicajimenez.emilio.modelo.Producto;
import com.chicajimenez.emilio.modelo.Reserva;
import com.chicajimenez.emilio.modelo.Usuario;
import com.chicajimenez.emilio.util.FileUpload;

/**
 * Servlet implementation class ServletFarmacia
 */
@WebServlet("/ServletReserva")
public class ServletReserva extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletReserva() {
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
		
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { e.printStackTrace(); }

		  System.out.println(jb.toString());
		  
		  JSONObject jObj=null;
			try {
				jObj = new JSONObject(jb.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			//Si el json de la reserva trae algunas propiedades que no sean mapeables se obvian
		    ObjectMapper mapper = new ObjectMapper().configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 // Desmapeo el json con los datos 
		    
		    Reserva r = mapper.readValue(jObj.toString(), Reserva.class);
		    int stock=0;
		    ItemInventario itemInventario=null;
		    List<Integer> conDisponibilidad = new ArrayList();
		    String disponibles="{\"disponibles\": {";
		    for(int i=0;i<r.getLineaReservas().size();++i){
		    	LineaReserva l = r.getLineaReservas().get(i);
		    	Producto p = l.getProducto();
		    	Farmacia f = l.getFarmacia();
		    	itemInventario = InventarioDAO.INSTANCE.getItemPorFarmaciaProd(f.getId(), p.getId());
		    	if(itemInventario.getStock()<l.getCantidad()){
		    		conDisponibilidad.add(-1);
		    		r.delLinea(l);
		    		i--;
		    		disponibles+="\""+p.getId()+"\": -1 ";
		    	}else{
		    		conDisponibilidad.add(1);
		    		disponibles+="\""+p.getId()+"\": 1 ";
		    		itemInventario.setStock(itemInventario.getStock()-l.getCantidad());
		    		InventarioDAO.INSTANCE.addItem(itemInventario);
		    		
		    	}
		    	
		    	if(i!=r.getLineaReservas().size()-1)
	    			disponibles+=",";
		    	
		    }
		    	
		    long id=-1;
		    int i=0;
		    for(i=0;i<conDisponibilidad.size() && conDisponibilidad.get(i)==-1;++i);
		    if(i!=conDisponibilidad.size())
		    	id= ReservaDAO.INSTANCE.addReserva(r);
		    
		    
		    List<ItemInventario> inventario =InventarioDAO.INSTANCE.getInventario();
		    String jsonsInventario = mapper.writeValueAsString(inventario);
		    
		    
		    disponibles+="}, \"reserva\":"+id+",\"inventario\":"+jsonsInventario+" }";
		    PrintWriter out = response.getWriter().append(disponibles);
		    out.print("");
		    out.close();
		
		
	}
	
}
