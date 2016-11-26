package it.unipv.payroll.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unipv.payroll.model.Login;

@Stateless
public class LoginDAO {

	@PersistenceContext
	EntityManager em;
	
	public void add(Login aLogin) {
		em.persist(aLogin);
	}

	public void remove(Login aLogin) {
		em.remove(em.find(Login.class, aLogin.getHashUsername()));
	}

	public void update(Login aLogin) {
		em.merge(aLogin);
		
	}

	public Login getLoginCredentials(String hashUsername) {
		return em.find(Login.class, hashUsername);
		
	}
}
