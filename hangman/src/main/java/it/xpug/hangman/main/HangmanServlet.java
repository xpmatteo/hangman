package it.xpug.hangman.main;


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
		new UserController(users, userIdSequence).handleGet(request, response, map);
		response.getWriter().write(JSON.toString(map));
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setContentType(response);
		Map<Object, Object> map = new HashMap<Object, Object>();

		new UserController(users, userIdSequence).handlePost(request, response, map);

		response.getWriter().write(JSON.toString(map));
	}

	private void setContentType(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
