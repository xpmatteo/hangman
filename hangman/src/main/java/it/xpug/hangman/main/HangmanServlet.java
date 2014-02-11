package it.xpug.hangman.main;

import static java.lang.String.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class HangmanServlet extends HttpServlet {

	private UserIdSequence userIdSequence;
	private UserBase users;

	public HangmanServlet(UserIdSequence userIdSequence, UserBase users) {
		this.userIdSequence = userIdSequence;
		this.users = users;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setContentType(response);

		Map<Object, Object> map = new HashMap<Object, Object>();
		String requestURI = request.getRequestURI();
		if (requestURI.equals("/")) {
			map.put("users", "/users");
			map.put("index", "/");
			map.put("prisoners", "/prisoners");
		} else if (requestURI.equals("/users")) {
			map.put("description", "Use POST on /users to create a user");
			map.put("status", "Method not allowed");
			map.put("status_code", 405);
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		} else if (requestURI.startsWith("/users/")) {
			handleGetUsers(request, map);
		}

		response.getWriter().write(JSON.toString(map));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setContentType(response);
	
		String newUserId = userIdSequence.next();
		Map<Object, Object> map = new HashMap<Object, Object>();
		String path = "/users/" + newUserId;
		map.put("location", path);
		map.put("status", "See other");
		map.put("status_code", HttpServletResponse.SC_SEE_OTHER);
		response.setStatus(HttpServletResponse.SC_SEE_OTHER);
		response.setHeader("Location", location(request, path));
		response.getWriter().write(JSON.toString(map));
	}

	private void handleGetUsers(HttpServletRequest request, Map<Object, Object> map) {
		String userId = getUserId(request);
		map.put("prisoners", "/prisoners");
		map.put("id", userId);
		map.put("url", "/users/" + userId);
		map.put("name", request.getParameter("name"));
	}

	private String getUserId(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String userId = requestURI.substring(1+requestURI.lastIndexOf("/"));
		return userId;
	}

	private String location(HttpServletRequest request, String path) {
		return format("http://%s:%s%s", request.getServerName(), request.getLocalPort(), path);
	}

	private void setContentType(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
