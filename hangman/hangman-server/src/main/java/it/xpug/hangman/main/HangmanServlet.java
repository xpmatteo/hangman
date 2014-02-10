package it.xpug.hangman.main;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.util.ajax.*;

public class HangmanServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

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
}
