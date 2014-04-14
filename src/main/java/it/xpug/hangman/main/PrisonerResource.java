package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;

public class PrisonerResource {

	private UserBase users;

	public PrisonerResource(UserBase users) {
		this.users = users;
	}

	public void service(WebRequest request, WebResponse response) {
		if (request.isGet())
			getOnePrisoner(request, response);
		else
			guess(request, response);
	}

	private void getOnePrisoner(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		String prisonerId = request.getPrisonerId();
		response.put("url", "/users/" + userId + "/prisoners/" + prisonerId);
		response.put("prisoner", users.findPrisoner(userId, prisonerId));
	}

	private void guess(WebRequest request, WebResponse response) {
		if (null == request.getParameter("guess")) {
			response.validationError("Parameter 'guess' is required");
			return;
		}
		UserId userId = request.getUserId();
		String prisonerId = request.getPrisonerId();
		Prisoner prisoner = users.findPrisoner(userId, prisonerId);
		prisoner.guess(request.getParameter("guess"));
		response.created(request.getPath());
	}
}
