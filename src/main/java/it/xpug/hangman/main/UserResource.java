package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;

public class UserResource {

	public void service(WebRequest request, WebResponse response) {
		getUsers(request, response);
	}

	private void getUsers(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("prisoners", "/users/" + userId + "/prisoners");
		response.put("id", userId);
		response.put("url", "/users/" + userId);
	}

}
