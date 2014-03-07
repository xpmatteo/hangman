package it.xpug.hangman.main;

import java.util.regex.*;


public class UserController {

	private UserBase users;

	public UserController(UserBase users) {
		this.users = users;
	}

	public void handleRequest(WebRequest request, WebResponse response) {
		String uri = request.getRequestURI();
		String method = request.getMethod().toLowerCase();
		boolean isGet = method.equals("get");
		boolean isPost = method.equals("post");

		if (uri.equals("/") && isGet) {
			index(response);
		} else if (uri.equals("/users") && isGet) {
			response.methodNotAllowed("Use POST on /users to create a user");
		} else if (uri.matches("^/users/\\d+/prisoners") && isGet) {
			getPrisoners(request, response);
		} else if (uri.startsWith("/users/") && isGet) {
			getUsers(request, response);
		} else if (isPost) {
			createNewUser(request, response);
		}
	}

	private void getPrisoners(WebRequest request, WebResponse response) {
		UserId userId = getUserId(request);
		response.put("url", "/users/" + userId + "/prisoners");
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
			response.redirect(path);
			users.add(new UserId(newUserId), "a name", request.getParameter("password"));
		}
	}

	private void getUsers(WebRequest request, WebResponse response) {
		UserId userId = getUserId(request);
		if (!users.contains(userId, request.getParameter("password"))) {
			response.forbidden("You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.");
			return;
		}
		response.put("prisoners", "/users/" + userId + "/prisoners");
		response.put("id", userId);
		response.put("url", "/users/" + userId);
	}

	private UserId getUserId(WebRequest request) {
		Matcher matcher = Pattern.compile("/users/(\\d+)").matcher(request.getRequestURI());
		matcher.find();
		return new UserId(matcher.group(1));
	}
}
