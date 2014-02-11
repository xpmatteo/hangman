package it.xpug.hangman.main;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class HangmanServlet extends HttpServlet {

	private UserIdSequence userIdSequence;

	public HangmanServlet(UserIdSequence userIdSequence, UserBase users) {
		this.userIdSequence = userIdSequence;
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
			map.put("prisoners", "/prisoners");
			map.put("id", "888");
			map.put("url", "/users/" + "888");
			map.put("name", request.getParameter("name"));
		}

		response.getWriter().write(JSON.toString(map));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setContentType(response);
		String newUserId = userIdSequence.next();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("prisoners", "/prisoners");
		map.put("id", newUserId);
		map.put("url", "/users/" + newUserId);
		map.put("name", request.getParameter("name"));
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.getWriter().write(JSON.toString(map));
	}

	private void setContentType(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
