package it.xpug.hangman.web;

import it.xpug.hangman.domain.*;

public class PasswordProtectionFilter implements Filter {

	private UserBase users;
	private boolean shouldContinue = true;

	public PasswordProtectionFilter(UserBase users) {
		this.users = users;
	}

	public void service(WebRequest request, WebResponse response) {
		if (needsPassword(request.getPath()) && passwordNotProvided(request)) {
			unauthorized(response);
			shouldContinue = false;
		} else if (needsPassword(request.getPath()) && passwordDoesNotMatch(request)) {
			forbidden(response);
			shouldContinue = false;
		}
	}

	private boolean passwordNotProvided(WebRequest request) {		
		return null == request.getCredentials();
	}

	private boolean needsPassword(String path) {
		return path.matches("^/users/[a-f0-9]+.*");
	}

	private boolean passwordDoesNotMatch(WebRequest request) {
		return !users.contains(request.getUserId(), request.getPassword());
	}

	private void forbidden(WebResponse response) {
		response.forbidden("You don't have the permission to access the requested resource.");
	}

	private void unauthorized(WebResponse response) {
		response.unauthorized("You don't have the permission to access the requested resource.");
	}

	public boolean shouldContinue() {
		return shouldContinue ;
	}
}
