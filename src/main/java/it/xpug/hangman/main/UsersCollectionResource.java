package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;

public class UsersCollectionResource {

	private UserBase users;

	public UsersCollectionResource(UserBase users) {
		this.users = users;
	}

	public void service(WebRequest request, WebResponse response) {
		if (request.isGet())
			response.methodNotAllowed("Use POST on /users to create a user");
		else
			createNewUser(request, response);
	}

	private void createNewUser(WebRequest request, WebResponse response) {
		String newUserId = users.getNextUserId();
		String path = "/users/" + newUserId;

		if (null == request.getParameter("name")) {
			response.validationError("Parameter 'name' is required");
		} else if (null == request.getParameter("password")) {
			response.validationError("Parameter 'password' is required");
		} else {
			response.created(path);
			users.add(new UserId(newUserId), "a name", request.getParameter("password"));
		}
	}

}
