package it.xpug.hangman.main;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class HangmanServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{}");
	}
}
