package it.xpug.hangman.main;

public class User {
	UserId id;
	String name;
	String password;

	public User(UserId id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public boolean authenticates(UserId userId, String password) {
		return this.password.equals(password) && this.id.equals(userId);
	}
}
