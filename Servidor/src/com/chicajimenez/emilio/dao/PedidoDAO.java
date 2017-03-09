package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.Pedido;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public enum PedidoDAO {
    INSTANCE; //para implementar el patron singleton.
	private SessionFactory sessionFactory;

    public List<Pedido> getPedidos() {
    	List<Pedido> pedidos = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			pedidos = (List<Pedido>) session.createQuery("from com.chicajimenez.emilio.modelo.Pedido p").list();
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
        return pedidos;
    }

    public boolean addPedido(Pedido pedido){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(pedido);
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
    public Pedido getPedido(long idPedido){
    	Pedido pedido = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			pedido = (Pedido) session
					.createQuery("from com.chicajimenez.emilio.modelo.Pedido p where p.id = :ID")
					.setParameter("ID", idPedido)
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

        return pedido;
    }
    public boolean delPedido(long idPedido){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(idPedido);
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
    private PedidoDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }
}
