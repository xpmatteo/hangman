package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;



public class UserController {

	private UserBase users;

	public UserController(UserBase users) {
		this.users = users;
	}

	public void handleRequest(WebRequest request, WebResponse response) {
		String path = request.getRequestPath();
		boolean isGet = request.isGet();
		boolean isPost = request.isPost();

		PasswordProtectionFilter filter = new PasswordProtectionFilter(users);
		filter.applyTo(request, response);
		if (!filter.shouldContinue())
			return;

		if (isIndex(path) && isGet) {
			index(response);
		} else if (isUsers(path) && isGet) {
			response.methodNotAllowed("Use POST on /users to create a user");
		} else if (path.matches("^/users/[a-f0-9]+/prisoners/[a-f0-9]+") && isGet) {
			getOnePrisoner(request, response);
		} else if (path.matches("^/users/[a-f0-9]+/prisoners/[a-f0-9]+")) {
			guess(request, response);
		} else if (path.matches("^/users/[a-f0-9]+/prisoners") && isGet) {
			getPrisoners(request, response);
		} else if (path.matches("^/users/[a-f0-9]+/prisoners")) {
			createNewPrisoner(request, response);
		} else if (path.startsWith("/users/") && isGet) {
			getUsers(request, response);
		} else if (isPost) {
			createNewUser(request, response);
		}
	}

	private boolean isUsers(String path) {
		return path.equals("/users");
	}

	private boolean isIndex(String path) {
		return path.equals("/");
	}

	private boolean needsPassword(String path) {
		return path.matches("^/users/[a-f0-9]+.*");
	}

	private boolean passwordDoesNotMatch(WebRequest request) {
		return !users.contains(request.getUserId(), request.getParameter("password"));
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
		response.created(request.getRequestPath());
	}

	private void getOnePrisoner(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		String prisonerId = request.getPrisonerId();
		response.put("url", "/users/" + userId + "/prisoners/" + prisonerId);
		response.put("prisoner", users.findPrisoner(userId, prisonerId));
	}

	private void getPrisoners(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("url", "/users/" + userId + "/prisoners");
		response.put("items", users.findPrisoners(userId));
	}

	private void index(WebResponse response) {
		response.put("users", "/users");
		response.put("index", "/");
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

	private void createNewPrisoner(WebRequest request, WebResponse response) {
		String prisonerId = users.getNextUserId();
		String path = request.getRequestPath() + "/" + prisonerId;

		response.created(path);
		users.addPrisoner(request.getUserId(), new Prisoner(prisonerId, new WordList().getRandomWord()));
	}

	private void getUsers(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("prisoners", "/users/" + userId + "/prisoners");
		response.put("id", userId);
		response.put("url", "/users/" + userId);
	}

	private void forbidden(WebResponse response) {
		response.forbidden("You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.");
	}
}
