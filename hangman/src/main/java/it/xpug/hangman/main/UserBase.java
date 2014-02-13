package it.xpug.hangman.main;

import java.util.*;

public class UserBase {

	List<User> users = new ArrayList<>();
	private String password;
	private Random random;
	private UserId userId;

	public UserBase(Random random) {
		this.random = random;
	}

	public UserBase() {
		this(new Random());
	}

	public void add(String name, String password, UserId userId) {
		this.password = password;
		this.userId = userId;
	}

	public boolean contains(UserId userId, String password) {
		return this.password.equals(password) && this.userId.equals(userId);
	}

	public String getNextUserId() {
		return Long.toHexString((Math.abs(random.nextInt())));
	}

}
