package it.unipv.payroll.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PasswordManager {

	private String str;
	private int randInt;
	private StringBuilder sb;
	private List<Integer> l;

	public PasswordManager() {
		l = new ArrayList<Integer>();
		for (int i = 33; i < 127; i++) {
			l.add(i);
		}

		l.remove(new Integer(34));
		l.remove(new Integer(47));
		l.remove(new Integer(92));
	}

	public String generatePassword() {
		sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			randInt = l.get(new SecureRandom().nextInt(91));
			sb.append((char) randInt);
		}

		str = sb.toString();

		return str;
	}

	public String hashIt(String password) {
		MessageDigest md;
		String passwordHash;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] passwordBytes = password.getBytes();
			byte[] hash = md.digest(passwordBytes);
			passwordHash= Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			passwordHash=null;
		}
		
		return passwordHash;
	}
}
