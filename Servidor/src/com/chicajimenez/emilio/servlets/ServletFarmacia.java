package com.chicajimenez.emilio.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.codehaus.jackson.map.ObjectMapper;
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
@WebServlet("/ServletFarmacia")
@MultipartConfig
public class ServletFarmacia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletFarmacia() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Farmacia f = new Farmacia();
		if(request.getParameter("id")!=null && !request.getParameter("id").equals(""))
		f.setId(Long.parseLong(request.getParameter("id")));
		f.setDescripcion(request.getParameter("descripcion"));
		f.setEmail(request.getParameter("email"));
		f.setLatitud(Float.parseFloat(request.getParameter("latitud")));
		f.setLongitud(Float.parseFloat(request.getParameter("longitud")));
		f.setNombre(request.getParameter("nombre"));
		f.setTelefono(request.getParameter("telefono"));
		long id_usuario =Long.parseLong( request.getParameter("id_usuario"));
		Usuario u = UsuarioDAO.INSTANCE.getUsuario(id_usuario);
		f.setUsuario(u);
		//u.addFarmacia(f);
		
		///Para subir el archivo al servidor
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		final PrintWriter out = response.getWriter();

		final Part filePart = request.getPart("imagen");
		
		final String fileName = FileUpload.getFileName(filePart);
		if(fileName!=null && !fileName.equals("")){
			///Establezco el nombre del archivo para cogerlo de la base de datos
			f.setImagen(fileName);
			
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
		FarmaciaDAO.INSTANCE.addFarmacia(f);

		

	}
	private static final String uploadFolderName = "imgFarmacias";

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
