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

import it.unipv.payroll.controller.LoginController;
import it.unipv.payroll.dao.LoginDAO;
import it.unipv.payroll.model.Login;
import it.unipv.payroll.view.LoginBean;

@RunWith(Arquillian.class)
public class LoginTest extends ArquillianTest {

	private static String USER1 = "ABC001DEF";
	private static String PASSWORD1 = "oh_no_una_password!AAARG11110101101000110101110010101010";

	@Inject
	LoginController logController;
	@Inject
	LoginDAO logDAO;
	@Inject
	LoginBean logBean;

	@After
	public void clean(){
		Login aLogin= new Login();
		aLogin.setHashUsername(hashIt(USER1));
		aLogin.setHashPassword(hashIt(PASSWORD1));
		if(logDAO.find(hashIt(USER1))!=null){
			logDAO.remove(aLogin.getHashUsername());
		}
	}
	@Test
	public void addLoginTest() {
		Login aLogin = new Login();
		aLogin.setHashUsername(hashIt(USER1));
		aLogin.setHashPassword(hashIt(PASSWORD1));

		logBean.setLogin(aLogin);

		String answer = logBean.addLogin();
		
		Assert.assertTrue("Adding login credentials successfully completed", answer.equals("Success"));

	}
	@Test
	public void loginTest() {
		Login aLogin = new Login();
		aLogin.setHashUsername(hashIt(USER1));
		aLogin.setHashPassword(hashIt(PASSWORD1));

		logBean.setLogin(aLogin);

		logBean.addLogin();

		String answer = logBean.login();

		Assert.assertTrue("Login successfully completed", answer.equals("Success"));

	}
	
	@Test
	public void removeLoginTest() {
		Login aLogin = new Login();
		aLogin.setHashUsername(hashIt(USER1));
		aLogin.setHashPassword(hashIt(PASSWORD1));

		logBean.setLogin(aLogin);

		logBean.addLogin();

		String answer = logBean.removeLogin();

		Assert.assertTrue("Remove login credentials successfully completed", answer.equals("Success"));

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