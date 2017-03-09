package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.Reserva;
import com.chicajimenez.emilio.modelo.Usuario;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public enum UsuarioDAO {
    INSTANCE; //para implementar el patron singleton.
	private SessionFactory sessionFactory;

    public List<Usuario> getUsuarios() {
    	List<Usuario> usuarios = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			usuarios = (List<Usuario>) session.createQuery("from com.chicajimenez.emilio.modelo.Usuario p").list();
			session.getTransaction().commit();
		}
		catch(Exception e){
			if(session != null)
				session.getTransaction().rollback();
		}
		finally {
			if(session != null)
				session.close();
		}
        return usuarios;
    }

    public boolean addUsuario(Usuario usuario){
    	Session session = null;
		boolean correcto = true;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(usuario);
			session.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if(session != null)
				session.getTransaction().rollback();
			
			correcto = false;
		}
		finally {
			if(session != null)
				session.close();
		}	
		return correcto;
    }
    public Usuario getUsuario(long idUser){
    	Usuario usuario = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			usuario = (Usuario) session
					.createQuery("from com.chicajimenez.emilio.modelo.Usuario p where p.id = :ID")
					.setParameter("ID", idUser)
					.uniqueResult();
			session.getTransaction().commit();
		}
		catch(Exception e){
			if(session != null)
				session.getTransaction().rollback();
		}
		finally {
			if(session != null)
				session.close();
		}

        return usuario;
    }
    public Usuario getUsuarioPorEmailYPassword(String email,String password){
    	Usuario usuario = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			usuario = (Usuario) session
					.createQuery("from com.chicajimenez.emilio.modelo.Usuario p where p.email = :email and password= :password")
					.setParameter("email", email)
					.setParameter("password", password)
					.uniqueResult();
			session.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if(session != null)
				session.getTransaction().rollback();
		}
		finally {
			if(session != null)
				session.close();
		}

        return usuario;
    }
    public boolean delUsuario(long idUser){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery("delete com.chicajimenez.emilio.modelo.Usuario p where p.id = :ID")
					.setParameter("ID", idUser)
			        .executeUpdate();
			session.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			if(session != null)
				session.getTransaction().rollback();
			
			hasErrors = true;
		}
		finally {
			if(session != null)
				session.close();
		}	
		return hasErrors;
    }
    private UsuarioDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }
}
