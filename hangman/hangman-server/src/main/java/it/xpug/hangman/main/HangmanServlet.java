package it.xpug.hangman.main;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class HangmanServlet extends HttpServlet {

	private UserIdSequence userIdSequence;

	public HangmanServlet(UserIdSequence userIdSequence) {
		this.userIdSequence = userIdSequence;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setContentType(response);

		Map<Object, Object> map = new HashMap<Object, Object>();
		if (request.getRequestURI().equals("/")) {
			map.put("me", "/me");
			map.put("index", "/");
			map.put("prisoners", "/prisoners");
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
