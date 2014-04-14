package it.xpug.hangman.main;

public class IndexResource {

	public void service(WebRequest request, WebResponse response) {
		response.put("users", "/users");
		response.put("index", "/");
	}

}
