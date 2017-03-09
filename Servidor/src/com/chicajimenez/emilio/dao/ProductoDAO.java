package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Producto;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public enum ProductoDAO {
    INSTANCE; //para implementar el patron singleton.
	private SessionFactory sessionFactory;

    public List<Producto> getProductos() {
    	List<Producto> productos = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			productos = (List<Producto>) session.createQuery("from com.chicajimenez.emilio.modelo.Producto p").list();
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
        return productos;
    }
    public List<Producto> getProductosUsuario(long id_usuario) {
    	List<Producto> productos = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			productos = (List<Producto>) session.createQuery("from com.chicajimenez.emilio.modelo.Producto p where p.usuario.id= :ID")
					.setParameter("ID", id_usuario).list();
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
        return productos;
    }
    public List<Producto> getProductosNoInventariados(long id,long id_usuario) {
    	List<Producto> productos = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			productos = (List<Producto>) 
					session.createQuery("FROM com.chicajimenez.emilio.modelo.Producto po "
							+ "WHERE po.usuario.id= :id_usuario and po.id "
							+ "NOT IN(select p.id FROM com.chicajimenez.emilio.modelo.Producto p "
							+ "INNER JOIN com.chicajimenez.emilio.modelo.ItemInventario as i ON i.producto.id = p.id "
							+ "WHERE i.farmacia.id= :ID AND p.usuario.id= :id_usuario) ")
					.setParameter("ID", id)
					.setParameter("id_usuario", id_usuario)
					.list();
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
        return productos;
    }
    

    public boolean addProducto(Producto producto){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(producto);
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
    public Producto getProducto(long idP){
    	Producto producto = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			producto = (Producto) session
					.createQuery("from com.chicajimenez.emilio.modelo.Producto p where p.id = :ID")
					.setParameter("ID", idP)
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

        return producto;
    }
    public boolean delProducto(long idP){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery("delete com.chicajimenez.emilio.modelo.Producto p where p.id = :ID")
					.setParameter("ID", idP)
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
    private ProductoDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }
}
