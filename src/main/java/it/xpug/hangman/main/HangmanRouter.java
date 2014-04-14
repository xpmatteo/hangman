package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;
import it.xpug.hangman.web.*;



public class HangmanRouter {

	private UserBase users;

	public HangmanRouter(UserBase users) {
		this.users = users;
	}

	public void handleRequest(WebRequest request, WebResponse response) {
		String path = request.getPath();

		PasswordProtectionFilter filter = new PasswordProtectionFilter(users);
		filter.service(request, response);
		if (!filter.shouldContinue())
			return;

		if (path.matches("/")) {
			new IndexResource().service(request, response);
		} else if (path.matches("/users")) {
			new UsersCollectionResource(users).service(request, response);
		} else if (path.matches("/users/[a-f0-9]+/prisoners/[a-f0-9]+")) {
			new PrisonerResource(users).service(request, response);
		} else if (path.matches("/users/[a-f0-9]+/prisoners")) {
			new PrisonersCollectionResource(users).service(request, response);
		} else if (path.matches("/users/[a-f0-9]+")) {
			new UserResource().service(request, response);
		}
	}

}
