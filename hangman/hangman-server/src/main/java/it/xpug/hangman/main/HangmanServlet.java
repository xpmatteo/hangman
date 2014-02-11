package it.xpug.hangman.main;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class HangmanServlet extends HttpServlet {

	public HangmanServlet(UserIdSequence userIdSequence) {
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
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("prisoners", "/prisoners");
		map.put("id", "12345");
		map.put("url", "/users/12345");
		map.put("name", "Pippo");
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.getWriter().write(JSON.toString(map));
	}

	private void setContentType(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
