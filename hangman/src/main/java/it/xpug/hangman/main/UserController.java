package it.xpug.hangman.main;

import java.util.*;


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
		} else if (uri.matches("^/users/[a-f0-9]+/prisoners") && isGet) {
			getPrisoners(request, response);
		} else if (uri.matches("^/users/[a-f0-9]+/prisoners")) {
			createNewPrisoner(request, response);
		} else if (uri.startsWith("/users/") && isGet) {
			getUsers(request, response);
		} else if (isPost) {
			createNewUser(request, response);
		}
	}

	private void getPrisoners(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("url", "/users/" + userId + "/prisoners");
		response.put("items", new ArrayList<Prisoner>());
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

	private void createNewPrisoner(WebRequest request, WebResponse response) {
		String newUserId = users.getNextUserId();
		String path = request.getRequestURI() + "/" + newUserId;

		if (null == request.getParameter("password")) {
			forbidden(response);
		} else {
			response.redirect(path);
//			users.add(new UserId(newUserId), "a name", request.getParameter("password"));
		}
	}

	private void getUsers(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		if (!users.contains(userId, request.getParameter("password"))) {
			forbidden(response);
			return;
		}
		response.put("prisoners", "/users/" + userId + "/prisoners");
		response.put("id", userId);
		response.put("url", "/users/" + userId);
	}

	private void forbidden(WebResponse response) {
		response.forbidden("You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.");
	}
}
