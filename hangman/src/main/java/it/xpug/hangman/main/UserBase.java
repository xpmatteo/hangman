package it.xpug.hangman.main;

import java.util.*;

public class UserBase {

	private List<User> users = new ArrayList<>();
	private Random random;

	public UserBase(Random random) {
		this.random = random;
	}

	public UserBase() {
		this(new Random());
	}

	public void add(UserId userId, String name, String password) {
		this.users.add(new User(userId, name, password));
	}

	public boolean contains(UserId userId, String password) {
		for (User user : users) {
			if (user.authenticates(userId, password)) {
				return true;
			}
		}
		return false;
	}

	public String getNextUserId() {
		return Long.toHexString((Math.abs(random.nextInt())));
	}

}
