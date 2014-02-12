package it.xpug.hangman.main;

import java.util.*;

public class UserBase {

	List<User> users = new ArrayList<>();
	private String password;
	private Random random;

	public UserBase(Random random) {
		this.random = random;
	}

	public UserBase() {
		this(new Random());
	}

	public void add(String name, String password, String id) {
		this.password = password;
	}

	public boolean contains(String userId, String password) {
		return this.password.equals(password);
	}

	public String getNextUserId() {
		return Long.toHexString((Math.abs(random.nextInt())));
	}

}
