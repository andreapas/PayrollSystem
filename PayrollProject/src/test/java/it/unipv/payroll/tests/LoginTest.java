package it.unipv.payroll.tests;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.payroll.controller.CredentialsController;
import it.unipv.payroll.dao.CredentialsDAO;
import it.unipv.payroll.model.Credentials;
import it.unipv.payroll.view.CredentialsBean;

@RunWith(Arquillian.class)
public class LoginTest extends ArquillianTest {

	private static String USER1 = "ABC001DEF";
	private static String PASSWORD1 = "oh_no_a_wild_password_appeared!AAARG11110101101000110101110010101010";

	@Inject
	CredentialsController logController;
	@Inject
	CredentialsDAO logDAO;
	@Inject
	CredentialsBean logBean;

	@After
	public void clean(){
		Credentials aLogin= new Credentials();
		aLogin.setUsername(USER1);
		aLogin.setPassword(hashIt(PASSWORD1));
		if(logDAO.find(USER1)!=null){
			logDAO.remove(aLogin.getUsername());
		}
	}
	@Test
	public void addLoginTest() {
		
		logBean.setCode(USER1);
		logBean.setPassword(hashIt(PASSWORD1));
		String answer = logBean.addLogin();

		Assert.assertTrue("Adding login credentials successfully completed", answer.equals("Operation completed successfully."));
	}
	
	
	@Test
	public void removeLoginTest() {
		
		logBean.setCode(USER1);
		logBean.setPassword(hashIt(PASSWORD1));

		logBean.addLogin();

		String answer = logBean.removeLogin();

		Assert.assertTrue("Remove login credentials successfully completed", answer.equals("Operation completed successfully."));

	}

	private String hashIt(String toHash) {
		byte[] userInBytes;
		byte[] digested = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			userInBytes = toHash.getBytes(StandardCharsets.UTF_8);
			digested = md.digest(userInBytes);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(digested);
	}

}
