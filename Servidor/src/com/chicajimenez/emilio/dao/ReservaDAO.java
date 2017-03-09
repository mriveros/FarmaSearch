package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.Reserva;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public enum ReservaDAO {
    INSTANCE; //para implementar el patron singleton.
	private SessionFactory sessionFactory;

    public List<Reserva> getReservas() {
    	List<Reserva> reservas = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			reservas = (List<Reserva>) session.createQuery("from com.chicajimenez.emilio.modelo.Reserva p").list();
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
        return reservas;
    }
    
    public List<Reserva> getReservas(long id) {
    	List<Reserva> reservas = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			reservas = (List<Reserva>) session.createQuery("from com.chicajimenez.emilio.modelo.Reserva p INNER JOIN "
					+ "com.chicajimenez.emilio.modelo.LineaReserva l on p.id= l.reserva.id where l.farmacia.id= :ID")
					.setParameter("ID", id)
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
        return reservas;
    }

    public long addReserva(Reserva reserva){
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(reserva);
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
		return reserva.getId();
    }
    public Reserva getReserva(long idReserva){
    	Reserva reserva = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			reserva = (Reserva) session
					.createQuery("from com.chicajimenez.emilio.modelo.Reserva p where p.id = :ID")
					.setParameter("ID", idReserva)
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

        return reserva;
    }
    public boolean delReserva(long idReserva){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery("delete com.chicajimenez.emilio.modelo.Reserva p where p.id = :ID")
					.setParameter("ID", idReserva)
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
    private ReservaDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }
}
