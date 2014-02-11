package it.xpug.hangman.main;

import java.util.*;

public class UserBase {

	List<User> users = new ArrayList<>();
	private String password;

	public void add(String name, String password, String id) {
		this.password = password;
	}

	public boolean contains(String userId, String password) {
		return this.password.equals(password);
	}

}
