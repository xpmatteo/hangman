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
		JsonResponse jsonResponse = new JsonResponse(request, response);
		new UserController(users).handleRequest(request, jsonResponse);
		response.getWriter().write(toJson(jsonResponse));
	}

	private String toJson(Map<Object, Object> map) {
		return JSON.toString(map).replaceAll("\",", "\",\n") + "\n";
	}

}
