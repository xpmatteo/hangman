package it.xpug.hangman.main;


import it.xpug.generic.web.*;


public class Main {
	public static void main(String[] args) {
		ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet(new UserBase()));
		app.start(8080, "src/main/webapp");
	}
}
