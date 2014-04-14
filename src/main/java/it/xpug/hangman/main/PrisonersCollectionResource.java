package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;

public class PrisonersCollectionResource {

	private UserBase users;

	public PrisonersCollectionResource(UserBase users) {
		this.users = users;
	}

	public void service(WebRequest request, WebResponse response) {
		if (request.isGet())
			getPrisoners(request, response);
		else
			createNewPrisoner(request, response);
	}

	private void getPrisoners(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("url", "/users/" + userId + "/prisoners");
		response.put("items", users.findPrisoners(userId));
	}

	private void createNewPrisoner(WebRequest request, WebResponse response) {
		String prisonerId = users.getNextUserId();
		String path = request.getPath() + "/" + prisonerId;

		response.created(path);
		users.addPrisoner(request.getUserId(), new Prisoner(prisonerId, new WordList().getRandomWord()));
	}

}
