package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.ItemInventario;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by NeN on 19/04/2016.
 */
public enum InventarioDAO {
    INSTANCE; //para implementar el patron singleton.
	private SessionFactory sessionFactory;
	
    public List<ItemInventario> getInventario() {
    	List<ItemInventario> inventario = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			inventario = (List<ItemInventario>) session.createQuery("from com.chicajimenez.emilio.modelo.ItemInventario p").list();
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
        return inventario;
    }

    public boolean addItem(ItemInventario item){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(item);
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
    public ItemInventario getItem(long idItem){
    	ItemInventario itemInventario = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			itemInventario = (ItemInventario) session
					.createQuery("from com.chicajimenez.emilio.modelo.ItemInventario p where p.id = :ID")
					.setParameter("ID", idItem)
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

        return itemInventario;
    }
    public boolean delItem(long idItem){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.createQuery("delete com.chicajimenez.emilio.modelo.ItemInventario p where p.id = :ID")
			.setParameter("ID", idItem)
	        .executeUpdate();
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
    public List<ItemInventario> getItemsPorDpt(long idDpt){
    	List<ItemInventario> itemPorDpt = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			itemPorDpt = (List<ItemInventario>) 
					session.createQuery("from com.chicajimenez.emilio.modelo.ItemInventario p where p.departamento.id = :ID")
					.setParameter("ID", idDpt).list();
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
        return itemPorDpt;
    }
    public List<ItemInventario> getItemsPorProd(long idProd){
    	List<ItemInventario> itemPorProd = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			itemPorProd = (List<ItemInventario>) 
					session.createQuery("from com.chicajimenez.emilio.modelo.ItemInventario p where p.producto.id = :ID")
					.setParameter("ID", idProd).list();
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
        return itemPorProd;
    }
    public List<ItemInventario> getItemsPorFarmacia(long idF){
    	List<ItemInventario> itemPorProd = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			itemPorProd = (List<ItemInventario>) 
					session.createQuery("from com.chicajimenez.emilio.modelo.ItemInventario p where p.farmacia.id = :ID")
					.setParameter("ID", idF).list();
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
        return itemPorProd;
    }
    public ItemInventario getItemPorFarmaciaProd(long idF,long idP){
    	ItemInventario i =null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			i = (ItemInventario) 
					session.createQuery("from com.chicajimenez.emilio.modelo.ItemInventario "
							+ "p where p.farmacia.id = :ID AND p.producto.id= :id_prod")
					.setParameter("ID", idF)
					.setParameter("id_prod", idP).uniqueResult();;
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
        return i;
    }
    private InventarioDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }
}
