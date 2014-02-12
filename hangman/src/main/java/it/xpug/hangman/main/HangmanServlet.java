package it.xpug.hangman.main;


import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class HangmanServlet extends HttpServlet {

	private UserBase users;

	public HangmanServlet(UserBase users) {
		this.users = users;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setContentType(response);
		Map<Object, Object> map = new HashMap<Object, Object>();
		new UserController(users).handleRequest(request, response, map);
		response.getWriter().write(toJson(map));
	}

	private String toJson(Map<Object, Object> map) {
		return JSON.toString(map).replaceAll("\",", "\",\n") + "\n";
	}

	private void setContentType(HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
