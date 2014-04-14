package it.xpug.hangman.web;


public class IndexResource {

	public void service(WebRequest request, WebResponse response) {
		response.put("users", "/users");
		response.put("index", "/");
	}

}
