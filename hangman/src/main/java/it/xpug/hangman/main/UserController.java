package it.xpug.hangman.main;

import static java.lang.String.*;

import java.util.*;

import javax.servlet.http.*;

public class UserController {

	private UserBase users;

	public UserController(UserBase users) {
		this.users = users;
	}

	public void handleRequest(HttpServletRequest request, HttpServletResponse response, Map<Object, Object> map) {
		String uri = request.getRequestURI();
		String method = request.getMethod().toLowerCase();
		boolean isGet = method.equals("get");
		boolean isPost = method.equals("post");

		if (uri.equals("/") && isGet) {
			index(map);
		} else if (uri.equals("/users") && isGet) {
			getUsersNotAllowed(response, map);
		} else if (uri.startsWith("/users/") && isGet) {
			handleGetUsers(request, response, map);
		} else if (isPost) {
			createNewUser(request, response, map);
		}
	}

	private void getUsersNotAllowed(HttpServletResponse response, Map<Object, Object> map) {
		map.put("description", "Use POST on /users to create a user");
		map.put("status", "Method not allowed");
		map.put("status_code", 405);
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	private void index(Map<Object, Object> map) {
		map.put("users", "/users");
		map.put("index", "/");
		map.put("prisoners", "/prisoners");
	}

	private void createNewUser(HttpServletRequest request, HttpServletResponse response, Map<Object, Object> map) {
		String newUserId = users.getNextUserId();
		String path = "/users/" + newUserId;
		map.put("location", path);
		map.put("status", "See other");
		map.put("status_code", HttpServletResponse.SC_SEE_OTHER);
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location", location(request, path));

		users.add("a name", request.getParameter("password"), newUserId);
	}

	private void handleGetUsers(HttpServletRequest request, HttpServletResponse response, Map<Object, Object> map) {
		String userId = getUserId(request);
		if (!users.contains(userId, request.getParameter("password"))) {
			forbidden(response);
			map.put("status", "Forbidden");
			map.put("status_code", HttpServletResponse.SC_FORBIDDEN);
			map.put("description", "You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.");
			return;
		}
		map.put("prisoners", "/prisoners");
		map.put("id", userId);
		map.put("url", "/users/" + userId);
		map.put("name", request.getParameter("name"));
	}

	private void forbidden(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}

	private String getUserId(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return requestURI.substring(1+requestURI.lastIndexOf("/"));
	}

	private String location(HttpServletRequest request, String path) {
		return format("http://%s:%s%s", request.getServerName(), request.getLocalPort(), path);
	}
}
