package controller;

import org.apache.commons.codec.digest.DigestUtils;

import model.DatabaseConnection;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_256;

public class ArenaWebBridge{

	DatabaseConnection db;
	public ArenaWebBridge() {
	}

	public void createUser(String username, String email, String password) {
		System.out.println("Creating User: "+username);
		
		db = new DatabaseConnection("jdbc:mysql://67.205.191.64/arena","root", "arenasb");
		String hashPassword = new DigestUtils(SHA_256).digestAsHex(password);
		db.createUser(username, email, hashPassword);
	}
	
	public void loginUser(String username, String password) {
		System.out.println("Username: "+username+"\nPassword: "+password);
		
		db = new DatabaseConnection("jdbc:mysql://67.205.191.64/arena","root", "arenasb");
		String hashPassword = new DigestUtils(SHA_256).digestAsHex(password);
		db.loginUser(username, hashPassword);
	}
}
