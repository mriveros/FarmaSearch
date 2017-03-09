package com.chicajimenez.emilio.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chicajimenez.emilio.dao.UsuarioDAO;
import com.chicajimenez.emilio.modelo.Usuario;
import com.chicajimenez.emilio.util.Cifrador;

@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				HttpSession sesion = request.getSession();
				String email = request.getParameter("email");
		        String password = request.getParameter("password");

		        password = Cifrador.cifrar(password,Cifrador.SHA1);

		        Usuario usuario = UsuarioDAO.INSTANCE.getUsuarioPorEmailYPassword(email, password);
		        
		        if (usuario != null) {
		        	sesion.setAttribute("id_usuario", usuario.getId());
		        	sesion.setAttribute("nombre", usuario.getNombre());
		        	sesion.setAttribute("email", usuario.getEmail());
		            response.sendRedirect("inicio.jsp");
		        }
		        else {
		            request.getSession().setAttribute("error", "El usuario no existe");
		            response.sendRedirect("login.jsp");
		        }
	}		       
}
