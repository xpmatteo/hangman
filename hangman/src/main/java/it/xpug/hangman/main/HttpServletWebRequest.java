package it.xpug.hangman.main;

import javax.servlet.http.*;

public class HttpServletWebRequest implements WebRequest {

	private HttpServletRequest request;

	public HttpServletWebRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getRequestURI() {
		return request.getRequestURI();
	}

	@Override
	public String getMethod() {
		return request.getMethod();
	}

	@Override
	public String getParameter(String name) {
		return request.getParameter(name);
	}

}
