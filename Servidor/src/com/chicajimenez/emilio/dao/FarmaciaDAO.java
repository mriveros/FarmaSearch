package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public enum FarmaciaDAO {
    INSTANCE; //para implementar el patron singleton.
	 private SessionFactory sessionFactory;

    public List<Farmacia> getFarmacias() {
    	List<Farmacia> farmacias = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			farmacias = (List<Farmacia>) session.createQuery("from com.chicajimenez.emilio.modelo.Farmacia p").list();
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
        return farmacias;
    }
    public List<Farmacia> getFarmaciasUsuario(long id_usuario) {
    	List<Farmacia> farmacias = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			farmacias = (List<Farmacia>) session.createQuery("from com.chicajimenez.emilio.modelo.Farmacia p where p.usuario.id= :ID")
					.setParameter("ID", id_usuario).list();
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
        return farmacias;
    }

    public boolean addFarmacia(Farmacia farmacia){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(farmacia);
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
    public Farmacia getFarmacia(long idFarmacia){
    	Farmacia farmacia = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			farmacia = (Farmacia) session
					.createQuery("from com.chicajimenez.emilio.modelo.Farmacia p where p.id = :ID")
					.setParameter("ID", idFarmacia)
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

        return farmacia;
    }
    public boolean delFarmacia(long idFarmacia){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery("delete com.chicajimenez.emilio.modelo.Farmacia p where p.id = :ID")
			.setParameter("ID", idFarmacia)
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
    private FarmaciaDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }
}
