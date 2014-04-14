package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;

public class UserResource extends Resource {

	@Override
	public void doGet(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("prisoners", "/users/" + userId + "/prisoners");
		response.put("id", userId);
		response.put("url", "/users/" + userId);
	}

}
