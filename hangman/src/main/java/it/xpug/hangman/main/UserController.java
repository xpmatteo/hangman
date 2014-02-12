package it.xpug.hangman.main;

import javax.servlet.http.*;

public class UserController {

	private UserBase users;

	public UserController(UserBase users) {
		this.users = users;
	}

	public void handleRequest(HttpServletRequest request, JsonResponse response) {
		String uri = request.getRequestURI();
		String method = request.getMethod().toLowerCase();
		boolean isGet = method.equals("get");
		boolean isPost = method.equals("post");

		if (uri.equals("/") && isGet) {
			index(response);
		} else if (uri.equals("/users") && isGet) {
			response.methodNotAllowed("Use POST on /users to create a user");
		} else if (uri.startsWith("/users/") && isGet) {
			handleGetUsers(request, response);
		} else if (isPost) {
			createNewUser(request, response);
		}
	}

	private void index(JsonResponse response) {
		response.put("users", "/users");
		response.put("index", "/");
		response.put("prisoners", "/prisoners");
	}

	private void createNewUser(HttpServletRequest request, JsonResponse response) {
		String newUserId = users.getNextUserId();
		String path = "/users/" + newUserId;
		response.respondWithRedirect(path);

		users.add("a name", request.getParameter("password"), newUserId);
	}

	private void handleGetUsers(HttpServletRequest request, JsonResponse response) {
		String userId = getUserId(request);
		if (!users.contains(userId, request.getParameter("password"))) {
			response.forbidden("You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.");
			return;
		}
		response.put("prisoners", "/prisoners");
		response.put("id", userId);
		response.put("url", "/users/" + userId);
		response.put("name", request.getParameter("name"));
	}

	private String getUserId(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return requestURI.substring(1+requestURI.lastIndexOf("/"));
	}
}
