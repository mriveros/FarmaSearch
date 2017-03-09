package com.chicajimenez.emilio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chicajimenez.emilio.modelo.Departamento;
import com.chicajimenez.emilio.modelo.Farmacia;
import com.chicajimenez.emilio.util.HibernateUtil;

/**
 * Created by Emilio Chica Jimenez on 05/04/2016.
 */
public enum  DepartamentoDAO {
    INSTANCE; //para implementar el patron singleton.
	private SessionFactory sessionFactory;
	
    public List<Departamento> getDepartamentos() {
    	List<Departamento> departamentos = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			departamentos = (List<Departamento>) session.createQuery("from com.chicajimenez.emilio.modelo.Departamento p").list();
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
        return departamentos;
    }
    public List<Departamento> getDepartamentosUsuario(long id_usuario) {
    	List<Departamento> departamentos = null;
    	Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			departamentos = (List<Departamento>) session.createQuery("from com.chicajimenez.emilio.modelo.Departamento p where p.usuario.id= :ID")
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
        return departamentos;
    }

    public boolean addDepartameno(Departamento departameno){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(departameno);
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
    public Departamento getDepartamento(long idDepartamento){
    	Departamento departamento = null;
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			departamento = (Departamento) session
					.createQuery("from com.chicajimenez.emilio.modelo.Departamento p where p.id = :ID")
					.setParameter("ID", idDepartamento)
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

        return departamento;
    }
    public boolean delDepartamento(long idDepartamento){
    	Session session = null;
		boolean hasErrors = false;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.createQuery("delete com.chicajimenez.emilio.modelo.Departamento p where p.id = :ID")
			.setParameter("ID", idDepartamento)
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
    private DepartamentoDAO(){
    	sessionFactory  = HibernateUtil.getSessionFactory();
    }

}
