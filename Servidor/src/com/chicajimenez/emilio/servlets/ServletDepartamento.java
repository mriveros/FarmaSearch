package com.chicajimenez.emilio.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.codehaus.jackson.map.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.chicajimenez.emilio.dao.DepartamentoDAO;
import com.chicajimenez.emilio.dao.FarmaciaDAO;
import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.modelo.Usuario;
import com.chicajimenez.emilio.util.FileUpload;

/**
 * Servlet implementation class ServletFarmacia
 */
@WebServlet("/ServletDepartamento")
@MultipartConfig
public class ServletDepartamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDepartamento() {
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
		
		Departamento d=new Departamento();
		d.setNombre(request.getParameter("nombre"));
		if(request.getParameter("id")!=null && !request.getParameter("id").equals(""))
			d.setId(Long.parseLong(request.getParameter("id")));
		d.setDescripcion(request.getParameter("descripcion"));
		String[] farmacias = request.getParameterValues("id_farmacia");
		Farmacia f=null;
		if(farmacias!=null)
			for(int i=0;i<farmacias.length;++i){
				f = FarmaciaDAO.INSTANCE.getFarmacia(Long.parseLong(farmacias[i]));
				if(f!=null)
					d.addFarmacia(f);			
			}
		
		long id_usuario =Long.parseLong( request.getParameter("id_usuario"));
		Usuario u = UsuarioDAO.INSTANCE.getUsuario(id_usuario);
		d.setUsuario(u);
		///Para guardar la imagen en el servidor
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		final PrintWriter out = response.getWriter();

		final Part filePart = request.getPart("imagen");

		final String fileName = FileUpload.getFileName(filePart);
		if(fileName!=null && !fileName.equals("")){
		///Establezco el nombre de la imagen para guardarla en la base de datos
			d.setImagen(fileName);
	
			final String serverFilePath = getUploadFolder() + File.separator + fileName;
	
			long timeBefore = System.currentTimeMillis();
		
			String writeMethod = request.getParameter("writemethod");
	
			if (writeMethod!=null && writeMethod.equals("partwrite")) {
	
				out.println("<br>Using Part.write method...");
	
				filePart.write(serverFilePath);
	
			} else {
	
				out.println("<br>Using Part.write method...");
	
				FileUpload.writeToFileUsingFileOutputStream(filePart.getInputStream(),
	
						serverFilePath);
	
			}
	
			long timeAfter = System.currentTimeMillis();
	
			out.println("<br>New file " + fileName + " created at " + serverFilePath);
	
			out.println("<br>Time elapsed= " + (timeAfter - timeBefore));
		}
		DepartamentoDAO.INSTANCE.addDepartameno(d);
		
		
		
	}
	private static final String uploadFolderName = "imgDepartamentos";

	private static volatile String uploadFolder = null;

	private String getUploadFolder() { // synchronization

		// not handled.

		if (uploadFolder == null) {

			String contextRealPath = getServletContext().getRealPath("/");

			uploadFolder = contextRealPath + File.separator + uploadFolderName;

			File dir = new File(uploadFolder);

			if (!dir.exists()) {

				boolean create = dir.mkdir();

				if (create) {

					System.out.println("Uploads directory created:" + uploadFolder);

				} else {

					throw new RuntimeException("Directory Cannot Be Created!");

				}

			}

		}

		return uploadFolder;

	}

}
