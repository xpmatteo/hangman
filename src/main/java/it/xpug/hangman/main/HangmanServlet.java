package it.xpug.hangman.main;


import it.xpug.hangman.domain.*;
import it.xpug.hangman.web.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class HangmanServlet extends HttpServlet {

	private UserBase users;

	public HangmanServlet(UserBase users) {
		this.users = users;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebResponse jsonResponse = new JsonResponse(response);
		WebRequest webRequest = new HttpServletWebRequest(request);
		new HangmanRouter(users).handleRequest(webRequest, jsonResponse);
		response.getWriter().write(jsonResponse.toString());
	}


}
